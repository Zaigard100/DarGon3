package com.zaig100.dg.utils.dgscript.lib;

import java.util.HashMap;
import java.util.Map;

public class Functions {
    private static final NumberVal ZERO = new NumberVal(0);
    private static final Map<String, Function> fuctions;

    static {
        fuctions = new HashMap<>();
        //sin
        fuctions.put("sin", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length != 1) throw new RuntimeException("Argument count exeption");
                return new NumberVal(Math.sin(args[0].asNum()));
            }
        });
        //cos
        fuctions.put("cos", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length != 1) throw new RuntimeException("Argument count exeption");
                return new NumberVal(Math.cos(args[0].asNum()));
            }
        });
        fuctions.put("print", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length != 1) throw new RuntimeException("Argument count exeption");
                System.out.print(args[0].asString());
                return ZERO;
            }
        });
        fuctions.put("println", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length == 0) System.out.println();
                else if (args.length == 1) System.out.println(args[0].asString());
                else throw new RuntimeException("Argument count exeption");
                return ZERO;
            }
        });
    }

    public static boolean isExist(String key) {
        return fuctions.containsKey(key);
    }

    public static Function get(String key) {
        if (!isExist(key)) throw new RuntimeException("Unknowm function" + key);
        return fuctions.get(key);
    }

    public static void set(String key, Function func) {
        fuctions.put(key, func);
    }

}
