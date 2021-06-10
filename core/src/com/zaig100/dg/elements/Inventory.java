package com.zaig100.dg.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.zaig100.dg.elements.items.Empty;
import com.zaig100.dg.elements.items.Item;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

import java.util.ArrayList;

public class Inventory {

    public ArrayList<Item> items;

    int x, y;

    public Inventory() {
        this.items = new ArrayList<>(12);
        for (int i = 0; i < 12; i++) {
            items.set(i, new Empty());
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
            items.get(i).render(batch, i - ((int) i / 4) * 4, (int) i / 4);
        }
    }

    public void frame() {
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
            if (items.get(x + y * 4).use()) {
                items.get(x + y * 4).setCount(items.get(x + y * 4).getCount() - 1);
            }
        }
    }


}
