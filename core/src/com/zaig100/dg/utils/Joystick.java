package com.zaig100.dg.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class Joystick {

    int getY;
    boolean up,right,down,left,use,Sensor,bag;
    int pointX,pointY,deltaX,deltaY;

    Configuration conf;

    public Joystick(Configuration conf) {

        this.conf=conf;
        this.Sensor = Sensor;

    }

    public void render(SpriteBatch batch,Res res){
        if(conf.isSensor()) {
            batch.draw(res.use, pointX-conf.getScale()-(int)((Gdx.graphics.getWidth() -16*7*conf.getScale())/2), pointY-conf.getScale()-(int)((Gdx.graphics.getHeight() -16*5*conf.getScale())/2), 2f * conf.getScale(), 2 * conf.getScale());
            //batch.draw(res.use, 6 * 16 * conf.getScale() - 0.8f * 16 * conf.getScale() / 2, 1.25f * 16 * conf.getScale() - 0.8f * 16 * conf.getScale() / 2, 0.8f * 16 * conf.getScale(), 0.8f * 16 * conf.getScale());
        }
    }

    public void frame(int sx,int sy) {
        if (conf.isSensor()) {
            left = false;
            right = false;
            down = false;
            up = false;
            use = false;
            bag = false;
            if (Gdx.input.justTouched()) {
                pointX = Gdx.input.getX();
                pointY = Gdx.graphics.getHeight() - Gdx.input.getY();
            }
            if (Gdx.input.isTouched()) {
                getY = Gdx.graphics.getHeight() - Gdx.input.getY();

                deltaX = Gdx.input.getX()-pointX;
                deltaY = getY-pointY;
                if(pointX<Gdx.graphics.getWidth()/2) {
                    if (deltaX > 8 * conf.getScale()) {
                        right = true;
                    }
                    if (deltaX < -8*conf.getScale()) {
                        left = true;
                    }
                    if (deltaY > 8 * conf.getScale()) {
                        up = true;
                    }
                    if (deltaY < -8 * conf.getScale()) {
                        down = true;
                    }
                }
                if(Gdx.input.getX()>Gdx.graphics.getWidth()/2){
                    use = true;
                }
                if (Gdx.input.justTouched()) {
                    if ((Gdx.input.getX()-sx > 6 * 16 * conf.getScale()) && (Gdx.input.getX()-sx < 7 * 16 * conf.getScale())) {
                        if ((getY-sy > 4 * 16 * conf.getScale()) && (getY-sy < 5 * 16 * conf.getScale())) {
                            bag = true;
                        }
                    }
                }
            }
        }
    }

    public boolean isJoystick() {
        return up||down||left||right||use;
    }

    public boolean isBag(){ return bag;}

    public boolean isUp() {
        return up;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isUse() {
        return use;
    }

    public void setUse(boolean is) {
        use = is;
    }
}
