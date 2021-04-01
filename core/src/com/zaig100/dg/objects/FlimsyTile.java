package com.zaig100.dg.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

public class FlimsyTile {

    int x, y;
    Player player;
    int crashed_lvl;

    float timer = 0;

    public FlimsyTile(int x, int y, Player player) {
        this.x = x;
        this.y = y;
        this.player = player;
        crashed_lvl = 3;
    }

    public void render(SpriteBatch batch, Res res, Configuration config) {
        if (crashed_lvl == 2) {
            batch.draw(res.clvl2, x * 16 * config.getScale(), y * 16 * config.getScale(), 16 * config.getScale(), 16 * config.getScale());
        }
        if (crashed_lvl == 1) {
            batch.draw(res.clvl1, x * 16 * config.getScale(), y * 16 * config.getScale(), 16 * config.getScale(), 16 * config.getScale());
        }
        if (crashed_lvl <= 0) {
            batch.draw(res.clvl0, x * 16 * config.getScale(), y * 16 * config.getScale(), 16 * config.getScale(), 16 * config.getScale());
        }
    }

    public void frame() {
        if ((x == player.getX()) && (y == player.getY())) {
            if (crashed_lvl <= 0) {
                player.setDamgeScr(8, 4);
            }
        }
    }


    public void tick(float second) {
        if ((x == player.getX()) && (y == player.getY())) {

            if (timer < 0 && crashed_lvl > 0) {
                crashed_lvl--;
                timer = second;
            }
        }
        timer -= Gdx.graphics.getDeltaTime();
    }

}
