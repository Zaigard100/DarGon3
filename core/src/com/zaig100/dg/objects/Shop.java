package com.zaig100.dg.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.elements.items.Item;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

public class Shop extends Obj {

    Item[] item;
    int[] cost;

    public Shop(int x, int y, Item[] item, int[] cost, String tag) {
        super(x, y, tag);

    }

    //TODO Магазин сделать фрейм,тег активейт
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(
                Res.shop,
                wX - Player.get_wX(),
                wY - Player.get_wY(),
                16 * Configuration.getScale(),
                16 * Configuration.getScale()
        );
    }

    @Override
    public void frame() {
        if ((Player.getX() - 1 == x || Player.getX() == x || Player.getX() + 1 == x) && (Player.getY() - 1 == y || Player.getY() == y || Player.getY() + 1 == y)) {
            if (!((Player.getX() == x && Player.getY() == y))) {
                if (!Player.isShop) {
                    Player.map.setShop(this);
                }
            }
        }
    }

    @Override
    public void show_obj(SpriteBatch batch) {
        batch.draw(
                Res.green_obj,
                wX - Player.get_wX(),
                wY - Player.get_wY(),
                16 * Configuration.getScale(),
                16 * Configuration.getScale()
        );
    }

    @Override
    public void tag_activate(String func) {

    }

    public Item[] getItem() {
        return item;
    }

    public int[] getCost() {
        return cost;
    }
}
