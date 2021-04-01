package com.zaig100.dg.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Joystick;
import com.zaig100.dg.utils.Res;

public class Teleport {
    int x,y,tx,ty;
    boolean hide;
    static Player player;

    public  Teleport(int x,int y,int tx,int ty,Player player){
        this.x = x;
        this.y = y;
        this.tx = tx;
        this.ty = ty;
        this.hide = true;
        this.player = player;
    }
    public  Teleport(int x,int y,int tx,int ty,boolean hide,Player player){
        this.x = x;
        this.y = y;
        this.tx = tx;
        this.ty = ty;
        this.hide = hide;
        this.player = player;
    }



    public void render(SpriteBatch batch, Res res, Configuration config){
        batch.draw(res.teleport,((x + 3) * 16 * config.getScale()) - player.get_wX(), ((y + 2) * 16 * config.getScale())-player.get_wY(), 16 * config.getScale(), 16 * config.getScale());
    }
    public void frame(Joystick joystick) {
        joystick.frame(0,0);
        if((player.getX() == x)&&(player.getY() == y)){
            if ((Gdx.input.isKeyJustPressed(Input.Keys.SPACE))||(joystick.isUse())) {
                player.teleport(tx, ty);
            }
        }
    }
}
