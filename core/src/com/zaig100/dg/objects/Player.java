package com.zaig100.dg.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Joystick;
import com.zaig100.dg.utils.Res;

import java.io.FileNotFoundException;
import java.util.Random;

public class Player {

    int x;
    int y;
    int oldX;
    int oldY;
    int wX;
    int wY;
    int hp;
    int potion;
    int sheld;
    int torch;
    int stage=0;
    float timer = 0,timer1 = 0;
    boolean walked = false,walked_anim = false,flip = false,bag_opened=false;
    int sx,sy;
    int wasted_id = 0;

    boolean[] slots = new boolean[3];
    boolean isSheld= false;

    Configuration config;
    Random random = new Random();

    Map map;
    float damgeScr=100;
    private int getYP;


    public Player(int x, int y, Map map, Configuration config){
        this.x = x;
        this.y = y;
        this.oldX = x;
        this.oldY = y;
        this.wX = (int) (x * 16 * config.getScale());
        this.wY = (int) (y * 16 * config.getScale());
        this.hp = 4;
        this.potion = 3;
        this.sheld = 2;
        this.torch = 1;
        this.map =map;
        this.config = config;
    }
    public Player(int x,int y,Map map,int hp,int potion,int sheld,int torch, Configuration config){
        this.x = x;
        this.y = y;
        this.oldX = x;
        this.oldY = y;
        this.wX = x * 16 * (int)config.getScale();
        this.wY = y * 16 * (int)config.getScale();
        this.map =map;
        this.potion =potion;
        this.sheld = sheld;
        this.torch = torch;
        this.hp = hp;
        this.config = config;

    }

    public void render(SpriteBatch batch, Res res){
        if(getHp()> 0) {
            batch.draw(res.hero(flip, walked_anim, stage), 16 * config.getScale() * 3, 16 * config.getScale() * 2, 16 * config.getScale(), 16 * config.getScale());
            if(isSheld) {
                if (flip) {
                    batch.draw(res.sheld, 16 * config.getScale() * 3.25f, 16 * config.getScale() * 2, 16 * config.getScale() * 0.75f, 16 * config.getScale() * 0.75f);
                } else {
                    batch.draw(res.sheld, 16 * config.getScale() * 3, 16 * config.getScale() * 2, 16 * config.getScale() * 0.75f, 16 * config.getScale() * 0.75f);
                }
            }
            }else{
            if(wasted_id==0){
                batch.draw(res.hero(flip, walked_anim, stage), 16 * config.getScale() * 3, 16 * config.getScale() * 2, 16 * config.getScale(), 16 * config.getScale());
            }
            if(wasted_id==1){
                batch.draw(res.amonghero, 16 * config.getScale() * 3, 16 * config.getScale() * 2, 16 * config.getScale(), 16 * config.getScale());
            }
            if(wasted_id==2){
                batch.draw(res.firedhero, 16 * config.getScale() * 3, 16 * config.getScale() * 2, 16 * config.getScale(), 16 * config.getScale());
            }
            if(wasted_id==3){
                batch.draw(res.shotedhero, 16 * config.getScale() * 3, 16 * config.getScale() * 2, 16 * config.getScale(), 16 * config.getScale());
            }
        }
        if(damgeScr<0.5f){
            batch.draw(res.damage,0,0,112*config.getScale(),80*config.getScale());
            damgeScr = damgeScr + Gdx.graphics.getDeltaTime();
        }
    }

    public void render_bag (SpriteBatch batch, Res res, BitmapFont font,Joystick joystick){
        batch.draw(res.bag, 6 * 16 * config.getScale(), 4 * 16 * config.getScale(), 16 * config.getScale(), 16 * config.getScale());
        if(bag_opened) {
            bag_use(joystick);
            if (hp > 0) {
                batch.draw(res.HP(hp), 6 * 16 * config.getScale(), 3 * 16 * config.getScale(), 16 * config.getScale(), 16 * config.getScale());
            }
            batch.draw(res.hp_potion, 6 * 16 * config.getScale(), 2 * 16 * config.getScale(), 16 * config.getScale(), 16 * config.getScale());
                font.draw(batch,String.valueOf(potion),6 * 16 * config.getScale(), 2*16 * config.getScale()+4*config.getScale());
            batch.draw(res.sheld, 6 * 16 * config.getScale(), 1 * 16 * config.getScale(), 16 * config.getScale(), 16 * config.getScale());
                font.draw(batch,String.valueOf(sheld),6 * 16 * config.getScale(), 1*16 * config.getScale()+4*config.getScale());
            batch.draw(res.torch, 6 * 16 * config.getScale(), 0 * 16 * config.getScale(), 16 * config.getScale(), 16 * config.getScale());
                font.draw(batch,String.valueOf(torch),6 * 16 * config.getScale(), 0*16 * config.getScale()+4*config.getScale());
        }
    }


