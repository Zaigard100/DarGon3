package com.zaig100.dg.utils.contain;

public class TabletC extends ObjC {
    String[] text;

    public TabletC(int x, int y, String[] text, String tag) {
        super(x, y, tag);
        this.text = text;
    }

    public String[] getText() {
        return text;
    }
}
