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
    float timer =0;

    Map map;
    Player player;
    public Arrow(int crossbow_x,int crossbow_y,int dx, int dy,int angle,Player player){

        x = crossbow_x + dx;
        y = crossbow_y + dy;
        this.crossbow_x =crossbow_x;
        this.crossbow_y = crossbow_y;
        this.dx = dx;
        this.dy = dy;
        this.angle = angle;
        map = player.map;
        this.player =player;

    }
    public  void render(SpriteBatch batch, Res res, Configuration config){

        batch.draw(res.arrow,
                ((x + 3) * 16 * config.getScale()) - player.get_wX(),
                ((y + 2) * 16 * config.getScale())-player.get_wY(),
                8 * config.getScale(),
                8 * config.getScale(),
                16 * config.getScale(),
                16 * config.getScale(),
                1,
                1,
                angle);


    }

    public void frame(){

        if((x == player.getX())&&(y == player.getY())){
            if(player.getHp()>0) {
                if(player.isSheld()){
                    player.setHp(player.getHp() - 1);
                }else {
                    player.setHp(player.getHp() - 2);
                }
                player.setDamgeScr(0f,3);
            }
            x = crossbow_x ;
            y = crossbow_y ;
        }

    }

    public void tick(float second){
        timer+= Gdx.graphics.getDeltaTime();
        if(timer>=second) {

            x+=dx;
            y+=dy;

            if (((x < 0) || (x >= map.getMapWidht())) || ((y < 0) || (y >= map.getMapHeight()))) {
                x = crossbow_x + dx;
                y = crossbow_y + dy;
            }

            timer =0;
        }
    }

    public boolean last(){
        return ((x < 0) || (x+dx >= map.getMapWidht())) || ((y < 0) || (y+dy >= map.getMapHeight()));
    }

}
