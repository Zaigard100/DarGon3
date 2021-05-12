package com.zaig100.dg.utils.contain;

public class ItemC extends ObjC {
    int id;

    public ItemC(int x, int y, int id, String tag) {
        super(x, y, tag);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
