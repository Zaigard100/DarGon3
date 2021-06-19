package com.zaig100.dg.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

public class Spike extends Obj {
    public boolean active, trigered;
    public float tick_sec;
    private float timer, timer1;

    public Spike(int x, int y, boolean active, float tick_sec, String tag) {
        super(x, y, tag);
        type = ObjType.SPIKE;
        this.tick_sec = tick_sec;
        this.active = active;
    }

    public void render(SpriteBatch batch) {
        if (active) {
            batch.draw(Res.spike_1,
                    wX - Player.get_wX() - Configuration.getScale(),
                    wY - Player.get_wY() + Configuration.getScale(),
                    16 * Configuration.getScale(),
                    16 * Configuration.getScale()
            );
        } else {
            batch.draw(Res.spike_0,
                    wX - Player.get_wX() - Configuration.getScale(),
                    wY - Player.get_wY() + Configuration.getScale(),
                    16 * Configuration.getScale(),
                    16 * Configuration.getScale()
            );
        }
    }

    public void frame() {
        if ((x == Player.getX()) && (y == Player.getY())) {
            if (Player.getHp() > 0) {
                trigered = true;
                if (active) {
                    Player.setHp(0);
                    Player.setDamgeScr(0f, 4);
                }
            }
        }
        tick(tick_sec);
        if (isMove()) {
            move();
        }
    }


    public void tick(float second) {
        if (trigered) {
            if (timer > second && !active) {
                active = true;
                timer = 0;
                timer1 = 0;
                trigered = false;
            } else {
                timer += Gdx.graphics.getDeltaTime();
            }

        } else {
            if (timer1 > 2 * second && active) {
                active = false;
                timer1 = 0;
                timer = 0;
            } else {
                timer1 += Gdx.graphics.getDeltaTime();
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
            case "Active":
                if (func.split(">")[1] == "++" || func.split(">")[1] == "--") {
                    active = !active;
                } else {
                    active = Boolean.parseBoolean(func.split(">")[1]);
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
