package com.zaig100.dg.elements.items;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Empty extends Item {

    @Override
    public void render(Batch butch, int x, int y) {

    }

    @Override
    public boolean use() {
        return false;
    }
}
