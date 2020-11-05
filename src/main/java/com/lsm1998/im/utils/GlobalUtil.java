package com.lsm1998.im.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalUtil
{
    private static final Map<Object, Object> globalMap = new ConcurrentHashMap<>();

    public static <K, V> void set(K key, V value)
    {
        globalMap.put(key, value);
    }

    public static void remove(Object key)
    {
        globalMap.remove(key);
    }

    public static <K, V> V get(K key)
    {
        Object val = globalMap.get(key);
        if (val == null)
        {
            return null;
        }
        return (V) val;
    }
}
