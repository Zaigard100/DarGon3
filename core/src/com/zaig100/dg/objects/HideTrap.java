package com.zaig100.dg.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Res;

public class HideTrap extends Obj {
    public boolean active;

    public HideTrap(int x, int y, String tag) {
        super(x, y, tag);
        type = ObjType.HIDE_TRAP;
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

    @Override
    public void tag_activate(String func) {
        switch (func.split(">")[0]) {
            case "X":
                if (func.split(">")[1] == "++") {
                    x++;
                } else if (func.split(">")[1] == "--") {
                    x--;
                } else {
                    x = Integer.parseInt((func.split(">")[1]));
                }
                break;
            case "Y":
                if (func.split(">")[1] == "++") {
                    y++;
                } else if (func.split(">")[1] == "--") {
                    y--;
                } else {
                    y = Integer.parseInt((func.split(">")[1]));
                }
                break;
            case "Active":
                if (func.split(">")[1] == "++" || func.split(">")[1] == "--") {
                    active = !active;
                } else {
                    active = Boolean.parseBoolean(func.split(">")[1]);
                }
                break;
            case "cordN":
                cordinateNormalize();
                break;
        }
    }
}
