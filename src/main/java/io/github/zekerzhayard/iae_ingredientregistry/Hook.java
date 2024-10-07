package io.github.zekerzhayard.iae_ingredientregistry;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.ImmutableMap;

public class Hook {
    private final static Set<Object> ingredientTypes = ConcurrentHashMap.newKeySet();

    public static ImmutableMap.Builder<Object, Object> checkAndPut(ImmutableMap.Builder<Object, Object> ingredientTypeBuilder, Object clazz, Object ingredientType) {
        if (!ingredientTypes.contains(clazz)) {
            ingredientTypes.add(clazz);
            ingredientTypeBuilder.put(clazz, ingredientType);
        }
        return ingredientTypeBuilder;
    }
}
