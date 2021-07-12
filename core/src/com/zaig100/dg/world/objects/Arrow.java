package com.zaig100.dg.world.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;
import com.zaig100.dg.world.World;

public class Arrow extends Obj {

    public int dx;
    public int dy;
    public int angle;
    float timer = 0;
    int far;
    public boolean isDel;

    public Arrow(int x, int y, int dx, int dy, int angle, String tag) {
        super(x, y, tag);
        type = ObjType.ARROW;
        this.dx = dx;
        this.dy = dy;
        this.angle = angle;
        if (World.map.mapHeight > World.map.mapWidht) {
            far = -World.map.mapHeight * 2;
        } else {
            far = -World.map.mapWidht * 2;
        }
    }

    public Arrow(int x, int y, int dx, int dy, int angle, String tag, int far) {
        super(x, y, tag);
        type = ObjType.ARROW;
        this.dx = dx;
        this.dy = dy;
        this.angle = angle;
        this.far = -far;
    }

    @Override
    public void render(SpriteBatch batch) {

        batch.draw(
                Res.arrow,
                wX - World.player.get_wX(),
                wY - World.player.get_wY(),
                8 * Configuration.getScale(),
                8 * Configuration.getScale(),
                16 * Configuration.getScale(),
                16 * Configuration.getScale(),
                1,
                1,
                angle
        );

    }

    @Override
    public void frame() {
        //System.out.println("X "+x+" Y " +y);
        if ((x == World.player.getX()) && (y == World.player.getY())) {
            if (World.player.getHp() > 0) {
                if (World.player.isSheld()) {
                    World.player.setHp(World.player.getHp() - 1);
                } else {
                    World.player.setHp(World.player.getHp() - 2);
                }
                World.player.setDamgeScr(0f, 3);
            }

        }
        isDel = last() || ((x == World.player.getX()) && (y == World.player.getY()));

        if (isMove()) {
            move();
        } else {
            x += dx;
            y += dy;
            timer = 0;
            far++;
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
            case "x":
                if (func.split(">")[1] == "++") {
                    x++;
                } else if (func.split(">")[1] == "--") {
                    x--;
                } else {
                    x = Integer.parseInt((func.split(">")[1]));
                }
                break;
            case "y":
                if (func.split(">")[1] == "++") {
                    y++;
                } else if (func.split(">")[1] == "--") {
                    y--;
                } else {
                    y = Integer.parseInt((func.split(">")[1]));
                }
                break;
            case "dx":
                if (func.split(">")[1] == "++") {
                    dx++;
                } else if (func.split(">")[1] == "--") {
                    dx--;
                } else {
                    dx = Integer.parseInt((func.split(">")[1]));
                }
                break;
            case "dy":
                if (func.split(">")[1] == "++") {
                    dy++;
                } else if (func.split(">")[1] == "--") {
                    dy--;
                } else {
                    dy = Integer.parseInt((func.split(">")[1]));
                }
                break;
            case "angle":
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


    public boolean last() {
        return far == 0;
    }

}