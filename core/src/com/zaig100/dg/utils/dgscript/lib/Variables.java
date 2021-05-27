package com.zaig100.dg.utils.dgscript.lib;

import java.util.HashMap;
import java.util.Map;

public class Variables {
    private static final NumberVal ZERO = new NumberVal(0);
    private static final Map<String, Value> variables;

    static {
        variables = new HashMap<>();
        variables.put("PI", new NumberVal(3.14));
        variables.put("F_PI", new NumberVal(Math.PI));
        variables.put("E", new NumberVal(2.72));
        variables.put("F_E", new NumberVal(Math.E));
        variables.put("G_R", new NumberVal(1.618));
    }

    public static boolean isExist(String key) {
        return variables.containsKey(key);
    }

    public static Value get(String key) {
        if (!isExist(key)) return ZERO;
        return variables.get(key);
    }

    public static void set(String key, Value val) {
        variables.put(key, val);
    }

}
