package com.zaig100.dg.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Joystick;
import com.zaig100.dg.utils.Res;

public class Teleport extends Obj {
    int tx, ty;
    boolean hide;

    public Teleport(int x, int y, int tx, int ty, String tag) {
        super(x, y, tag);
        this.tx = tx;
        this.ty = ty;
        this.hide = true;
    }

    public Teleport(int x, int y, int tx, int ty, boolean hide, String tag) {
        super(x, y, tag);
        this.x = x;
        this.y = y;
        this.tx = tx;
        this.ty = ty;
        this.hide = hide;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(Res.teleport, ((x + 3) * 16 * Configuration.getScale()) - Player.get_wX(), ((y + 2) * 16 * Configuration.getScale()) - Player.get_wY(), 16 * Configuration.getScale(), 16 * Configuration.getScale());
    }

    @Override
    public void frame() {
        Joystick.frame(0, 0);
        if ((Player.getX() == x) && (Player.getY() == y)) {
            if ((Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) || (Joystick.isUse())) {
                Player.teleport(tx, ty);
            }
        }
    }
}
