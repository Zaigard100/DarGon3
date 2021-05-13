package com.zaig100.dg.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Joystick;

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
        if (Joystick.isUse() && Player.getX() == x && Player.getY() == y) {

        }
    }

    @Override
    public void tag_activate(String func) {

    }
}
