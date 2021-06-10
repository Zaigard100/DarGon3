package com.zaig100.dg.utils.dgscript.moduls.dargon;

import com.zaig100.dg.utils.dgscript.lib.ArrayValue;
import com.zaig100.dg.utils.dgscript.lib.Function;
import com.zaig100.dg.utils.dgscript.lib.StringVal;
import com.zaig100.dg.utils.dgscript.lib.Value;

public class getValueInObj implements Function {
    @Override
    public Value execute(Value... args) {
        ArrayValue arrVal;
        StringVal valName;
        if (args.length == 2) {
            if (args[0] instanceof ArrayValue) {
                arrVal = (ArrayValue) args[0];
                valName = (StringVal) args[1];
            } else if (args[0] instanceof StringVal) {
                arrVal = (ArrayValue) args[1];
                valName = (StringVal) args[0];
            } else {
                throw new RuntimeException("Invalid value type");
            }
            return getVal(arrVal, valName);
        }
        return null;
    }

    Value getVal(ArrayValue arr, StringVal val) {
        switch (val.asString()) {
            case "x":
            case "X":
                return arr.get(0);
            ///
            case "y":
            case "Y":
                return arr.get(1);
            ///
            case "tag":
            case "Tag":
                return arr.get(arr.getLastIndex() - 1);
            ///
            case "type":
            case "Type":
                return arr.getLastVal();
            ///
            case "id":
            case "ID":
                if (arr.getLastVal().asString().equals("ITEM")) {
                    return arr.get(2);
                }
                break;
            ////
            case "dx":
            case "DX":
            case "dX":
                if (arr.getLastVal().asString().equals("ARROW") || arr.getLastVal().asString().equals("CROSSBOW")) {
                    return arr.get(2);
                }
                break;
            ///
            case "dy":
            case "DY":
            case "dY":
                if (arr.getLastVal().asString().equals("ARROW") || arr.getLastVal().asString().equals("CROSSBOW")) {
                    return arr.get(3);
                }
                break;
            ///
            case "active":
            case "Active":
                if (arr.getLastVal().asString().equals("SPIKE") || arr.getLastVal().asString().equals("HIDE_TRAP")) {
                    return arr.get(2);
                }
                if (arr.getLastVal().asString().equals("ITEM")) {
                    return arr.get(3);
                }
                break;
            ///
            case "angle":
            case "Angle":
                if (arr.getLastVal().asString().equals("ARROW") || arr.getLastVal().asString().equals("CROSSBOW")) {
                    return arr.get(4);
                }
                if (arr.getLastVal().asString().equals("FLAMETHROWER")) {
                    return arr.get(3);
                }
                break;
            ///
            case "Tick":
            case "tick":
                if (arr.getLastVal().asString().equals("SPIKE") || arr.getLastVal().asString().equals("FLIMSY_TILE")) {
                    return arr.get(3);
                }
                break;
            ///
            case "FlipX":
            case "flipX":
                if (arr.getLastVal().asString().equals("STAIR")) {
                    return arr.get(2);
                }
                break;
            ///
            case "Next":
            case "next":
            case "NextLvl":
            case "nextLvl":
                if (arr.getLastVal().asString().equals("STAIR")) {
                    return arr.get(3);
                }
                break;
            ///
            case "End":
            case "end":
                if (arr.getLastVal().asString().equals("STAIR")) {
                    return arr.get(4);
                }
                break;
            ///
            case "Wight":
            case "wight":
                if (arr.getLastVal().asString().equals("SPINNEY")) {
                    return arr.get(2);
                }
                break;
            ///
            case "Height":
            case "height":
                if (arr.getLastVal().asString().equals("SPINNEY")) {
                    return arr.get(3);
                }
                break;
            ///
            case "tx":
            case "TX":
            case "tX":
                if (arr.getLastVal().asString().equals("TELEPORT")) {
                    return arr.get(2);
                }
                break;
            ///
            case "ty":
            case "TY":
            case "tY":
                if (arr.getLastVal().asString().equals("TELEPORT")) {
                    return arr.get(3);
                }
                break;
            ///
            case "Hide":
            case "hide":
                if (arr.getLastVal().asString().equals("TELEPORT")) {
                    return arr.get(4);
                }
                break;
            ///
            case "crash":
            case "Crash":
            case "crashLvl":
            case "CrashLvl":
                if (arr.getLastVal().asString().equals("FLIMSY_TILE")) {
                    return arr.get(2);
                }
                break;
            ///
            case "max":
            case "Max":
                if (arr.getLastVal().asString().equals("FLAMETHROWER")) {
                    return arr.get(2);
                }
                break;
            ///
            case "stage":
            case "Stage":
                if (arr.getLastVal().asString().equals("FLAMETHROWER")) {
                    return arr.get(3);
                }
                break;
            ///
        }

        throw new RuntimeException(val.asString() + " not used with " + arr.getLastVal().asString() + " object");
    }

}
