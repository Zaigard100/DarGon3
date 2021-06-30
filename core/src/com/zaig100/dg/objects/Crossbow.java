package com.zaig100.dg.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

import java.util.ArrayList;

public class Crossbow extends Obj {

    public int dx, dy, angle;
    public float tick_sec;
    float timer = 0;
    ArrayList<Arrow> arrs = new ArrayList<>();

    public Crossbow(int x, int y, int dx, int dy, int angel, float tick_sec, String tag) {

        super(x, y, tag);
        type = ObjType.CROSSBOW;
        this.dx = dx;
        this.dy = dy;
        this.angle = angel;
        this.tick_sec = tick_sec;
        arrs.add(new Arrow(x, y, dx, dy, angle, tag + "Arr" + arrs.size()));
    }

    @Override
    public void render(SpriteBatch batch) {

        batch.draw(Res.crossbow(false), wX - Player.get_wX(), wY - Player.get_wY(), 8 * Configuration.getScale(), 8 * Configuration.getScale(), 16 * Configuration.getScale(), 16 * Configuration.getScale(), 1, 1, angle);
        for (Arrow arr : arrs) {
            arr.render(batch);
        }

    }

    @Override
    public void frame() {
        tick(tick_sec);
        for (Arrow arr : arrs) {
            if (arr.isDel) {
                arrs.remove(arr);
                return;
            }
            arr.frame();
        }
        if (isMove()) {
            move();
        }

    }

    @Override
    public void show_obj(SpriteBatch batch) {
        batch.draw(
                Res.damage,
                wX - Player.get_wX(),
                wY - Player.get_wY(),
                16 * Configuration.getScale(),
                16 * Configuration.getScale()
        );
        for (Arrow arr : arrs) {
            arr.show_obj(batch);
        }
    }

    public void tick(float second) {
        timer += Gdx.graphics.getDeltaTime();
        if (timer >= second) {
            arrs.add(new Arrow(x, y, dx, dy, angle, tag + "Arr" + arrs.size()));
            timer = 0;
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
            case "DX":
                if (func.split(">")[1] == "++") {
                    dx++;
                } else if (func.split(">")[1] == "--") {
                    dx--;
                } else {
                    dx = Integer.parseInt((func.split(">")[1]));
                }
                break;
            case "DY":
                if (func.split(">")[1] == "++") {
                    dy++;
                } else if (func.split(">")[1] == "--") {
                    dy--;
                } else {
                    dy = Integer.parseInt((func.split(">")[1]));
                }
                break;
            case "Angle":
                if (func.split(">")[1] == "++") {
                    angle++;
                } else if (func.split(">")[1] == "--") {
                    angle--;
                } else {
                    angle = Integer.parseInt((func.split(">")[1]));
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

    public void setTick_sec(float tick_sec) {
        this.tick_sec = tick_sec;
    }
}
