package com.zaig100.dg.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

public class Crossbow {

    int x,y,arr_dx,arr_dy, angle;
    float timer=0;
    Arrow arr;
    public Crossbow(int x, int y, int arr_dx, int arr_dy, int angel) {

        this.x = x;
        this.y = y;
        this.arr_dx = arr_dx;
        this.arr_dy = arr_dy;
        this.angle = angel;
        arr = new Arrow(x, y, arr_dx, arr_dy, angle);

    }

    public void render(SpriteBatch batch) {

        batch.draw(Res.crossbow(arr.last()), ((x + 3) * 16 * Configuration.getScale()) - Player.get_wX(), ((y + 2) * 16 * Configuration.getScale()) - Player.get_wY(), 8 * Configuration.getScale(), 8 * Configuration.getScale(), 16 * Configuration.getScale(), 16 * Configuration.getScale(), 1, 1, angle);
        arr.render(batch);

    }

    public void frame() {
        arr.frame();
    }

    public void tick(float second) {
        arr.tick(second);
    }

}
