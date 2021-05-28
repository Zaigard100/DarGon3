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
        //print
        fuctions.put("print", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length != 1) throw new RuntimeException("Argument count exeption");
                System.out.print(args[0].asString());
                return ZERO;
            }
        });
        //println
        fuctions.put("println", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length == 0) System.out.println();
                else if (args.length == 1) System.out.println(args[0].asString());
                else throw new RuntimeException("Argument count exeption");
                return ZERO;
            }
        });
        fuctions.put("rand", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length == 0) return new NumberVal(Math.random());
                else if (args.length == 1) return new NumberVal(Math.random() * args[0].asNum());
                else if (args.length == 2)
                    return new NumberVal((Math.random() * (args[1].asNum() - args[0].asNum())) + args[0].asNum());
                else throw new RuntimeException("Argument count exeption");
            }
        });
        fuctions.put("round", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length == 1) return new NumberVal(Math.round(args[0].asNum()));
                else throw new RuntimeException("Argument count exeption");
            }
        });
    }

    public static boolean isExist(String key) {
        return fuctions.containsKey(key);
    }

    public static Function get(String key) {
        if (!isExist(key)) throw new RuntimeException("Unknowm function: " + key);
        return fuctions.get(key);
    }

    public static void set(String key, Function func) {
        if (isExist(key)) throw new RuntimeException("Function duplikat: " + key);
        fuctions.put(key, func);
    }

}
