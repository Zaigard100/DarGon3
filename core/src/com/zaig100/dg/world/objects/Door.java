package com.zaig100.dg.world.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.elements.items.Item;
import com.zaig100.dg.elements.items.Key;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Joystick;
import com.zaig100.dg.utils.Res;
import com.zaig100.dg.world.World;

public class Door extends Obj {

    boolean isDoorOpen;
    String keyTag;
    int facing;
    int doorTile;


    public Door(int x, int y, boolean isDoorOpen, String keyTag, int facing, String tag) {
        super(x, y, tag);
        this.isDoorOpen = isDoorOpen;
        this.keyTag = keyTag;
        this.facing = facing;
        type = ObjType.DOOR;
        doorTile = World.map.map[x + y * World.map.getMapWidht()];
        if (!isDoorOpen) {
            World.map.map[x + y * World.map.getMapWidht()] = 10;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!isDoorOpen) {
            batch.draw(
                    Res.door(facing),
                    wX - World.player.get_wX(),
                    wY - World.player.get_wY(),
                    16 * Configuration.getScale(),
                    32 * Configuration.getScale()
            );
            if ((World.player.getX() - 1 == x || World.player.getX() == x || World.player.getX() + 1 == x) && (World.player.getY() - 1 == y || World.player.getY() == y || World.player.getY() + 1 == y)) {
                Res.getFont(3).draw(batch,
                        keyTag,
                        x * 16 * Configuration.getScale() - World.player.get_wX() + 3 * 16 * Configuration.getScale(),
                        y * 16 * Configuration.getScale() - World.player.get_wY() + 2 * 16 * Configuration.getScale()
                );
            }
        }
    }

    @Override
    public void frame() {
        if ((Gdx.input.justTouched() && Joystick.isUse()) || (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))) {
            if ((World.player.getX() - 1 == x || World.player.getX() == x || World.player.getX() + 1 == x) && (World.player.getY() - 1 == y || World.player.getY() == y || World.player.getY() + 1 == y)) {
                if (!((World.player.getX() == x && World.player.getY() == y))) {
                    for (Item item : World.player.inventory.items) {
                        if (item.getType().equals(Item.ItemType.KEY)) {
                            if (((Key) item).getKeyTag().equals(keyTag)) {
                                isDoorOpen = !isDoorOpen;
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (isDoorOpen) {
            World.map.map[x + y * World.map.getMapWidht()] = doorTile;
        } else {
            World.map.map[x + y * World.map.getMapWidht()] = 10;
        }

    }

    @Override
    public void show_obj(SpriteBatch batch) {
        batch.draw(
                Res.blue_obj,
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
            case "KeyTag":
                keyTag = func.split(">")[1];
                break;
            case "Facing":
                if (func.split(">")[1] == "++") {
                    facing++;
                } else if (func.split(">")[1] == "--") {
                    facing--;
                } else {
                    facing = Integer.parseInt((func.split(">")[1]));
                }
                break;
            case "IsDoorOpen":
                if (func.split(">")[1] == "++") {
                    isDoorOpen = !isDoorOpen;
                } else if (func.split(">")[1] == "--") {
                    isDoorOpen = !isDoorOpen;
                } else {
                    isDoorOpen = Boolean.getBoolean(func.split(">")[1]);
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
}
