package com.zaig100.dg.utils.dgscript.moduls.dargon;

import com.zaig100.dg.objects.Arrow;
import com.zaig100.dg.objects.Crossbow;
import com.zaig100.dg.objects.Flamefrower;
import com.zaig100.dg.objects.FlimsyTile;
import com.zaig100.dg.objects.HideTrap;
import com.zaig100.dg.objects.Items;
import com.zaig100.dg.objects.Obj;
import com.zaig100.dg.objects.Spike;
import com.zaig100.dg.objects.Spinney;
import com.zaig100.dg.objects.Stair;
import com.zaig100.dg.objects.Teleport;
import com.zaig100.dg.utils.dgscript.lib.ArrayValue;
import com.zaig100.dg.utils.dgscript.lib.Functions;
import com.zaig100.dg.utils.dgscript.lib.NumberVal;
import com.zaig100.dg.utils.dgscript.lib.StringVal;
import com.zaig100.dg.utils.dgscript.lib.Value;
import com.zaig100.dg.utils.dgscript.moduls.Module;

public class dargon implements Module {
    @Override
    public void init() {
        Functions.set("getObj", new getObj());
        Functions.set("getValueInObj", new getValueInObj());
        Functions.set("setValueInObj", new setValueInObj());
    }

    static Value objInValue(Obj obj) {
        ArrayValue arr;
        switch (obj.getType()) {
            case OBJ:
                arr = new ArrayValue(5);
                arr.set(0, new NumberVal(obj.getX()));
                arr.set(1, new NumberVal(obj.getY()));
                arr.set(2, new NumberVal(obj.getObjID()));
                arr.set(3, new StringVal(obj.getTag()));
                arr.set(4, new StringVal("OBJ"));
                return arr;
            case ITEM:
                arr = new ArrayValue(7);
                arr.set(0, new NumberVal(obj.getX()));
                arr.set(1, new NumberVal(obj.getY()));
                //TODO Item in script
                arr.set(3, NumberVal.fromBoolean(((Items) obj).active));
                arr.set(4, new NumberVal(obj.getObjID()));
                arr.set(5, new StringVal(obj.getTag()));
                arr.set(6, new StringVal("ITEM"));
                return arr;
            case ARROW:
                arr = new ArrayValue(8);
                arr.set(0, new NumberVal(obj.getX()));
                arr.set(1, new NumberVal(obj.getY()));
                arr.set(2, new NumberVal(((Arrow) obj).dx));
                arr.set(3, new NumberVal(((Arrow) obj).dy));
                arr.set(4, new NumberVal(((Arrow) obj).angle));
                arr.set(5, new NumberVal(obj.getObjID()));
                arr.set(6, new StringVal(obj.getTag()));
                arr.set(7, new StringVal("ARROW"));
                return arr;
            case SPIKE:
                arr = new ArrayValue(7);
                arr.set(0, new NumberVal(obj.getX()));
                arr.set(1, new NumberVal(obj.getY()));
                arr.set(2, NumberVal.fromBoolean(((Spike) obj).active));
                arr.set(3, new NumberVal(((Spike) obj).tick_sec));
                arr.set(4, new NumberVal(obj.getObjID()));
                arr.set(5, new StringVal(obj.getTag()));
                arr.set(6, new StringVal("SPIKE"));
                return arr;
            case STAIR:
                arr = new ArrayValue(8);
                arr.set(0, new NumberVal(obj.getX()));
                arr.set(1, new NumberVal(obj.getY()));
                arr.set(2, NumberVal.fromBoolean(((Stair) obj).flip_x));
                arr.set(3, new StringVal(((Stair) obj).next_path));
                arr.set(4, NumberVal.fromBoolean(((Stair) obj).end));
                arr.set(5, new NumberVal(obj.getObjID()));
                arr.set(6, new StringVal(obj.getTag()));
                arr.set(7, new StringVal("STAIR"));
                return arr;
            case BUTTON:
                arr = new ArrayValue(5);
                arr.set(0, new NumberVal(obj.getX()));
                arr.set(1, new NumberVal(obj.getY()));
                arr.set(2, new NumberVal(obj.getObjID()));
                arr.set(3, new StringVal(obj.getTag()));
                arr.set(4, new StringVal("BUTTON"));
                return arr;
            case SPINNEY:
                arr = new ArrayValue(7);
                arr.set(0, new NumberVal(obj.getX()));
                arr.set(1, new NumberVal(obj.getY()));
                arr.set(2, new NumberVal(((Spinney) obj).wight));
                arr.set(3, new NumberVal(((Spinney) obj).height));
                arr.set(4, new NumberVal(obj.getObjID()));
                arr.set(5, new StringVal(obj.getTag()));
                arr.set(6, new StringVal("SPINNEY"));
                return arr;
            case CROSSBOW:
                arr = new ArrayValue(8);
                arr.set(0, new NumberVal(obj.getX()));
                arr.set(1, new NumberVal(obj.getY()));
                arr.set(2, new NumberVal(((Crossbow) obj).dx));
                arr.set(3, new NumberVal(((Crossbow) obj).dy));
                arr.set(4, new NumberVal(((Crossbow) obj).angle));
                arr.set(5, new NumberVal(obj.getObjID()));
                arr.set(6, new StringVal(obj.getTag()));
                arr.set(7, new StringVal("CROSSBOW"));
                return arr;
            case TELEPORT:
                arr = new ArrayValue(8);
                arr.set(0, new NumberVal(obj.getX()));
                arr.set(1, new NumberVal(obj.getY()));
                arr.set(2, new NumberVal(((Teleport) obj).tx));
                arr.set(3, new NumberVal(((Teleport) obj).ty));
                arr.set(4, NumberVal.fromBoolean(((Teleport) obj).hide));
                arr.set(5, new NumberVal(obj.getObjID()));
                arr.set(6, new StringVal(obj.getTag()));
                arr.set(7, new StringVal("TELEPORT"));
                return arr;
            case HIDE_TRAP:
                arr = new ArrayValue(6);
                arr.set(0, new NumberVal(obj.getX()));
                arr.set(1, new NumberVal(obj.getY()));
                arr.set(2, NumberVal.fromBoolean(((HideTrap) obj).active));
                arr.set(3, new NumberVal(obj.getObjID()));
                arr.set(4, new StringVal(obj.getTag()));
                arr.set(5, new StringVal("HIDE_TRAP"));
                return arr;
            case FLIMSY_TILE:
                arr = new ArrayValue(7);
                arr.set(0, new NumberVal(obj.getX()));
                arr.set(1, new NumberVal(obj.getY()));
                arr.set(2, new NumberVal(((FlimsyTile) obj).crashed_lvl));
                arr.set(3, new NumberVal(((FlimsyTile) obj).tick_sec));
                arr.set(4, new NumberVal(obj.getObjID()));
                arr.set(5, new StringVal(obj.getTag()));
                arr.set(6, new StringVal("FLIMSY_TILE"));
                return arr;
            case FLAMETHROWER:
                arr = new ArrayValue(8);
                arr.set(0, new NumberVal(obj.getX()));
                arr.set(1, new NumberVal(obj.getY()));
                arr.set(2, new NumberVal(((Flamefrower) obj).max));
                arr.set(3, new NumberVal(((Flamefrower) obj).stage));
                arr.set(4, new NumberVal(((Flamefrower) obj).angle));
                arr.set(5, new NumberVal(obj.getObjID()));
                arr.set(6, new StringVal(obj.getTag()));
                arr.set(7, new StringVal("FLAMETHROWER"));
                return arr;
        }
        throw new RuntimeException("Unknoun object");
    }

}
