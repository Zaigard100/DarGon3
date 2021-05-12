package com.zaig100.dg.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

public class Crossbow extends Obj {

    int arr_dx, arr_dy, angle;
    float timer = 0;
    Arrow arr;

    public Crossbow(int x, int y, int arr_dx, int arr_dy, int angel, String tag) {

        super(x, y, tag);
        this.arr_dx = arr_dx;
        this.arr_dy = arr_dy;
        this.angle = angel;
        arr = new Arrow(x, y, arr_dx, arr_dy, angle, tag + "Arr1");

    }

    @Override
    public void render(SpriteBatch batch) {

        batch.draw(Res.crossbow(arr.last()), ((x + 3) * 16 * Configuration.getScale()) - Player.get_wX(), ((y + 2) * 16 * Configuration.getScale()) - Player.get_wY(), 8 * Configuration.getScale(), 8 * Configuration.getScale(), 16 * Configuration.getScale(), 16 * Configuration.getScale(), 1, 1, angle);
        arr.render(batch);

    }

    @Override
    public void frame() {
        arr.frame();
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
            case "DX":
                if (func.split(">")[1] == "++") {
                    arr_dx++;
                } else if (func.split(">")[1] == "--") {
                    arr_dx--;
                } else {
                    arr_dx = Integer.parseInt((func.split(">")[1]));
                }
                break;
            case "DY":
                if (func.split(">")[1] == "++") {
                    arr_dy++;
                } else if (func.split(">")[1] == "--") {
                    arr_dy--;
                } else {
                    arr_dy = Integer.parseInt((func.split(">")[1]));
                }
                break;
            case "Angle":
                if (func.split(">")[1] == "++") {
                    angle++;
                } else if (func.split(">")[1] == "--") {
                    angle--;
                } else {
                    angle = Integer.parseInt((func.split(">")[1]));
                }
                break;
        }
    }


}
