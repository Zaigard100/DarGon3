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
        //Joystick.frame((int) ((Gdx.graphics.getWidth() - 16 * 7 * Configuration.getScale()) / 2), (int) ((Gdx.graphics.getHeight() - 16 * 5 * Configuration.getScale()) / 2));
        if ((Player.getX() == x) && (Player.getY() == y)) {
            if ((Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) || (Joystick.isUse())) {
                Player.teleport(tx, ty);
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
            case "TX":
                if (func.split(">")[1] == "++") {
                    tx++;
                } else if (func.split(">")[1] == "--") {
                    tx--;
                } else {
                    tx = Integer.parseInt((func.split(">")[1]));
                }
                break;
            case "TY":
                if (func.split(">")[1] == "++") {
                    ty++;
                } else if (func.split(">")[1] == "--") {
                    ty--;
                } else {
                    ty = Integer.parseInt((func.split(">")[1]));
                }
                break;
            case "Hide":
                if (func.split(">")[1] == "++" || func.split(">")[1] == "--") {
                    hide = !hide;
                } else {
                    hide = Boolean.parseBoolean((func.split(">")[1]));
                }
                break;
        }
    }

}
