package com.aweshams.cinematch.services.data;

/**
 * Created by irteza on 2018-01-23.
 */

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.TreeTypeAdapter;
import com.google.gson.reflect.TypeToken;

/**
 * Creates an instance of the generic SafeArrayDeserializer&lt;?&gt; for each array type found in
 * the data model.
 */
public class SafeArrayAdapterFactory implements TypeAdapterFactory {
    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        // Extract the class for the array type
        Class clazzArray = typeToken.getRawType();
        // If it is not an array, return null to indicate we do not handle this type
        if (!clazzArray.isArray()) {
            return null;
        }
        // Return a TreeTypeAdapter with a new instance of a SafeArrayDeserializer
        // NOTE: We do this the same exact way the GsonBuilder does. This allows us to effectively
        // inject a JsonDeserializer into an already constructed and immutable Gson object
        return TreeTypeAdapter
                .newFactoryWithMatchRawType(typeToken, new SafeArrayDeserializer<>(clazzArray.getComponentType()))
                .create(gson, typeToken);
    }
}
