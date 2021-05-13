package com.zaig100.dg.utils.contain;

public class ItemC extends ObjC {
    int id;
    boolean active;

    public ItemC(int x, int y, int id, boolean active, String tag) {
        super(x, y, tag);
        this.id = id;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }
}
