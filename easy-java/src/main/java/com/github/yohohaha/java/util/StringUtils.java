package com.github.yohohaha.java.util;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * String Utils for manipulating strings.
 *
 * @author Yohohaha
 */
public class StringUtils {
    private static ConcurrentHashMap<String, Field> CLASS_FIELD_MAP = new ConcurrentHashMap<>();

    /**
     * format a raw string with a map, formatted key and map key must be the same.
     * example:
     * <p>
     * {@code interpolate("My name is ${name}", new HashMap(){{put("name", "Lee");}});}
     * will get {@code "My name is Lee"}
     *
     * @param origin not formatted string
     * @param map    map
     *
     * @return formatted string
     */
    public static String interpolate(String origin, Map<String, Object> map) {
        if (map == null) {
            return origin;
        }
        if (origin == null) {
            return null;
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            origin = org.apache.commons.lang3.StringUtils.replace(origin, "{" + entry.getKey() + "}", entry.getValue().toString());
        }
        return origin;
    }


    /**
     * format a raw string with a pojo, formatted key and field name must be the same.
     * example:
     * <p>
     * {@code interpolate("My name is ${name}.", new Person("Lee"));}
     * will get {@code "My name is Lee."}
     *
     * @param origin not formatted string
     * @param pojo   pojo
     *
     * @return formatted string
     */
    public static String interpolate(String origin, Object pojo) {
        if (pojo == null) {
            return origin;
        }
        if (origin == null) {
            return null;
        }
        Class<?> cls = pojo.getClass();
        String clsName = cls.getTypeName();
        int len = origin.length();
        StringBuilder builder = new StringBuilder(len);
        int lastStrEndIdx = 0;
        int i = 0;
        while (i + 1 < len) {
            String substr = origin.substring(i, i + 2);
            if ("${".equals(substr)) {
                builder.append(origin, lastStrEndIdx, i);
                int varNameStartIdx = i + 2;
                int varNameIdx = varNameStartIdx;
                while (varNameIdx < len) {
                    String subVarNameStr = origin.substring(varNameIdx, varNameIdx + 1);
                    if ("}".equals(subVarNameStr)) {
                        break;
                    }
                    varNameIdx++;
                }
                String varName = origin.substring(varNameStartIdx, varNameIdx);
                i = varNameIdx + 1;
                String fieldKey = clsName + "." + varName;
                Field field = CLASS_FIELD_MAP.computeIfAbsent(fieldKey, s -> {
                    try {
                        Field declaredField = cls.getDeclaredField(varName);
                        declaredField.setAccessible(true);
                        return declaredField;
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException("no such field, class=" + clsName + ", name=" + varName, e);
                    }
                });
                try {
                    builder.append(field.get(pojo));
                    lastStrEndIdx = i;
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("can't access field, field name = " + varName);
                }

            } else {
                i += 1;
            }
        }
        builder.append(origin, lastStrEndIdx, len);
        return builder.toString();
    }
}
