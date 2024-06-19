package com.example.HotelManagementSystem.exception;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ResourceNotFoundException extends RuntimeException {


    public ResourceNotFoundException(Class clazz, String... searchParamsMap) {
        super(ResourceNotFoundException.generateMessage(clazz.getSimpleName(), toMap(searchParamsMap)));
    }

    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return StringUtils.capitalize(entity) + " was not found for parameters " + searchParams;
    }

    @SuppressWarnings("unchecked")
    private static <K, V> Map<K, V> toMap(Object... entries) {
        if (entries.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid entries");
        }

        Map<K, V> map = new HashMap<>();
        for (int i = 0; i < entries.length; i += 2) {
            map.put(((Class<K>) String.class).cast(entries[i]), ((Class<V>) String.class).cast(entries[i + 1]));
        }

        return map;
    }


}
