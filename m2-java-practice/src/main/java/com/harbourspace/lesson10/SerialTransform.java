package com.harbourspace.lesson10;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public class SerialTransform {
    public static String serialize(Object obj) throws IllegalAccessException {
        StringBuilder jsonBuilder = new StringBuilder();
        Field[] fields = obj.getClass().getDeclaredFields();
        jsonBuilder.append("{");

        boolean firstField = true;
        for (Field field : fields) {

            field.setAccessible(true);
            var value = field.get(obj);

            if (value != null) {
                if (!firstField) {
                    jsonBuilder.append(", ");
                }
                ;

                jsonBuilder.append("\"").append(field.getName()).append("\": ");
                if ((field.getType().getSimpleName().equals("Boolean")) || (field.getType().getSimpleName().equals("Integer"))
                        || (field.getType().getSimpleName().equals("Double"))
                        || (field.getType().getSimpleName().equals("Long"))) {
                    jsonBuilder.append(value);

                } else {
                    jsonBuilder.append("\"").append(value.toString()).append("\"");
                }
            }
            firstField = false;

        }

        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }
}
