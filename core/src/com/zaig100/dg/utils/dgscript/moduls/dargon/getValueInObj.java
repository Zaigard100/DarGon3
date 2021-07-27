package com.zaig100.dg.utils.dgscript.moduls.dargon;

import com.zaig100.dg.utils.contain.DoorC;
import com.zaig100.dg.utils.dgscript.lib.ArrayValue;
import com.zaig100.dg.utils.dgscript.lib.DGObjectValue;
import com.zaig100.dg.utils.dgscript.lib.Function;
import com.zaig100.dg.utils.dgscript.lib.NumberVal;
import com.zaig100.dg.utils.dgscript.lib.StringVal;
import com.zaig100.dg.utils.dgscript.lib.Value;

public class getValueInObj implements Function {
    @Override
    public Value execute(Value... args) {
        DGObjectValue arrVal;
        StringVal valName;
        if (args.length == 2) {
            if (args[0] instanceof DGObjectValue) {
                arrVal = (DGObjectValue) args[0];
                valName = (StringVal) args[1];
            } else if (args[0] instanceof StringVal) {
                arrVal = (DGObjectValue) args[1];
                valName = (StringVal) args[0];
            } else {
                throw new RuntimeException("Invalid value type");
            }
            return getVal(arrVal, valName);
        }
        return null;
    }

    Value getVal(DGObjectValue val, StringVal name) {
        if ("X".equals(name) || "x".equals(name)) {
            return new NumberVal(val.getObject().getX());
        } else if ("Y".equals(name) || "y".equals(name)) {
            return new NumberVal(val.getObject().getY());
        } else if ("Tag".equals(name) || "tag".equals(name) || "TAG".equals(name)) {
            return new StringVal(val.getObject().tag);
        }
        switch (val.getType()){
            case DOOR:
                if(val.getObject() instanceof DoorC)
                    if ("keyTag".equals(name) || "KeyTag".equals(name) || "keytag".equals(name)) {
                        return new StringVal(((DoorC) val.getObject()).getKeyTag());
                    } else if ("isOpen".equals(name) || "IsOpen".equals(name) || "isopen".equals(name)) {
                        return NumberVal.fromBoolean(((DoorC) val.getObject()).isOpen());
                    } else if ("facing".equals(name) || "Facing".equals(name)) {
                        return new NumberVal(((DoorC) val.getObject()).getFaicing());
                    }
                break;
            case ITEM: break;//TODO доделать
            case SHOP: break;
            case ZONA: break;
            case ARROW: break;
            case CHEST: break;
            case SPIKE: break;
            case STAIR: break;
            case BUTTON: break;
            case TABLET: break;
            case SPINNEY: break;
            case CROSSBOW: break;
            case KAMIKAZE: break;
            case TELEPORT: break;
            case HIDE_TRAP: break;
            case FLIMSY_TILE: break;
            case FLAMETHROWER: break;
            case MOB: break;
        }

        return null;
    }

}
