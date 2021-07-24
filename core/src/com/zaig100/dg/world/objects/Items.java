package com.zaig100.dg.world.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.contain.ItemC;
import com.zaig100.dg.world.elements.items.Item;
import com.zaig100.dg.world.elements.items.Poition;
import com.zaig100.dg.world.elements.items.Sheld;
import com.zaig100.dg.world.elements.items.Torch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Joystick;
import com.zaig100.dg.utils.Res;
import com.zaig100.dg.world.World;

public class Items extends Obj {


    public Item item;
    public boolean active;

    public  Items(ItemC contain){
        super(contain.getX(),contain.getY(), contain.getTag());
        type = ObjType.ITEM;
        item = Item.jsonToItem(contain.getItem());
    }

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
            item.renderInMap(batch, wX, wY);
        }
    }

    public void frame(){
        if(active) {
            if ((Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) || (Joystick.isUse())) {
                if ((World.player.getX() == x) && (World.player.getY() == y)) {
                    World.player.inventory.set(item);
                    active = false;
                }
            }
        }
        if (isMove()) {
            move();
        }
        if (!active) {
            del();
        }
    }

    @Override
    public void show_obj(SpriteBatch batch) {
        batch.draw(
                Res.green_obj,
                wX - World.player.get_wX(),
                wY - World.player.get_wY(),
                16 * Configuration.getScale(),
                16 * Configuration.getScale()
        );
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
            case "del":
                del();
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
