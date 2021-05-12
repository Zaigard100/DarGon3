package com.zaig100.dg.utils.contain;

public class FlimsyTileC extends ObjC {
    int stage;

    public FlimsyTileC(int x, int y, int stage, String tag) {
        super(x, y, tag);
        this.stage = stage;
    }

    public int getStage() {
        return stage;
    }
}