    public void frame(Joystick joystick){
        joystick.frame((int)((Gdx.graphics.getWidth()-16*7*config.getScale())/2),(int)((Gdx.graphics.getHeight()-16*5*config.getScale())/2));
        if(hp>0) {
            if ((Gdx.input.isKeyPressed(Input.Keys.W))||(joystick.isUp())) {
                if (!walked) {
                    oldY = y + 1;
                    // System.out.println("W");
                }
            }
            if ((Gdx.input.isKeyPressed(Input.Keys.S))||(joystick.isDown())) {
                if (!walked) {
                    oldY = y - 1;
                    //System.out.println("S");
                }
            }
            if ((Gdx.input.isKeyPressed(Input.Keys.A))||(joystick.isLeft())) {
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

        if(map.isGround(oldX,oldY)) {

                x = oldX;
                y = oldY;

        }else {

                oldX = x;
                oldY = y;

        }

            if(wX != x * 16 * config.getScale()) {
                if(wX > x * 16 * config.getScale()) {
                    wX = (int) (wX - config.getScale());
                    flip = true;
                }
                if(wX < x * 16 * config.getScale()) {
                    wX = (int) (wX + config.getScale());
                    flip = false;
                }

            }

            if(wY != y * 16 * config.getScale()) {
                if (wY > y * 16 * config.getScale()) {
                    wY = (int) (wY - config.getScale() );
                }
                if (wY < y * 16 * config.getScale()) {
                    wY = (int) (wY + config.getScale() );
                }
            }

            if((wX != x * 16 * config.getScale())||(wY != y * 16 * config.getScale())) {

                walked = true;
                walked_anim = true;
            }else {

                walked = false;
                walked_anim = (Gdx.input.isKeyPressed(Input.Keys.W)) || (Gdx.input.isKeyPressed(Input.Keys.S)) || (Gdx.input.isKeyPressed(Input.Keys.A)) || (Gdx.input.isKeyPressed(Input.Keys.D)) || (joystick.isJoystick() && !joystick.isUse());
            }
            if((hp<=0)){
                walked_anim = false;
            }



    }

    public void tick(float second){
        timer+=Gdx.graphics.getDeltaTime();
        if(timer>=second) {
            if (stage > 4) {
                stage = 0;
            }
            stage++;
            timer =0;
        }
    }

    public void teleport(int tx,int ty){
        x = tx;
        y = ty;
        oldX = x;
        oldY = y;
        wX = (int) (x * 16 * config.getScale());
        wY = (int) (y * 16 * config.getScale());
    }


    void bag_use(Joystick joystick){
        sx = (int)((Gdx.graphics.getWidth() -16*7*config.getScale())/2);
        sy = (int)((Gdx.graphics.getHeight()-16*5*config.getScale())/2);
        slots[0]=false;
        slots[1]=false;
        slots[2]=false;
        if(Gdx.input.justTouched()){
            getYP = Gdx.graphics.getHeight()-Gdx.input.getY();
            if ((Gdx.input.getX()-sx > 6*16 * config.getScale()) && (Gdx.input.getX()-sx <7*16  * config.getScale())) {
                if ((getYP-sy > 2*16 * config.getScale()) && (getYP-sy < 3*16 * config.getScale())) {
                    joystick.setUse(false);
                    slots[0] = true;
                }
                if ((getYP-sy > 1*16 * config.getScale()) && (getYP-sy < 2*16 * config.getScale())) {
                    joystick.setUse(false);
                    slots[1] = true;
                }
                if ((getYP-sy > 0*16 * config.getScale()) && (getYP-sy < 1*16 * config.getScale())) {
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

    public Map getMap() {
        return map;
    }

    public float getDamgeScr() {
        return damgeScr;
    }

    public void setDamgeScr(float damgeScr, int id) {
        this.damgeScr = damgeScr;
        this.wasted_id = id;
    }

    public void setMap(Map map){
        this.map = map;
    }

    public int getX() {
        return x;
    }

    public void setX(int x){this.x= x;}

    public int getY() {
        return y;
    }

    public void wCordNormalize() {
        oldX = x;
        oldY = y;
        wX = (int) (x * 16 * config.getScale());
        wY = (int) (y * 16 * config.getScale());

    }

    public void setY(int y){this.y= y;}

    public int get_wX() {
        return wX;
    }

    public int get_wY() {
        return wY;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public  int getPotion(){
        return  potion;
    }
    public  void setPotion(int potion){
        this.potion=potion;
    }

    public int getSheld() {
        return sheld;
    }
    public  void setSheld(int sheld){
        this.sheld=sheld;
    }

    public int getTorch() {
        return torch;
    }
    public void setTorch(int torch) {
        this.torch = torch;
    }



    public boolean isSheld() {
        return isSheld;
    }

    public Player setIsSheld(boolean sheld) {
        isSheld = sheld;
        return this;
    }

}
