package com.zaig100.dg.world.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;
import com.zaig100.dg.world.World;

public class Spinney extends Obj {
    public int wight, height;
    private int i, j;

    public Spinney(int x, int y, int wight, int height, String tag) {
        super(x, y, tag);
        type = ObjType.SPINNEY;
        this.wight = Math.abs(wight);
        this.height = Math.abs(height);
    }

    public void render(SpriteBatch batch) {
        for (i = 0; i < height; i++) {
            for (j = 0; j < wight; j++) {
                batch.draw(
                        Res.spinney,
                        (wX + j * 16 * Configuration.getScale()) - World.player.get_wX(),
                        (wY + i * 16 * Configuration.getScale()) - World.player.get_wY(),
                        16 * Configuration.getScale(),
                        16 * Configuration.getScale()
                );
            }
        }
    }

    @Override
    public void frame() {
        if (isMove()) {
            move();
        }
    }

    @Override
    public void show_obj(SpriteBatch batch) {
        batch.draw(
                Res.blue_obj,
                wX - World.player.get_wX(),
                wY - World.player.get_wY(),
                wight * 16 * Configuration.getScale(),
                height * 16 * Configuration.getScale()
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
            case "Wight":
                if (func.split(">")[1] == "++") {
                    wight++;
                } else if (func.split(">")[1] == "--") {
                    wight--;
                } else {
                    wight = Integer.parseInt((func.split(">")[1]));
                }
                break;
            case "Height":
                if (func.split(">")[1] == "++") {
                    height++;
                } else if (func.split(">")[1] == "--") {
                    height--;
                } else {
                    height = Integer.parseInt((func.split(">")[1]));
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
