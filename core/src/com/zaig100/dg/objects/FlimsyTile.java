package com.zaig100.dg.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

public class FlimsyTile {

    int x, y;
    int crashed_lvl;

    float timer = 0;

    public FlimsyTile(int x, int y, int stage) {
        this.x = x;
        this.y = y;
        crashed_lvl = 3 - stage;
    }

    public void render(SpriteBatch batch) {
        if (crashed_lvl == 2) {
            batch.draw(Res.clvl2, ((x + 3) * 16 * Configuration.getScale()) - Player.get_wX(), ((y + 2) * 16 * Configuration.getScale()) - Player.get_wY(), 16 * Configuration.getScale(), 16 * Configuration.getScale());
        }
        if (crashed_lvl == 1) {
            batch.draw(Res.clvl1, ((x + 3) * 16 * Configuration.getScale()) - Player.get_wX(), ((y + 2) * 16 * Configuration.getScale()) - Player.get_wY(), 16 * Configuration.getScale(), 16 * Configuration.getScale());
        }
        if (crashed_lvl <= 0) {
            batch.draw(Res.clvl0, ((x + 3) * 16 * Configuration.getScale()) - Player.get_wX(), ((y + 2) * 16 * Configuration.getScale()) - Player.get_wY(), 16 * Configuration.getScale(), 16 * Configuration.getScale());
        }
    }

    public void frame() {
        if ((x == Player.getX()) && (y == Player.getY())) {
            if (Player.getHp() > 0) {
                if (crashed_lvl < 1) {
                    Player.setHp(0);
                    Player.setDamgeScr(0f, 4);
                }
            }
        }
    }


    public void tick(float second) {
        if ((x == Player.getX()) && (y == Player.getY())) {
            if (Player.getHp() > 0) {
                if (timer < 0 && crashed_lvl > 0) {
                    crashed_lvl--;
                    timer = second;
                }
            }
        }
        timer -= Gdx.graphics.getDeltaTime();
    }

}
