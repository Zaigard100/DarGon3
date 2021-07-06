package com.zaig100.dg.world.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Joystick;
import com.zaig100.dg.utils.Res;
import com.zaig100.dg.world.World;

public class Tablet extends Obj {

    String[] text;

    public Tablet(int x, int y, String[] text, String tag) {
        super(x, y, tag);
        this.text = text;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(
                Res.tablet,
                wX - World.player.get_wX(),
                wY - World.player.get_wY(),
                16 * Configuration.getScale(),
                16 * Configuration.getScale()
        );
    }

    @Override
    public void frame() {
        if ((Gdx.input.justTouched() && Joystick.isUse()) || (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))) {
            if ((World.player.getX() - 1 == x || World.player.getX() == x || World.player.getX() + 1 == x) && (World.player.getY() - 1 == y || World.player.getY() == y || World.player.getY() + 1 == y)) {
                if (!((World.player.getX() == x && World.player.getY() == y))) {
                    World.map.setTablet_text(text);
                }
            }
        }

    }

    @Override
    public void show_obj(SpriteBatch batch) {
        batch.draw(
                Res.blue_obj,
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
            case "cordN":
                cordinateNormalize();
                break;
            case "del":
                del();
                break;
        }
    }
}
