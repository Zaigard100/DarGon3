 package com.zaig100.dg.world.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;
import com.zaig100.dg.utils.contain.HideTrapC;
import com.zaig100.dg.world.World;

import java.util.HashMap;

 public class HideTrap extends Obj {

    public boolean active;

    public HideTrap(HideTrapC contain){
        super(contain.getX(), contain.getY(), contain.getTag());
        type = ObjType.HIDE_TRAP;
        this.active = true;
    }

    public HideTrap(int x, int y, String tag) {
        super(x, y, tag);
        type = ObjType.HIDE_TRAP;
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
            if ((World.player.getX() == x) && (World.player.getY() == y)) {
                if (!World.player.isSheld()) {
                    World.player.setHp(World.player.getHp() - 1);
                }
                World.player.setDamgeScr(0f, 1);
                Res.lov.play(1.0f);
                active = false;
            }
        }
        if (!active) {
            del();
        }
    }

    @Override
    public void show_obj(SpriteBatch batch) {
        batch.draw(
                Res.damage,
                wX - World.player.get_wX(),
                wY - World.player.get_wY(),
                16 * Configuration.getScale(),
                16 * Configuration.getScale()
        );
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
            case "del":
                del();
                break;
        }
    }
}
