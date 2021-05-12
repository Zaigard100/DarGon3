package com.zaig100.dg.utils.contain;

public class SpikeC extends ObjC {
    boolean active;

    public SpikeC(int x, int y, boolean active, String tag) {
        super(x, y, tag);
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
