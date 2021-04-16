package com.zaig100.dg.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Joystick;
import com.zaig100.dg.utils.Res;

import java.util.Random;

public class Player {

    static int x;
    static int y;
    static int oldX;
    static int oldY;
    static int wX;
    static int wY;
    static int hp;
    static int potion;
    static int sheld;
    static int torch;
    static int stage = 0;
    static float timer = 0;
    static boolean walked = false, walked_anim = false, flip = false, bag_opened = false;
    static int sx, sy;
    static int wasted_id = 0;

    static boolean[] slots = new boolean[3];
    static boolean isSheld = false;

    static Random random = new Random();

    static Map map;
    static float damgeScr = 100;
    static private int getYP;


    public Player(int x, int y, Map map) {
        Player.x = x;
        Player.y = y;
        Player.oldX = x;
        Player.oldY = y;
        Player.wX = (int) (x * 16 * Configuration.getScale());
        Player.wY = (int) (y * 16 * Configuration.getScale());
        Player.hp = 4;
        Player.potion = 3;
        Player.sheld = 2;
        Player.torch = 1;
        Player.map = map;
    }

    public Player(int x, int y, Map map, int hp, int potion, int sheld, int torch) {
        Player.x = x;
        Player.y = y;
        Player.oldX = x;
        Player.oldY = y;
        Player.wX = x * 16 * (int) Configuration.getScale();
        Player.wY = y * 16 * (int) Configuration.getScale();
        Player.map = map;
        Player.potion = potion;
        Player.sheld = sheld;
        Player.torch = torch;
        Player.hp = hp;

    }

    static public void render(SpriteBatch batch) {
        if (getHp() > 0) {
            batch.draw(Res.hero(flip, walked_anim, stage), 16 * Configuration.getScale() * 3, 16 * Configuration.getScale() * 2, 16 * Configuration.getScale(), 16 * Configuration.getScale());
            if (isSheld) {
                if (flip) {
                    batch.draw(Res.sheld, 16 * Configuration.getScale() * 3.25f, 16 * Configuration.getScale() * 2, 16 * Configuration.getScale() * 0.75f, 16 * Configuration.getScale() * 0.75f);
                } else {
                    batch.draw(Res.sheld, 16 * Configuration.getScale() * 3, 16 * Configuration.getScale() * 2, 16 * Configuration.getScale() * 0.75f, 16 * Configuration.getScale() * 0.75f);
                }
            }
        } else {
            if (wasted_id == 0) {
                batch.draw(Res.hero(flip, walked_anim, stage), 16 * Configuration.getScale() * 3, 16 * Configuration.getScale() * 2, 16 * Configuration.getScale(), 16 * Configuration.getScale());
            }
            if(wasted_id==1){
                batch.draw(Res.amonghero, 16 * Configuration.getScale() * 3, 16 * Configuration.getScale() * 2, 16 * Configuration.getScale(), 16 * Configuration.getScale());
            }
            if (wasted_id == 2) {
                batch.draw(Res.firedhero, 16 * Configuration.getScale() * 3, 16 * Configuration.getScale() * 2, 16 * Configuration.getScale(), 16 * Configuration.getScale());
            }
            if (wasted_id == 3) {
                batch.draw(Res.shotedhero, 16 * Configuration.getScale() * 3, 16 * Configuration.getScale() * 2, 16 * Configuration.getScale(), 16 * Configuration.getScale());
            }
            //wasted_id == 4: Texture = null!!
        }
        if (damgeScr < 0.5f) {
            batch.draw(Res.damage, 0, 0, 112 * Configuration.getScale(), 80 * Configuration.getScale());
            damgeScr = damgeScr + Gdx.graphics.getDeltaTime();
        }
    }

