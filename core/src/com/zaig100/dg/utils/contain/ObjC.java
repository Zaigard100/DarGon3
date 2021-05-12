package com.zaig100.dg.utils.contain;

public abstract class ObjC {
    int x, y;
    String tag;

    public ObjC(int x, int y, String tag) {
        this.x = x;
        this.y = y;
        this.tag = tag;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getTag() {
        return tag;
    }
}
