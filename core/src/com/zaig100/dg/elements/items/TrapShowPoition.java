package com.zaig100.dg.elements.items;

import com.badlogic.gdx.graphics.g2d.Batch;

import org.json.simple.JSONObject;

public class TrapShowPoition extends Item {

    //TODO Зелье показа ловушек сделать рендер,мап рендер,юсе,жейсон
    @Override
    public void render(Batch batch, int x, int y) {

    }

    @Override
    public void renderInMap(Batch batch, int wX, int wY) {

    }

    @Override
    public boolean use() {
        return false;
    }

    @Override
    public JSONObject toJson() {
        return null;
    }
}
