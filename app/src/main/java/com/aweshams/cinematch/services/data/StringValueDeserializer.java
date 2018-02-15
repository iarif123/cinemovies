package com.aweshams.cinematch.services.data;

/**
 * Created by irteza on 2018-01-23.
 */

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Deserializes any object based on a JSON {@link String} value.
 */
public abstract class StringValueDeserializer<T> implements JsonDeserializer<T> {

    // region abstract methods

    protected abstract T deserialize(String value) throws JsonParseException;

    // endregion


    // region overridden methods

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        // skip if the value is null
        if (json.isJsonNull()) {
            return null;
        }

        // throw if there's no string for the element
        String value;
        if (!json.isJsonPrimitive()
                || (value = json.getAsString()) == null
                || value.isEmpty()) {
            throw new JsonParseException("Expected string value, found " + json.toString());
        }

        // try to deserialize value
        try {
            return deserialize(value);
        }

        // rethrow any uncaught exceptions as parse errors
        catch (Exception e) {
            throw new JsonParseException("Unable to parse value: " + e.getMessage(), e);
        }
    }

    // endregion
}