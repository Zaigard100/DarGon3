package com.zaig100.dg.utils.dgscript.moduls.dargon;

import com.zaig100.dg.utils.dgscript.lib.Functions;
import com.zaig100.dg.utils.dgscript.moduls.Module;

public class dargon implements Module {
    @Override
    public void init() {
        Functions.set("getObj", new getObj());
    }
}
