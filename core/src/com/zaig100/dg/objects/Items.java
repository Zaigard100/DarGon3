package com.zaig100.dg.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.elements.items.Item;
import com.zaig100.dg.elements.items.Poition;
import com.zaig100.dg.elements.items.Sheld;
import com.zaig100.dg.elements.items.Torch;

public class Items extends Obj {


    public Item item;
    public boolean active;

    public Items(int x, int y, Item item, String tag) {
        super(x, y, tag);
        type = ObjType.ITEM;
        this.item = item;
        this.active = true;
    }

    public Items(int x, int y, Item item, boolean active, String tag) {
        super(x, y, tag);
        this.item = item;
        this.active = active;

    }

    public void render(SpriteBatch batch) {
        if (active) {

        }
    }

    public void frame(){
        if(active) {
            if ((Player.getX() == x) && (Player.getY() == y)) {
                Player.inventory.set(item);
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
            case "item":
                if (func.split(">")[1] == "poition") {
                    item = new Poition(1);
                }
                if (func.split(">")[1] == "sheld") {
                    item = new Sheld(1);
                }
                if (func.split(">")[1] == "torch") {
                    item = new Torch(1);
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

    public Item getItem() {
        return item;
    }

    public boolean isActive() {
        return active;
    }
}
