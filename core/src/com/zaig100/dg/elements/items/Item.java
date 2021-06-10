package com.zaig100.dg.elements.items;

import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class Item {
    int count;

    public abstract void render(Batch batch, int x, int y);

    public abstract boolean use();

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
