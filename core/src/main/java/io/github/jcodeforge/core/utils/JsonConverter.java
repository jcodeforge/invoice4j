package io.github.jcodeforge.core.utils;

import com.google.gson.*;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public final class JsonConverter {

    public static String toJson(Object obj) {
        return obj != null ? new Gson().toJson(obj) : null;
    }

    public static void toJson(Object obj, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            new Gson().toJson(obj, writer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json != null && !json.isEmpty()) {
            try {
                return new Gson().fromJson(json, clazz);
            }
            catch (JsonSyntaxException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public static <T> T fromJsonArray(String json, Type type) {
        if (json != null && !json.isEmpty()) {
            try {
                return new Gson().fromJson(json, type);
            }
            catch (JsonSyntaxException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public static JsonElement toJsonElement(String s) {
        JsonElement result = null;
        try {
            result = JsonParser.parseString(s);
        } catch (final JsonParseException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
