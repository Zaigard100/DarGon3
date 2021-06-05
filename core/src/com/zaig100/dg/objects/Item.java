package com.zaig100.dg.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

import java.util.Random;

public class Item extends Obj {


    public int itemId;
    public boolean active;

    public Item(int x, int y, int itemId, String tag) {
        super(x, y, tag);
        type = ObjType.ITEM;
        this.itemId = itemId;
        this.active = true;
        if (itemId == 0) {
            this.itemId = new Random().nextInt(2) + 1;
        }
    }

    public Item(int x, int y, int itemId, boolean active, String tag) {
        super(x, y, tag);
        this.itemId = itemId;
        this.active = active;
        if (itemId == 0) {
            this.itemId = new Random().nextInt(2) + 1;
        }
    }

    public void render(SpriteBatch batch) {
        if (active) {
            if (itemId == 1) {
                batch.draw(Res.hp_potion, wX + (3.25f * 16 * Configuration.getScale()) - Player.get_wX(), wY + (2.25f * 16 * Configuration.getScale()) - Player.get_wY(), 16 * Configuration.getScale() * 0.5f, 16 * Configuration.getScale() * 0.5f);
            }
            if (itemId == 2) {
                batch.draw(Res.sheld, wX + (3.25f * 16 * Configuration.getScale()) - Player.get_wX(), wY + (2.25f * 16 * Configuration.getScale()) - Player.get_wY(), 16 * Configuration.getScale() * 0.5f, 16 * Configuration.getScale() * 0.5f);
            }
            if (itemId == 3) {
                batch.draw(Res.torch, wX + (3.25f * 16 * Configuration.getScale()) - Player.get_wX(), wY + (2.25f * 16 * Configuration.getScale()) - Player.get_wY(), 16 * Configuration.getScale() * 0.5f, 16 * Configuration.getScale() * 0.5f);
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
        if (isMove()) {
            move();
        }
    }

    @Override
    public void tag_activate(String func) {
        switch (func.split(">")[0]) {
            case "X":
                if (func.split(">")[1] == "++") {
                    x++;
                } else if (func.split(">")[1] == "--") {
                    x--;
                } else {
                    x = Integer.parseInt((func.split(">")[1]));
                }
                break;
            case "Y":
                if (func.split(">")[1] == "++") {
                    y++;
                } else if (func.split(">")[1] == "--") {
                    y--;
                } else {
                    y = Integer.parseInt((func.split(">")[1]));
                }
                break;
            case "ID":
                if (func.split(">")[1] == "++") {
                    itemId++;
                } else if (func.split(">")[1] == "--") {
                    itemId--;
                } else {
                    itemId = Integer.parseInt((func.split(">")[1]));
                }
                break;
            case "Active":
                if (func.split(">")[1] == "++" || func.split(">")[1] == "--") {
                    active = !active;
                } else {
                    active = Boolean.parseBoolean((func.split(">")[1]));
                }
                break;
            case "cordN":
                cordinateNormalize();
                break;
        }
    }

    public int getItemId() {
        return itemId;
    }

    public boolean isActive() {
        return active;
    }
}
