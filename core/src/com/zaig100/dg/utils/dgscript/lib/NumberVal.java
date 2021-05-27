package com.zaig100.dg.utils.dgscript.lib;

public class NumberVal implements Value {
    private final double val;

    public NumberVal(boolean val) {
        this.val = val ? 1 : 0;
    }

    public NumberVal(double val) {
        this.val = val;
    }

    @Override
    public double asNum() {
        return val;
    }

    @Override
    public String asString() {
        return Double.toString(val);
    }
}
