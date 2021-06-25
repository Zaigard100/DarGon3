package com.zaig100.dg.objects.mobs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.objects.Obj;

public class Mob extends Obj {
    //TODO Mob(Абстракт) сделать рендер,фрейм,тег активейт
    public Mob(int x, int y, String tag) {
        super(x, y, tag);
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void frame() {

    }

    @Override
    public void tag_activate(String func) {

    }
}
