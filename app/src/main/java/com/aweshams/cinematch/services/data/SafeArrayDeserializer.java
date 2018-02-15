package com.aweshams.cinematch.services.data;

/**
 * Created by irteza on 2018-01-23.
 */

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Deserializes arrays safely. Some services may return a single JSON object instead of an array of
 * object. This causes Gson to fail as it expects an array not an object for the given Data Model.
 * This JsonDeserializer will create a temp list and properly deserialize the object into the list
 * or just deserialize the regular array items into the list and then convert it back to a POJO array
 * @param <T> Object type of the list items
 */
class SafeArrayDeserializer<T> implements JsonDeserializer<T[]> {
    /** Class of the list items' type */
    private Class<T> clazz;

    /**
     * Default Constructor
     * @param clazz Class of the list items' type
     */
    SafeArrayDeserializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @SuppressWarnings("unchecked")
    public T[] deserialize(JsonElement json, Type type, JsonDeserializationContext ctx) {
        // If the type is not a primitive array, return null (bad state)
        if (!(type instanceof GenericArrayType)) {
            // Should never happen
            return null;
        }
        // Instantiate the list to be used for the array items
        ArrayList<T> list = new ArrayList<>();
        // If the JSON Element is an Object instead of an array
        if (json.isJsonObject()) {
            // Parse the object and add it as the only item of the list
            list.add(ctx.deserialize(json, clazz));
        } else {
            // Otherwise parse the individual item in the JSON Array and add them to the list
            for (JsonElement item : json.getAsJsonArray()) {
                list.add(ctx.deserialize(item, clazz));
            }
        }
        // Create a new primitive array of the same size as the list; fill it with the list's values
        // then return it
        return list.toArray((T[]) Array.newInstance(clazz, list.size()));
    }
}
