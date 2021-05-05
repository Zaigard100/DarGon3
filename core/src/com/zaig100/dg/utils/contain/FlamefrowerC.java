package com.zaig100.dg.utils.contain;

public class FlamefrowerC {
    int x, y, stage, max, rot;

    public FlamefrowerC(int x, int y, int stage, int max, int rot) {
        this.x = x;
        this.y = y;
        this.stage = stage;
        this.max = max;
        this.rot = rot;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getStage() {
        return stage;
    }

    public int getMax() {
        return max;
    }

    public int getRot() {
        return rot;
    }
}
