package com.zaig100.dg.elements.items;

import com.badlogic.gdx.graphics.g2d.Batch;

import org.json.simple.JSONObject;

public abstract class Item {

    public enum ItemType {
        ITEM("item"),
        EMPTY("empty"),
        POITION("poition"),
        SHELD("sheld"),
        TORCH("torch"),
        KEY("key"),
        EGG("egg");

        String name;

        ItemType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    ItemType type = ItemType.ITEM;
    int count;

    public Item() {
        this.count = 1;
    }

    public Item(int count) {
        this.count = count;
    }

    public abstract void render(Batch batch, int x, int y);

    public abstract void renderInMap(Batch batch, int wX, int wY);

    public abstract boolean use();

    public abstract JSONObject toJson();

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ItemType getType() {
        return type;
    }
}
