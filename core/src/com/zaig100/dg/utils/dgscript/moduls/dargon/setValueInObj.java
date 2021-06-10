package com.zaig100.dg.utils.dgscript.moduls.dargon;

import com.zaig100.dg.utils.dgscript.lib.ArrayValue;
import com.zaig100.dg.utils.dgscript.lib.Function;
import com.zaig100.dg.utils.dgscript.lib.StringVal;
import com.zaig100.dg.utils.dgscript.lib.Value;

public class setValueInObj implements Function {
    @Override
    public Value execute(Value... args) {

        ArrayValue arr = null;
        StringVal name = null;
        Value val = null;

        if (args.length == 3) {
            arr = (ArrayValue) args[0];
            name = (StringVal) args[1];
            val = args[2];
        }
        return setVal(arr, name, val);
    }

    Value setVal(ArrayValue arr, StringVal name, Value val) {
        switch (name.asString()) {
            case "x":
            case "X":
                arr.set(0, val);
                ///
            case "y":
            case "Y":
                arr.set(1, val);
                ///
            case "tag":
            case "Tag":
                arr.set(arr.getLastIndex() - 1, val);
                ///
            case "type":
            case "Type":
                arr.set(arr.getLastIndex(), val);
                ///
            case "id":
            case "ID":
                if (arr.getLastVal().asString().equals("ITEM")) {
                    arr.set(2, val);
                }
                break;
            ////
            case "dx":
            case "DX":
            case "dX":
                if (arr.getLastVal().asString().equals("ARROW") || arr.getLastVal().asString().equals("CROSSBOW")) {
                    arr.set(2, val);
                }
                break;
            ///
            case "dy":
            case "DY":
            case "dY":
                if (arr.getLastVal().asString().equals("ARROW") || arr.getLastVal().asString().equals("CROSSBOW")) {
                    arr.set(3, val);
                }
                break;
            ///
            case "active":
            case "Active":
                if (arr.getLastVal().asString().equals("SPIKE") || arr.getLastVal().asString().equals("HIDE_TRAP")) {
                    arr.set(2, val);
                }
                if (arr.getLastVal().asString().equals("ITEM")) {
                    arr.set(3, val);
                }
                break;
            ///
            case "angle":
            case "Angle":
                if (arr.getLastVal().asString().equals("ARROW") || arr.getLastVal().asString().equals("CROSSBOW")) {
                    arr.set(4, val);
                }
                if (arr.getLastVal().asString().equals("FLAMETHROWER")) {
                    arr.set(3, val);
                }
                break;
            ///
            case "Tick":
            case "tick":
                if (arr.getLastVal().asString().equals("SPIKE") || arr.getLastVal().asString().equals("FLIMSY_TILE")) {
                    arr.set(3, val);
                }
                break;
            ///
            case "FlipX":
            case "flipX":
                if (arr.getLastVal().asString().equals("STAIR")) {
                    arr.set(2, val);
                }
                break;
            ///
            case "Next":
            case "next":
            case "NextLvl":
            case "nextLvl":
                if (arr.getLastVal().asString().equals("STAIR")) {
                    arr.set(3, val);
                }
                break;
            ///
            case "End":
            case "end":
                if (arr.getLastVal().asString().equals("STAIR")) {
                    arr.set(4, val);
                }
                break;
            ///
            case "Wight":
            case "wight":
                if (arr.getLastVal().asString().equals("SPINNEY")) {
                    arr.set(2, val);
                }
                break;
            ///
            case "Height":
            case "height":
                if (arr.getLastVal().asString().equals("SPINNEY")) {
                    arr.set(3, val);
                }
                break;
            ///
            case "tx":
            case "TX":
            case "tX":
                if (arr.getLastVal().asString().equals("TELEPORT")) {
                    arr.set(2, val);
                }
                break;
            ///
            case "ty":
            case "TY":
            case "tY":
                if (arr.getLastVal().asString().equals("TELEPORT")) {
                    arr.set(3, val);
                }
                break;
            ///
            case "Hide":
            case "hide":
                if (arr.getLastVal().asString().equals("TELEPORT")) {
                    arr.set(4, val);
                }
                break;
            ///
            case "crash":
            case "Crash":
            case "crashLvl":
            case "CrashLvl":
                if (arr.getLastVal().asString().equals("FLIMSY_TILE")) {
                    arr.set(2, val);
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
                    arr.set(3, val);
                }
                break;
            ///
            default:
                throw new RuntimeException(val.asString() + " not used with " + arr.getLastVal().asString() + " object");
        }
        return arr;
    }

}
