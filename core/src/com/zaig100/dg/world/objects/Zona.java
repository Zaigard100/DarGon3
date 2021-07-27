package com.zaig100.dg.world.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;
import com.zaig100.dg.utils.contain.ButtonС;
import com.zaig100.dg.utils.contain.ZonaC;
import com.zaig100.dg.world.World;

public class Zona extends Obj {

    public enum ZonaType {

        HP_PIUS("hp+"),
        HP_MINUS("hp-");

        String name;

        ZonaType(String s) {
            name = s;
        }

        public String getName() {
            return name;
        }
    }

    int wight, height;
    ZonaType zonaType;
    float tick, timer;
    int i, j;


    public Zona(ZonaC contain){
        super(contain.getX(),contain.getY(), contain.getTag());
        type = ObjType.ZONA;
        zonaType = contain.getType();
        wight = contain.getWight();
        height = contain.getHeight();
        tick = contain.getTick();
        this.contain = contain;
    }

    public void load(ZonaC contain){
        x = contain.getX();
        y = contain.getY();
        tag = contain.getTag();
        type = ObjType.ZONA;
        zonaType = contain.getType();
        wight = contain.getWight();
        height = contain.getHeight();
        this.contain = contain;
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void frame() {
        tick(tick);
    }

    @Override
    public void show_obj(SpriteBatch batch) {
        switch (zonaType) {
            case HP_PIUS:
                for (i = 0; i < height; i++) {
                    for (j = 0; j < wight; j++) {
                        batch.draw(
                                Res.green_sm_obj,
                                wX + j * 16 * Configuration.getScale() - World.player.get_wX(),
                                wY + i * 16 * Configuration.getScale() - World.player.get_wY(),
                                16 * Configuration.getScale(),
                                16 * Configuration.getScale()
                        );
                    }
                }
                break;
            case HP_MINUS:
                for (i = 0; i < height; i++) {
                    for (j = 0; j < wight; j++) {
                        batch.draw(
                                Res.red_sm_obj,
                                wX + j * 16 * Configuration.getScale() - World.player.get_wX(),
                                wY + i * 16 * Configuration.getScale() - World.player.get_wY(),
                                16 * Configuration.getScale(),
                                16 * Configuration.getScale()
                        );
                    }
                }
                break;
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
            case "Wight":
                if (func.split(">")[1] == "++") {
                    wight++;
                } else if (func.split(">")[1] == "--") {
                    wight--;
                } else {
                    wight = Integer.parseInt((func.split(">")[1]));
                }
                break;
            case "Height":
                if (func.split(">")[1] == "++") {
                    height++;
                } else if (func.split(">")[1] == "--") {
                    height--;
                } else {
                    height = Integer.parseInt((func.split(">")[1]));
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

    void tick(float second) {
        timer += Gdx.graphics.getDeltaTime();
        if (timer >= second) {
            timer = 0;
            if ((World.player.getX() >= x) && (World.player.getX() <= x + wight) && (World.player.getY() >= y) && (World.player.getY() <= y + height)) {

                switch (zonaType) {
                    case HP_PIUS:
                        World.player.hp += 1;
                        break;
                    case HP_MINUS:
                        World.player.setDamgeScr(0f,1);
                        World.player.hp -= 1;
                        break;
                }

            } else {
                timer = 0;
            }
        }
    }

    public float getTick() {
        return tick;
    }

    public void setTick(float tick) {
        this.tick = tick;
    }
}
