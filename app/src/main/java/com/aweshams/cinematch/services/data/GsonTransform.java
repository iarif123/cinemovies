package com.aweshams.cinematch.services.data;

/**
 * Created by irteza on 2018-01-23.
 */

import com.aweshams.cinematch.services.api.ApiTransform;
import com.aweshams.cinematch.services.api.TransformException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A JSON data transform implementation based on the Google {@link Gson} parser.
 */
public class GsonTransform<T> extends ApiTransform<T> implements ITransform<T> {

    // region static variables

    private static final Charset DEFAULT_ENCODING = Charset.forName("UTF-8");

    // endregion


    // region instance variables

    private final Class<T> _modelClass;
    private final List<TransformAdapter<?>> _adapters;
    private String[] _targetPath;

    // endregion


    // region constructors

    private GsonTransform(Class<T> modelClass) {

        // initialize instance variables
        _modelClass = modelClass;
        _adapters = new ArrayList<>();
    }

    // endregion


    // region factory method

    public static final <U> GsonTransform<U> create(Class<U> modelClass) {
        return new GsonTransform<>(modelClass);
    }

    // endregion


    // region configuration methods

    /**
     * Specifies the JSON path where the parsing starts (inclusive of final element in the path),
     * where path elements are separated by '.' as separators.
     * <p>
     * You can also provide a '*' if you do not know what the path element will be, but it will
     * throw a RuntimeException if no key is found
     *
     * @param path
     * @return The original transform object, so that it can be built upon.
     */
    public GsonTransform<T> target(String path) {

        // set target path
        _targetPath = path.split("\\.");

        // return transform for building
        return this;
    }

    public GsonTransform<T> withAdapter(TransformAdapter<?> adapter) {

        // map adapter
        _adapters.add(adapter);

        // return transform for building
        return this;
    }

    // endregion


    // region transformation methods

    @Override
    public T transform(byte[] data) throws TransformException {
        String text = new String(data, DEFAULT_ENCODING);
        return transform(text);
    }

    public T transform(String text) throws TransformException {

        // parse json directly into model
        try {

            // create parser
            final Gson gson = createGson();

            // parse directly if there is no target path
            final T model;
            if (_targetPath == null) {
                model = gson.fromJson(text, _modelClass);
            }

            // or navigate to target path, then parse
            else {

                // parse text into DOM model
                JsonElement json = new JsonParser().parse(text);

                // parse DOM
                model = parse(json);
            }

            // validate model (throws)
            validate(model);

            // return model
            return model;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new BadFormatException(e);
        }
    }

    public T transform(JsonElement json) throws TransformException {
        try {

            // parse model
            T model = parse(json);

            // validate model (throws)
            validate(model);

            // return model
            return model;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new BadFormatException(e);
        }
    }

    private T parse(JsonElement json) {

        // skip to target (if required)
        if (_targetPath != null) {

            // start at top
            JsonObject node = json.getAsJsonObject();

            // walk through the properties, until the target is reached
            final int pathCount = _targetPath.length - 1;
            for (int i = 0; i <= pathCount; ++i) {

                // get next node
                String target = _targetPath[i];

                // TODO: throw a better error than this!
                // throw an exception if there are no entries
                if (node.size() == 0) {
                    throw new RuntimeException(
                            "Attempted to target JSON object without elements");
                }

                // navigate down first property if path is "*", or just get property child
                JsonElement element = target.equals("*")
                        ? node.entrySet().iterator().next().getValue()
                        : node.get(target);

                // move to next node if there are more elements
                if (i < pathCount) {
                    node = element.getAsJsonObject();
                }

                // or set target
                else {
                    json = element;
                }
            }
        }

        // parse json
        T model = createGson().fromJson(json, _modelClass);
        return model;
    }

    // endregion


    // region validation methods

    private void validate(T model) throws ValidationException {

        try {
            validate(model, new LinkedList<>(), new HashMap<>());
        } catch (IllegalAccessException e) {
            throw new ValidationException(
                    "Model property is not accessible (check class definition)", e);
        } catch (NullPointerException e) {
            throw new ValidationException(
                    "Model property is null", e);
        }
    }

    private void validate(Object model, LinkedList<String> path,
                          Map<Class<?>, ValidationContext> contexts) throws ValidationException, IllegalAccessException {

        // get underlying model class (get component for arrays)
        Class<?> modelClass = model.getClass();
        Class<?> componentClass = modelClass.isArray()
                ? modelClass.getComponentType()
                : modelClass;

        // get context, or build it
        ValidationContext context = contexts.get(componentClass);
        if (context == null) {

            // loop through all component fields
            final Set<Field> requiredFields = new HashSet<>();
            final Set<Field> complexFields = new HashSet<>();
            for (Field field : componentClass.getFields()) {

                // skip non-serializable fields
                int modifiers = field.getModifiers();
                if (!Modifier.isPublic(modifiers)
                        || Modifier.isStatic(modifiers)
                        || Modifier.isTransient(modifiers)) {
                    continue;
                }

                // capture required fields
                if (field.getAnnotation(Required.class) != null) {
                    requiredFields.add(field);
                }

                // capture complex types
                if (!field.getType().isPrimitive()) {
                    complexFields.add(field);
                }
            }

            // persist context
            context = new ValidationContext(requiredFields, complexFields);
            contexts.put(componentClass, context);
        }

        // TODO: use Pipeline for this later
        // convert items to a list for iteration
        List<Object> parents = modelClass.isArray()
                ? Arrays.asList((Object[]) model)
                : Collections.singletonList(model);

        // process all objects (might just be a single item)
        int parentIndex = 0;
        for (Object parent : parents) {

            // skip if parent is null
            if (parent == null) {

                // increment index
                ++parentIndex;

                // continue
                continue;
            }

            // process any required fields
            for (Field field : context.requiredFields) {
                if (field.get(parent) == null) {
                    String reference = path.size() == 0
                            ? ""
                            : "." + StringUtils.join(path, '.');
                    reference += modelClass.isArray()
                            ? "[" + parentIndex + "]"
                            : "";
                    throw new ValidationException("Missing required value at "
                            + _modelClass.getSimpleName() + reference + "." + field.getName());
                }
            }

            // process any complex fields
            for (Field field : context.complexFields) {

                // skip if child is null
                Object child = field.get(parent);
                if (child == null) {
                    continue;
                }

                // add child path
                path.addLast((modelClass.isArray() ? "[" + parentIndex + "]" : "") + field.getName());

                // validate child (recursive)
                validate(child, path, contexts);

                // remove child after completion
                path.removeLast();
            }

            // increment index
            ++parentIndex;
        }
    }

    private class ValidationContext {

        final Set<Field> requiredFields;
        final Set<Field> complexFields;

        ValidationContext(Set<Field> requiredFields, Set<Field> complexFields) {
            this.requiredFields = requiredFields;
            this.complexFields = complexFields;
        }
    }

    // endregion


    // region helper methods

    protected final Gson createGson() {

        // create builder
        GsonBuilder builder = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC);

        // add adapters
        for (TransformAdapter<?> adapter : _adapters) {
            builder = builder.registerTypeAdapter(adapter.type, adapter);
        }

        // call base configuration and then create builder
        return configureDefaults(builder)
                .setPrettyPrinting()
                .create();
    }

    protected GsonBuilder configureDefaults(GsonBuilder gsonBuilder) {
        return gsonBuilder
                .registerTypeAdapter(DateTime.class, new DateTimeDeserializer())
                .registerTypeAdapterFactory(new SafeArrayAdapterFactory());
    }


    // endregion
}