    static public void render_bag(SpriteBatch batch, BitmapFont font, Joystick joystick) {
        batch.draw(Res.bag, 6 * 16 * Configuration.getScale(), 4 * 16 * Configuration.getScale(), 16 * Configuration.getScale(), 16 * Configuration.getScale());
        if (bag_opened) {
            bag_use(joystick);
            if (hp > 0) {
                batch.draw(Res.HP(hp), 6 * 16 * Configuration.getScale(), 3 * 16 * Configuration.getScale(), 16 * Configuration.getScale(), 16 * Configuration.getScale());
            }
            batch.draw(Res.hp_potion, 6 * 16 * Configuration.getScale(), 2 * 16 * Configuration.getScale(), 16 * Configuration.getScale(), 16 * Configuration.getScale());
            font.draw(batch, String.valueOf(potion), 6 * 16 * Configuration.getScale(), 2 * 16 * Configuration.getScale() + 4 * Configuration.getScale());
            batch.draw(Res.sheld, 6 * 16 * Configuration.getScale(), 1 * 16 * Configuration.getScale(), 16 * Configuration.getScale(), 16 * Configuration.getScale());
            font.draw(batch, String.valueOf(sheld), 6 * 16 * Configuration.getScale(), 1 * 16 * Configuration.getScale() + 4 * Configuration.getScale());
            batch.draw(Res.torch, 6 * 16 * Configuration.getScale(), 0 * 16 * Configuration.getScale(), 16 * Configuration.getScale(), 16 * Configuration.getScale());
            font.draw(batch, String.valueOf(torch), 6 * 16 * Configuration.getScale(), 0 * 16 * Configuration.getScale() + 4 * Configuration.getScale());
        }
    }


    static public void frame(Joystick joystick) {
        joystick.frame((int) ((Gdx.graphics.getWidth() - 16 * 7 * Configuration.getScale()) / 2), (int) ((Gdx.graphics.getHeight() - 16 * 5 * Configuration.getScale()) / 2));
        if (hp > 0) {
            if ((Gdx.input.isKeyPressed(Input.Keys.W)) || (joystick.isUp())) {
                if (!walked) {
                    oldY = y + 1;
                    // System.out.println("W");
                }
            }
            if ((Gdx.input.isKeyPressed(Input.Keys.S)) || (joystick.isDown())) {
                if (!walked) {
                    oldY = y - 1;
                    //System.out.println("S");
                }
            }
            if ((Gdx.input.isKeyPressed(Input.Keys.A)) || (joystick.isLeft())) {
                if (!walked) {
                    oldX = x - 1;
                    // System.out.println("A");
                }

            }
            if ((Gdx.input.isKeyPressed(Input.Keys.D))||(joystick.isRight())) {
                if (!walked) {
                    oldX =x + 1;
                    // System.out.println("D");
                }
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)||joystick.isBag()) {
            joystick.setUse(false);
            bag_opened = !bag_opened;
        }

        if (map.isGround(oldX, oldY)) {

            x = oldX;
            y = oldY;

        } else {

            oldX = x;
            oldY = y;

        }

        if (wX != x * 16 * Configuration.getScale()) {
            if (wX > x * 16 * Configuration.getScale()) {
                wX = (int) (wX - Configuration.getScale());
                flip = true;
            }
            if (wX < x * 16 * Configuration.getScale()) {
                wX = (int) (wX + Configuration.getScale());
                flip = false;
            }

        }

        if (wY != y * 16 * Configuration.getScale()) {
            if (wY > y * 16 * Configuration.getScale()) {
                wY = (int) (wY - Configuration.getScale());
            }
            if (wY < y * 16 * Configuration.getScale()) {
                wY = (int) (wY + Configuration.getScale());
            }
        }

        if ((wX != x * 16 * Configuration.getScale()) || (wY != y * 16 * Configuration.getScale())) {

            walked = true;
            walked_anim = true;
        } else {

            walked = false;
            walked_anim = (Gdx.input.isKeyPressed(Input.Keys.W)) || (Gdx.input.isKeyPressed(Input.Keys.S)) || (Gdx.input.isKeyPressed(Input.Keys.A)) || (Gdx.input.isKeyPressed(Input.Keys.D)) || (joystick.isJoystick() && !joystick.isUse());
        }
        if ((hp <= 0)) {
            walked_anim = false;
        }


    }

