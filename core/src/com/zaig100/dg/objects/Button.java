package com.zaig100.dg.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button extends Obj {
    String[] func;

    public Button(int x, int y, String[] func, String tag) {
        super(x, y, tag);
        this.func = func;
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void frame() {

    }

    @Override
    public void tag_activate(String func) {

    }
}
