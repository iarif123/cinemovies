package com.aweshams.cinematch.services.data;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by irteza on 2018-01-23.
 */

public class TransformAdapter<T> extends TypeAdapter<T> {

    public final Type type;

    public TransformAdapter() {
        type = new TypeToken<T>(){}.getType();
    }

    @Override
    public void write(JsonWriter out, T value) throws IOException {
        new Gson().toJson(value, type, out);
    }

    @Override
    public T read(JsonReader in) throws IOException {
        T value = new Gson().fromJson(in, type);
        return value;
    }
}