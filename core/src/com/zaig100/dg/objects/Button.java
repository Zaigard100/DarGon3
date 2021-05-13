package com.zaig100.dg.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.screen.PlayScreen;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Joystick;
import com.zaig100.dg.utils.Res;

public class Button extends Obj {
    String[] func;
    PlayScreen PS;
    boolean active = true;

    public Button(int x, int y, String[] func, String tag) {
        super(x, y, tag);
        this.func = func;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(Res.button, ((x + 3) * 16 * Configuration.getScale()) - Player.get_wX(), ((y + 2) * 16 * Configuration.getScale()) - Player.get_wY(), 16 * Configuration.getScale(), 16 * Configuration.getScale());
    }

    @Override
    public void frame() {

        Joystick.frame(0, 0);
        if ((Player.getX() == x) && (Player.getY() == y) && active) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || (Joystick.isUse() && Gdx.input.justTouched())) {
                for (int i = 0; i < func.length; i++) {
                    System.out.println(i);
                    PS.doFunc(func[i]);
                }
                active = false;
            }
        }

        if ((Player.getX() != x) && (Player.getY() != y)) {
            active = true;
        }

    }

    public Button setPlayScreen(PlayScreen PS) {
        this.PS = PS;
        return this;
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
        }
    }
}

