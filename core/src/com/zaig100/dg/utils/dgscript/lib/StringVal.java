package com.zaig100.dg.utils.dgscript.lib;

public class StringVal implements Value {
    private final String val;

    public StringVal(String val) {
        this.val = val;
    }

    @Override
    public double asNum() {
        try {
            return Double.parseDouble(val);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public String asString() {
        return val;
    }
}
