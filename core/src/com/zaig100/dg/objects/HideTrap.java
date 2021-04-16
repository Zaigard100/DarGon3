package com.zaig100.dg.objects;

import com.zaig100.dg.utils.Res;

public class HideTrap {
    int x, y;
    boolean active;

    public HideTrap(int x, int y) {
        this.x = x;
        this.y = y;
        this.active = true;
    }

    public HideTrap(int x, int y, boolean active) {
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
