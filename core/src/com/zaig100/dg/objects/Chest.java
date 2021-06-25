package com.zaig100.dg.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.elements.items.Item;
import com.zaig100.dg.elements.items.Key;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Joystick;
import com.zaig100.dg.utils.Res;

import java.util.ArrayList;

public class Chest extends Obj {
    ArrayList<Item> items;
    boolean open;
    boolean isLooked;
    String keyTag;

    public Chest(int x, int y, ArrayList<Item> items, String tag) {
        super(x, y, tag);
        this.items = items;
        isLooked = false;
        open = false;
    }

    public Chest(int x, int y, ArrayList<Item> items, boolean isLooked, String keyTag, boolean open, String tag) {
        super(x, y, tag);
        this.items = items;
        this.isLooked = isLooked;
        this.keyTag = keyTag;
        this.open = open;
    }


    @Override
    public void render(SpriteBatch batch) {
        batch.draw(
                Res.chest(open),
                wX - Player.wX,
                wY - Player.wY,
                16 * Configuration.getScale(),
                16 * Configuration.getScale()
        );

        if ((Player.getX() - 1 == x || Player.getX() == x || Player.getX() + 1 == x) && (Player.getY() - 1 == y || Player.getY() == y || Player.getY() + 1 == y)) {
            if (isLooked) {
                Res.getFont(3).draw(
                        batch,
                        keyTag,
                        x * 16 * Configuration.getScale() - Player.get_wX() + 3 * 16 * Configuration.getScale(),
                        y * 16 * Configuration.getScale() - Player.get_wY() + 2 * 16 * Configuration.getScale()
                );
            } else {
                Res.getFont(3).draw(
                        batch,
                        "open",
                        x * 16 * Configuration.getScale() - Player.get_wX() + 3 * 16 * Configuration.getScale(),
                        y * 16 * Configuration.getScale() - Player.get_wY() + 2 * 16 * Configuration.getScale()
                );
            }
        }

    }

    @Override
    public void frame() {
        if ((Gdx.input.justTouched() && Joystick.isUse()) || (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))) {
            if ((Player.getX() - 1 == x || Player.getX() == x || Player.getX() + 1 == x) && (Player.getY() - 1 == y || Player.getY() == y || Player.getY() + 1 == y)) {
                if (!((Player.getX() == x && Player.getY() == y))) {
                    if (isLooked) {
                        for (Item item : Player.inventory.items) {
                            if (item.getType().equals(Item.ItemType.KEY)) {
                                if (((Key) item).getKeyTag().equals(keyTag)) {
                                    isLooked = true;
                                    break;
                                }
                            }
                        }
                    } else {
                        if (items != null) {
                            open = false;
                            for (Item item : items) {
                                Player.inventory.set(item);
                            }
                            items = null;
                        }
                    }
                }
            }
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
            case "KeyTag":
                keyTag = func.split(">")[1];
                break;
            case "Open":
                if (func.split(">")[1] == "++" || func.split(">")[1] == "--") {
                    open = !open;
                } else {
                    open = Boolean.getBoolean(func.split(">")[1]);
                }
                break;
            case "IsDoorOpen":
                if (func.split(">")[1] == "++" || func.split(">")[1] == "--") {
                    isLooked = !isLooked;
                } else {
                    isLooked = Boolean.getBoolean(func.split(">")[1]);
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
