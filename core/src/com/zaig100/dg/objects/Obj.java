package com.zaig100.dg.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;

public abstract class Obj {

    public enum ObjType {
        OBJ,
        ARROW,
        BUTTON,
        CROSSBOW,
        FLAMETHROWER,
        FLIMSY_TILE,
        HIDE_TRAP,
        ITEM,
        SPIKE,
        SPINNEY,
        STAIR,
        TELEPORT
    }

    int x, y, wX, wY;
    String tag;
    ObjType type = ObjType.OBJ;

    public Obj(int x, int y, String tag) {
        this.x = x;
        this.y = y;
        cordinateNormalize();
        this.tag = tag;
    }

    public void cordinateNormalize() {
        wX = (x + 3) * 16 * (int) Configuration.getScale();
        wY = (y + 2) * 16 * (int) Configuration.getScale();
    }

    public void move() {
        if (wX != (x + 3) * 16 * (int) Configuration.getScale()) {
            if (wX > (x + 3) * 16 * (int) Configuration.getScale()) {
                wX = (int) (wX - Configuration.getScale());

            }
            if (wX < (x + 3) * 16 * (int) Configuration.getScale()) {
                wX = (int) (wX + Configuration.getScale());
            }

        }

        if (wY != (y + 2) * 16 * (int) Configuration.getScale()) {
            if (wY > (y + 2) * 16 * (int) Configuration.getScale()) {
                wY = (int) (wY - Configuration.getScale());
            }
            if (wY < (y + 2) * 16 * (int) Configuration.getScale()) {
                wY = (int) (wY + Configuration.getScale());
            }
        }
    }

    public boolean isMove() {
        return (wX != (x + 3) * 16 * (int) Configuration.getScale() || wY != (y + 2) * 16 * (int) Configuration.getScale());
    }

    public abstract void render(SpriteBatch batch);

    public abstract void frame();

    public abstract void tag_activate(String func);

    public ObjType getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getTag() {
        return tag;
    }
}