    static public void tick(float second) {
        timer += Gdx.graphics.getDeltaTime();
        if (timer >= second) {
            if (stage > 4) {
                stage = 0;
            }
            stage++;
            timer = 0;
        }
    }

    static public void teleport(int tx, int ty) {
        x = tx;
        y = ty;
        oldX = x;
        oldY = y;
        wX = (int) (x * 16 * Configuration.getScale());
        wY = (int) (y * 16 * Configuration.getScale());
    }


    static void bag_use(Joystick joystick) {
        sx = (int) ((Gdx.graphics.getWidth() - 16 * 7 * Configuration.getScale()) / 2);
        sy = (int) ((Gdx.graphics.getHeight() - 16 * 5 * Configuration.getScale()) / 2);
        slots[0] = false;
        slots[1] = false;
        slots[2] = false;
        if (Gdx.input.justTouched()) {
            getYP = Gdx.graphics.getHeight() - Gdx.input.getY();
            if ((Gdx.input.getX() - sx > 6 * 16 * Configuration.getScale()) && (Gdx.input.getX() - sx < 7 * 16 * Configuration.getScale())) {
                if ((getYP - sy > 2 * 16 * Configuration.getScale()) && (getYP - sy < 3 * 16 * Configuration.getScale())) {
                    joystick.setUse(false);
                    slots[0] = true;
                }
                if ((getYP - sy > 1 * 16 * Configuration.getScale()) && (getYP - sy < 2 * 16 * Configuration.getScale())) {
                    joystick.setUse(false);
                    slots[1] = true;
                }
                if ((getYP - sy > 0 * 16 * Configuration.getScale()) && (getYP - sy < 1 * 16 * Configuration.getScale())) {
                    joystick.setUse(false);
                    slots[2] = true;
                }
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)||slots[0]){
            if((hp<4)&&(potion>0)) {
                potion--;
                hp++;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)||slots[1]){
            if((!isSheld)&&(sheld>0)) {
                sheld--;
                isSheld = true;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)||slots[2]){
            if((map.isDark)&&(torch>0)) {
                torch--;
                map.setDark(false);
            }
        }
    }

    static public Map getMap() {
        return map;
    }

    //public float getDamgeScr() { return damgeScr; }

    static public void setDamgeScr(float damgeScr, int id) {
        Player.damgeScr = damgeScr;
        wasted_id = id;
    }

    static public void setMap(Map map) {
        Player.map = map;
    }

    static public int getX() {
        return x;
    }

    static public void setX(int x) {
        Player.x = x;
    }

    static public int getY() {
        return y;
    }

    static public void wCordNormalize() {
        oldX = x;
        oldY = y;
        wX = (int) (x * 16 * Configuration.getScale());
        wY = (int) (y * 16 * Configuration.getScale());

    }

    static public void setY(int y) {
        Player.y = y;
    }

    static public int get_wX() {
        return wX;
    }

    static public int get_wY() {
        return wY;
    }

    static public void setHp(int hp) {
        Player.hp = hp;
    }

    static public int getHp() {
        return hp;
    }

    static public int getPotion() {
        return potion;
    }

    static public void setPotion(int potion) {
        Player.potion = potion;
    }

    static public int getSheld() {
        return sheld;
    }

    static public void setSheld(int sheld) {
        Player.sheld = sheld;
    }

    static public int getTorch() {
        return torch;
    }

    static public void setTorch(int torch) {
        Player.torch = torch;
    }


    static public boolean isSheld() {
        return isSheld;
    }

    static public void setIsSheld(boolean sheld) {
        isSheld = sheld;
    }

}
