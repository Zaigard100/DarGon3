package com.zaig100.dg.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Joystick;
import com.zaig100.dg.utils.Res;

public class Tablet extends Obj { //TODO Табличка, сэр.

    String[] text;

    public Tablet(int x, int y, String[] text, String tag) {
        super(x, y, tag);
        this.text = text;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(
                Res.tablet,
                wX - Player.get_wX(),
                wY - Player.get_wY(),
                16 * Configuration.getScale(),
                16 * Configuration.getScale()
        );
    }

    @Override
    public void frame() {
        if ((Gdx.input.justTouched() && Joystick.isUse()) || (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))) {
            if ((Player.getX() - 1 == x || Player.getX() == x || Player.getX() + 1 == x) && (Player.getY() - 1 == y || Player.getY() == y || Player.getY() + 1 == y)) {
                if (!((Player.getX() == x && Player.getY() == y))) {
                    Player.map.setTablet_text(text);
                }
            }
        }

    }

    @Override
    public void tag_activate(String func) {

    }
}
