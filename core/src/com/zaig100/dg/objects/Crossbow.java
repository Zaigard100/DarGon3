package com.zaig100.dg.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

public class Crossbow {

    int x,y,arr_dx,arr_dy, angle;
    float timer=0;
    Arrow arr;
    Player player;
    public Crossbow(int x,int y, int arr_dx,int arr_dy,int angel,Player player){

        this.x = x;
        this.y = y;
        this.arr_dx = arr_dx;
        this.arr_dy = arr_dy;
        this.angle = angel;
        arr = new Arrow(x,y,arr_dx,arr_dy,angle,player);
        this.player = player;

    }
    public  void render(SpriteBatch batch, Res res, Configuration config){

        batch.draw(res.crossbow(arr.last()),((x + 3) * 16 * config.getScale()) - player.get_wX(), ((y + 2) * 16 * config.getScale())-player.get_wY(),8 * config.getScale(), 8 * config.getScale(), 16 * config.getScale(), 16 * config.getScale(), 1, 1, angle);
        arr.render(batch,res,config);

    }

    public void frame(){
        arr.frame();
    }

    public void tick(float second){
        arr.tick(second);
        timer+= Gdx.graphics.getDeltaTime();
        if(timer>second) {

            timer =0;
        }
    }

}
