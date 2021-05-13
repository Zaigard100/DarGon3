package com.zaig100.dg.utils.contain;

public class ButtonС extends ObjC {
    String[] func;

    public ButtonС(int x, int y, String[] func, String tag) {
        super(x, y, tag);
        this.func = func;
    }

    public String[] getFunc() {
        return func;
    }
}
