package com.zaig100.dg.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.zaig100.dg.elements.items.Empty;
import com.zaig100.dg.elements.items.Item;
import com.zaig100.dg.elements.items.Poition;
import com.zaig100.dg.elements.items.Sheld;
import com.zaig100.dg.elements.items.Torch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Inventory {

    public ArrayList<Item> items;

    int x, y;
    int scrX, scrY;

    public Inventory() {
        this.items = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            items.add(new Empty());
        }
    }

    public void clear() {
        items.clear();
        for (int i = 0; i < 12; i++) {
            items.add(new Empty());
        }
    }

    public void render(Batch batch) {
        batch.draw(
                Res.inventar,
                8 * Configuration.getScale(),
                8 * Configuration.getScale(),
                5 * 16 * Configuration.getScale(),
                4 * 16 * Configuration.getScale()
        );
        for (int i = 0; i < 12; i++) {
            items.get(i).render(batch, i - ((int) (i / 4)) * 4, 2 - i / 4);
        }
    }

    void sensor(int sx, int sy) {
        if (Gdx.input.justTouched()) {

            scrX = (int) (Gdx.input.getX() - (sx + 16 * Configuration.getScale()));
            scrY = (int) ((Gdx.graphics.getHeight() - Gdx.input.getY()) - (sy + 16 * Configuration.getScale()));
            for (int i = 0; i < 4; i++) {
                if (i * 16 * Configuration.getScale() <= scrX && (i + 1) * 16 * Configuration.getScale() >= scrX) {
                    x = i;
                }
            } /// X
            for (int i = 0; i < 3; i++) {
                if (i * 16 * Configuration.getScale() <= scrY && (i + 1) * 16 * Configuration.getScale() >= scrY) {
                    y = 2 - i;
                }
            } /// Y

            if (items.get(x + y * 4).getCount() > 0) {
                if (items.get(x + y * 4).use()) {
                    items.get(x + y * 4).setCount(items.get(x + y * 4).getCount() - 1);
                    if (items.get(x + y * 4).getCount() <= 0) {
                        items.remove(x + y * 4);
                        items.add(new Empty());
                    }
                }
            } else {
                items.remove(x + y * 4);
                items.add(new Empty());
            }

        }
    }

    public void frame() {
        sensor((int) ((Gdx.graphics.getWidth() - 16 * 7 * Configuration.getScale()) / 2), (int) ((Gdx.graphics.getHeight() - 16 * 5 * Configuration.getScale()) / 2));
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            y--;
            if (y < 0) {
                y = 2;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            y++;
            if ((y > 2)) {
                y = 0;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            x--;
            if (x < 0) {
                x = 2;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            x++;
            if ((x > 2)) {
                x = 0;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (items.get(x + y * 4).getCount() > 0) {
                if (items.get(x + y * 4).use()) {
                    items.get(x + y * 4).setCount(items.get(x + y * 4).getCount() - 1);
                    if (items.get(x + y * 4).getCount() <= 0) {
                        items.remove(x + y * 4);
                        items.add(new Empty());
                    }
                }
            } else {
                items.remove(x + y * 4);
                items.add(new Empty());
            }
        }
    }

    public boolean set(Item item) {
        for (int i = 0; i < 12; i++) {
            if (items.get(i).getType() == item.getType()) {
                items.get(i).setCount(items.get(i).getCount() + 1);
                return true;
            }
        }
        for (int i = 0; i < 12; i++) {
            if (items.get(i) instanceof Empty) {
                items.set(i, item);
                return true;
            }
        }
        System.out.println("Inventory full");
        return false;
    }

    public boolean set(int index, Item item) {
        items.set(index, item);
        return true;
    }

    public JSONArray inventoryToJSON() {
        JSONArray arr = new JSONArray();
        for (Item item : items) {
            arr.add(item.toJson());
        }
        return arr;
    }

    public void jsonToInventory(JSONArray arr) {
        for (int i = 0; i < 12; i++) {
            String type = (String) (((JSONObject) arr.get(i)).get("Type"));
            if (type.equals("poition")) {
                items.set(i, new Poition((((Long) ((JSONObject) arr.get(i)).get("Count"))).intValue()));
            } else if (type.equals("sheld")) {
                items.set(i, new Sheld((((Long) ((JSONObject) arr.get(i)).get("Count"))).intValue()));
            } else if (type.equals("torch")) {
                items.set(i, new Torch((((Long) ((JSONObject) arr.get(i)).get("Count"))).intValue()));
            } else {
                items.set(i, new Empty());
            }
        }
    }

}
