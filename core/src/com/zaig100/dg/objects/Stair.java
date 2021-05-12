package com.zaig100.dg.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Joystick;
import com.zaig100.dg.utils.Res;

public class Stair extends Obj {

    boolean flip_x, end;
    String next_path;
    boolean isExit = false;


    public Stair(int x, int y, boolean flip_x, String next_path, boolean end, String tag) {
        super(x, y, tag);
        this.flip_x = flip_x;
        this.next_path = next_path;
        this.end = end;

    }

    public void render(SpriteBatch batch) {
        if (Res.nextlv.isFlipX() != flip_x) {
            Res.nextlv.flip(flip_x, false);
        }
        batch.draw(Res.nextlv, -Player.get_wX() + (x + 3) * 16 * Configuration.getScale(), -Player.get_wY() + (y + 2) * 16 * Configuration.getScale(), 16 * Configuration.getScale(), 16 * Configuration.getScale());
    }

    @Override
    public void frame() {
        if ((Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) || (Joystick.isUse())) {
            if ((x == Player.getX()) && (y == Player.getY())) {
                Player.setIsSheld(false);
                isExit = true;
            }
        }
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
            case "End":
                if (func.split(">")[1] == "++" || func.split(">")[1] == "--") {
                    end = !end;
                } else {
                    end = Boolean.parseBoolean((func.split(">")[1]));
                }
                break;
            case "FlipX":
                if (func.split(">")[1] == "++" || func.split(">")[1] == "--") {
                    flip_x = !flip_x;
                } else {
                    flip_x = Boolean.parseBoolean((func.split(">")[1]));
                }
                break;
            case "Next":
                next_path = func.split(">")[1];
                break;
        }
    }

    public String getNext_path() {
        return next_path;
    }

    public boolean isExit() {
        return isExit;
    }

    public boolean isEnd() {
        return end;
    }

}
