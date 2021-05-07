package com.zaig100.dg.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

public class Spinney {
    int x, y, wight, height, i, j;

    public Spinney(int x, int y, int wight, int height) {
        this.x = x;
        this.y = y;
        this.wight = Math.abs(wight);
        this.height = Math.abs(height);
    }

    public void render(SpriteBatch batch) {
        for (i = 0; i < height; i++) {
            for (j = 0; j < wight; j++) {
                batch.draw(Res.spinney, ((x + j + 3) * 16 * Configuration.getScale()) - Player.get_wX(), ((y + i + 2) * 16 * Configuration.getScale()) - Player.get_wY(), 16 * Configuration.getScale(), 16 * Configuration.getScale());
            }
        }
    }

}
