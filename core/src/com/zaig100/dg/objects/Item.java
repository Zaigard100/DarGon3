package com.zaig100.dg.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

import java.util.Random;

public class Item {

    int x,y;
    Player player;
    int itemId;
    boolean active;
    public Item(int x, int y,int itemId, Player player){
        this.x = x;
        this.y = y;
        this.itemId = itemId;
        this.player = player;
        this.active = true;
        if (itemId == 0) {
            this.itemId = new Random().nextInt(2)+1;
        }
    }

    public  void render(SpriteBatch batch, Res res, Configuration config){
        if(active) {
            if (itemId == 1) {
                batch.draw(res.hp_potion, (x + 3.25f) * 16 * config.getScale()- player.get_wX(), (y + 2.25f) * 16 * config.getScale()-player.get_wY(), 16 * config.getScale() * 0.5f, 16 * config.getScale() * 0.5f);
            }
            if (itemId == 2) {
                batch.draw(res.sheld, (x + 3.25f) * 16 * config.getScale()- player.get_wX(), (y + 2.25f) * 16 * config.getScale()-player.get_wY(), 16 * config.getScale() * 0.5f, 16 * config.getScale() * 0.5f);
            }
            if (itemId == 3) {
                batch.draw(res.torch, (x + 3.25f) * 16 * config.getScale()- player.get_wX(), (y + 2.25f) * 16 * config.getScale()-player.get_wY(), 16 * config.getScale() * 0.5f, 16 * config.getScale() * 0.5f);
            }
        }
    }

    public void frame(){
        if(active) {
            if ((player.getX() == x) && (player.getY() == y)) {
                if(itemId==1) {
                    player.setPotion(player.getPotion() + 1);
                }
                if(itemId==2) {
                    player.setSheld(player.getSheld() + 1);
                }
                if(itemId==3) {
                    player.setTorch(player.getTorch() + 1);
                }
                active = false;
            }
        }
    }

}
