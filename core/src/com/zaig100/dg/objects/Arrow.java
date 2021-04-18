package com.zaig100.dg.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

public class Arrow {

    int x;
    int y;
    int crossbow_x;
    int crossbow_y;
    int dx;
    int dy;
    int angle;
    float timer = 0;

    //static Player player;
    public Arrow(int crossbow_x, int crossbow_y, int dx, int dy, int angle) {

        x = crossbow_x + dx;
        y = crossbow_y + dy;
        this.crossbow_x = crossbow_x;
        this.crossbow_y = crossbow_y;
        this.dx = dx;
        this.dy = dy;
        this.angle = angle;

    }

    public void render(SpriteBatch batch) {

        batch.draw(Res.arrow,
                ((x + 3) * 16 * Configuration.getScale()) - Player.get_wX(),
                ((y + 2) * 16 * Configuration.getScale()) - Player.get_wY(),
                8 * Configuration.getScale(),
                8 * Configuration.getScale(),
                16 * Configuration.getScale(),
                16 * Configuration.getScale(),
                1,
                1,
                angle);

    }

    public void frame() {

        if ((x == Player.getX()) && (y == Player.getY())) {
            if (Player.getHp() > 0) {
                if (Player.isSheld()) {
                    Player.setHp(Player.getHp() - 1);
                } else {
                    Player.setHp(Player.getHp() - 2);
                }
                Player.setDamgeScr(0f, 3);
            }
            x = crossbow_x;
            y = crossbow_y;
        }

    }

    public void tick(float second){
        timer+= Gdx.graphics.getDeltaTime();
        if(timer>=second) {

            x+=dx;
            y+=dy;

            if (((x < 0) || (x >= Player.getMap().getMapWidht())) || ((y < 0) || (y >= Player.getMap().getMapHeight()))) {
                x = crossbow_x + dx;
                y = crossbow_y + dy;
            }

            timer =0;
        }
    }

    public boolean last() {
        return ((x < 0) || (x + dx >= Player.getMap().getMapWidht())) || ((y < 0) || (y + dy >= Player.getMap().getMapHeight()));

    }

}
