package com.zaig100.dg.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Res;

public class HideTrap extends Obj {
    boolean active;

    public HideTrap(int x, int y, String tag) {
        super(x, y, tag);
        this.x = x;
        this.y = y;
        this.active = true;
    }

    @Override
    public void render(SpriteBatch batch) {
    }

    public HideTrap(int x, int y, boolean active, String tag) {
        super(x, y, tag);
        this.x = x;
        this.y = y;
        this.active = active;
    }

    public void frame() {
        if (active) {
            if ((Player.getX() == x) && (Player.getY() == y)) {
                if (!Player.isSheld()) {
                    Player.setHp(Player.getHp() - 1);
                }
                Player.setDamgeScr(0f, 1);
                Res.lov.play(1.0f);
                active = false;
            }
        }
    }
}
