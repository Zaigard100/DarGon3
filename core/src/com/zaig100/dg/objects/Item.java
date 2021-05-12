package com.zaig100.dg.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

import java.util.Random;

public class Item extends Obj {


    int itemId;
    boolean active;

    public Item(int x, int y, int itemId, String tag) {
        super(x, y, tag);
        this.itemId = itemId;
        this.active = true;
        if (itemId == 0) {
            this.itemId = new Random().nextInt(2) + 1;
        }
    }

    public void render(SpriteBatch batch) {
        if (active) {
            if (itemId == 1) {
                batch.draw(Res.hp_potion, (x + 3.25f) * 16 * Configuration.getScale() - Player.get_wX(), (y + 2.25f) * 16 * Configuration.getScale() - Player.get_wY(), 16 * Configuration.getScale() * 0.5f, 16 * Configuration.getScale() * 0.5f);
            }
            if (itemId == 2) {
                batch.draw(Res.sheld, (x + 3.25f) * 16 * Configuration.getScale() - Player.get_wX(), (y + 2.25f) * 16 * Configuration.getScale() - Player.get_wY(), 16 * Configuration.getScale() * 0.5f, 16 * Configuration.getScale() * 0.5f);
            }
            if (itemId == 3) {
                batch.draw(Res.torch, (x + 3.25f) * 16 * Configuration.getScale() - Player.get_wX(), (y + 2.25f) * 16 * Configuration.getScale() - Player.get_wY(), 16 * Configuration.getScale() * 0.5f, 16 * Configuration.getScale() * 0.5f);
            }
        }
    }

    public void frame(){
        if(active) {
            if ((Player.getX() == x) && (Player.getY() == y)) {
                if (itemId == 1) {
                    Player.setPotion(Player.getPotion() + 1);
                }
                if (itemId == 2) {
                    Player.setSheld(Player.getSheld() + 1);
                }
                if (itemId == 3) {
                    Player.setTorch(Player.getTorch() + 1);
                }
                active = false;
            }
        }
    }

}
