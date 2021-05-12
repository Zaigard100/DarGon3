package com.zaig100.dg.utils.contain;

public class FlamefrowerC extends ObjC {
    int stage, max, rot;

    public FlamefrowerC(int x, int y, int stage, int max, int rot, String tag) {
        super(x, y, tag);
        this.stage = stage;
        this.max = max;
        this.rot = rot;
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
