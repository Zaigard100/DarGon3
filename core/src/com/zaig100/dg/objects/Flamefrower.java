package com.zaig100.dg.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

public class Flamefrower {

    int x, y, max, angle, stage;
    int flame_stage,anim_stage=0;
    float timer = 0, fire_timer = 0;
    Player player;

    public Flamefrower(int x, int y, int max, int angle, Player player) {
        this.x = x;
        this.y = y;
        this.stage = 0;
        this.max = max;
        this.angle = angle;
        this.player = player;
    }

    public Flamefrower(int x, int y, int stage, int max, int angle, Player player) {
        this.x = x;
        this.y = y;
        this.stage = stage;
        this.max = max;
        this.angle = angle;
        this.player = player;
    }

    public void render(SpriteBatch batch, Res res, Configuration config) {
        batch.draw(res.flamethrowfer, (x + 3) * 16 * config.getScale() - player.get_wX(), (y + 2) * 16 * config.getScale() - player.get_wY(), 8 * config.getScale(), 8 * config.getScale(), 16 * config.getScale(), 16 * config.getScale(), 1, 1, 90 * angle);
        int dx, dy;
        dx = 0;
        dy = 0;
        if (angle == 0) {
            dx = 1;
        }
        if (angle == 1) {
            dy = 1;
        }
        if (angle == 2) {
            dx = -1;
        }
        if (angle == 3) {
            dy = -1;
        }
        flame_stage = 0;
        while (flame_stage < stage) {


            batch.draw(res.fire(anim_stage), ((x + dx * (flame_stage + 1)) + 3) * 16 * config.getScale() - player.get_wX(), ((y + dy * (flame_stage + 1)) + 2) * 16 * config.getScale() - player.get_wY(), 8 * config.getScale(), 8 * config.getScale(), 16 * config.getScale(), 16 * config.getScale(), 1, 1, 90 * (angle - 1));

            flame_stage++;
        }
    }

    public void frame() {




        int dx, dy;
        dx = 0;
        dy = 0;
        if (angle == 0) {
            dx = 1;
        }
        if (angle == 1) {
            dy = 1;
        }
        if (angle == 2) {
            dx = -1;
        }
        if (angle == 3) {
            dy = -1;
        }
        flame_stage = 0;
        while (flame_stage < stage) {

            if ((player.getX() == x + ((flame_stage + 1) * dx)) && (player.getY() ==  y+ ((flame_stage + 1) * dy))) {

                if(player.getHp()>0) {
                    player.setHp(player.getHp()-4);
                    player.setDamgeScr(0f,2);
                }
            }

            flame_stage++;

        }



    }





    public void tick(float second){
        timer+= Gdx.graphics.getDeltaTime();
        if(timer>=second) {

            stage++;
            timer =0;
            if (stage > max) {
                stage = 0;
            }
        }
    }
    public void tick1(float second){
        fire_timer+= Gdx.graphics.getDeltaTime();
        if(fire_timer>=second) {

            anim_stage++;
            fire_timer =0;
            if (anim_stage > 1) {
                anim_stage = 0;
            }
        }
    }


}
