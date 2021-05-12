package com.zaig100.dg.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Obj {
    int x, y;
    String tag;

    public Obj(int x, int y, String tag) {
        this.x = x;
        this.y = y;
        this.tag = tag;
    }

    public abstract void render(SpriteBatch batch);

    public abstract void frame();

    public abstract void tag_activate(String func);

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
