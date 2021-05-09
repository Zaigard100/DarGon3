package com.zaig100.dg.utils.contain;

public class SpikeC {
    int x, y;
    boolean active;

    public SpikeC(int x, int y, boolean active) {
        this.x = x;
        this.y = y;
        this.active = active;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isActive() {
        return active;
    }
}
