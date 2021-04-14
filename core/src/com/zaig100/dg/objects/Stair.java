package com.zaig100.dg.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.Main;
import com.zaig100.dg.screen.GameScreen;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Joystick;
import com.zaig100.dg.utils.Res;

public class Stair {

    int x, y;
    boolean flip_x, end;
    Player player;
    String next_path;
    boolean isExit = false;


    public Stair(int x, int y, boolean flip_x, String next_path, boolean end, Player player) {
        this.x = x;
        this.y = y;
        this.flip_x = flip_x;
        this.player = player;
        this.next_path = next_path;
        this.end = end;

    }

    public void render(SpriteBatch batch, Res res, Configuration config) {
        if(res.nextlv.isFlipX()!=flip_x) {
            res.nextlv.flip(flip_x, false);
        }
        batch.draw(res.nextlv, -player.get_wX() + (x + 3) * 16 * config.getScale(), -player.get_wY() + (y + 2) * 16 * config.getScale(), 16 * config.getScale(), 16 * config.getScale());
    }

    public void frame(Main m, Res res, Joystick joystick, boolean isPack) {
        joystick.frame(0,0);
        if ((Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) || (joystick.isUse())) {
            if ((x == player.getX()) && (y == player.getY())) {
                if (end) {
                    m.getScreen().dispose();
                    m.setScreen(new GameScreen(m));
                } else {
                    player.setIsSheld(false);
                    isExit = true;
                }
            }
        }
    }

    public void frame_isPack(Main m, Res res, Joystick joystick, boolean isPack, String packname, String derectory) {
        joystick.frame(0,0);
        if ((Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) || (joystick.isUse())) {
            if ((x == player.getX()) && (y == player.getY())) {
                if (end) {
                    m.setScreen(new GameScreen(m));
                } else {
                    player.setIsSheld(false);
                    isExit = true;
                }
            }
        }
    }

    public String getNext_path() {
        return next_path;
    }

    public boolean isExit() {
        return isExit;
    }
}
