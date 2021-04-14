package com.zaig100.dg.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zaig100.dg.Main;
import com.zaig100.dg.utils.Font;


public class Setting implements Screen {

    Main m;
    
    double Scale;
    long Music;
    long Sound;
    boolean Sensor;
    boolean Debug;

    SpriteBatch batch;

    String[] line = new String[5];
    Font font;
    BitmapFont f1;

    int num = 0;

    int getY;
    private int scrH;
    private int scrW;
    private Viewport viewport;
    private OrthographicCamera cam;

    public Setting(Main m){
        this.m = m;
        batch = new SpriteBatch();
    }

    @Override
    public void show() {


        scrH = Gdx.graphics.getHeight();
        scrW = Gdx.graphics.getWidth();

        cam = new OrthographicCamera();
        cam.translate(scrW / 2, scrW / 2, 0);
        viewport = new ScreenViewport(cam);


        Scale = Main.getConfiguration().getScale();
        Music = Main.getConfiguration().getMusic();
        Sound = Main.getConfiguration().getSound();
        Sensor = Main.getConfiguration().isSensor();
        Debug = Main.getConfiguration().isDebug();

        font = new Font();
        f1 = font.gFont(8 * Main.getConfiguration().getScale(), "fonts/GFont.ttf");

    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.F11)) {
            if(!Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }else{
                Gdx.graphics.setWindowedMode((int)( 16*7*Main.getConfiguration().getScale()), (int)( 16*5*Main.getConfiguration().getScale()));
            }
        }
        
        Gdx.graphics.getGL20().glClearColor(0, 0, 0, 1f);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Sensor) sensor();


        line[0] = "  Scale: x" + Scale +" (" + Main.getConfiguration().getScale() + ")";
        line[1] = "  Music:" + Music +" (" + Main.getConfiguration().getMusic() + ")";
        line[2] = "  Sound:" + Sound +" (" + Main.getConfiguration().getSound() + ")";
        line[3] = "  Sensor:" + boolStr(Sensor) +" (" + boolStr(Main.getConfiguration().isSensor()) + ")";
        line[4] = "  Debug:" + boolStr(Debug) +" (" + boolStr(Main.getConfiguration().isDebug()) + ")";

        if(Gdx.input.isKeyJustPressed(Input.Keys.W)) num--;
        if(Gdx.input.isKeyJustPressed(Input.Keys.S)) num++;
        if(Gdx.input.justTouched()) {
            if (getY > 145 / 8 * Main.getConfiguration().getScale() && getY < 256 / 8 * Main.getConfiguration().getScale()) {
                Sensor = !Sensor;
                num = 3;
            }
        }
        if(num>4) num = 0;
        if(num<0) num = 4;

        if(num == 0){
            line[0] = ">Scale: x" + Scale +" (" + Main.getConfiguration().getScale() + ")";

            if(Gdx.input.isKeyJustPressed(Input.Keys.A)) Scale -= 1f;
            if(Gdx.input.isKeyJustPressed(Input.Keys.D)) Scale += 1f;

            if(Scale<0) Scale = 0;

        }
        if(num == 1){
            line[1] = ">Music:" + Music +" (" + Main.getConfiguration().getMusic() + ")";

            if(Gdx.input.isKeyJustPressed(Input.Keys.A)) Music -= 10;
            if(Gdx.input.isKeyJustPressed(Input.Keys.D)) Music += 10;
            if(Music>100) Music = 100;
            if(Music<0) Music = 0;

        }
        if(num == 2){
            line[2] = ">Sound:" + Sound +" (" + Main.getConfiguration().getSound() + ")";

            if(Gdx.input.isKeyJustPressed(Input.Keys.A)) Sound -= 10;
            if(Gdx.input.isKeyJustPressed(Input.Keys.D)) Sound += 10;
            if(Sound>100) Sound = 100;
            if(Sound<0) Sound = 0;

        }

        if(num == 3){
            line[3] = ">Sensor:" + boolStr(Sensor) +" (" + boolStr(Main.getConfiguration().isSensor()) + ")";

            if(Gdx.input.isKeyJustPressed(Input.Keys.A)) Sensor =!Sensor;
            if(Gdx.input.isKeyJustPressed(Input.Keys.D)) Sensor =!Sensor;

        }
        if(num == 4){
            line[4] = ">Debug:" + boolStr(Debug) +" (" + boolStr(Main.getConfiguration().isDebug()) + ")";

            if(Gdx.input.isKeyJustPressed(Input.Keys.A)) Debug =!Debug;
            if(Gdx.input.isKeyJustPressed(Input.Keys.D)) Debug =!Debug;

        }

        batch.begin();
            f1.draw(batch, "Sentings :",10, 5 * 16 * Main.getConfiguration().getScale() -10);
            f1.draw(batch, line[0], 10, 5 * 16 * Main.getConfiguration().getScale()- (12 * Main.getConfiguration().getScale()) -10);
            f1.draw(batch, line[1], 10, 5 * 16 * Main.getConfiguration().getScale()- (25 * Main.getConfiguration().getScale()) -10);
            f1.draw(batch, line[2], 10, 5 * 16 * Main.getConfiguration().getScale()- (37 * Main.getConfiguration().getScale()) -10);
            f1.draw(batch, line[3], 10, 5 * 16 * Main.getConfiguration().getScale()- (49 * Main.getConfiguration().getScale()) -10);
            f1.draw(batch, line[4], 10, 5 * 16 * Main.getConfiguration().getScale()- (62 * Main.getConfiguration().getScale()) -10);
        batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) exit();

    }

    void sensor(){
        if(Gdx.input.justTouched()){
            System.out.println(Gdx.graphics.getHeight()-Gdx.input.getY());
            getY = Gdx.graphics.getHeight()-Gdx.input.getY();
            if(getY>549/8*Main.getConfiguration().getScale()){
                exit();
            }
            if(Gdx.input.getX()<596/8*Main.getConfiguration().getScale()){
                if(getY>449/8*Main.getConfiguration().getScale()&&getY<549/8*Main.getConfiguration().getScale()){
                    num = 0;
                    Scale -= 1f;
                }
                if(getY>349/8*Main.getConfiguration().getScale()&& getY<449/8*Main.getConfiguration().getScale()){
                    num = 1;
                    Music -= 10;
                }
                if(getY>249/8*Main.getConfiguration().getScale()&&getY<349/8*Main.getConfiguration().getScale()){
                    num = 2;
                    Sound -= 10;
                }
            }
            if(Gdx.input.getX()>596/8*Main.getConfiguration().getScale()){
                if(getY>449/8*Main.getConfiguration().getScale()&&getY<549/8*Main.getConfiguration().getScale()){
                    num = 0;
                    Scale += 1f;
                }
                if(getY>349/8*Main.getConfiguration().getScale()&&getY<449/8*Main.getConfiguration().getScale()){
                    num = 1;
                    Music += 10;
                }
                if(getY>249/8*Main.getConfiguration().getScale()&&getY<349/8*Main.getConfiguration().getScale()){
                    num = 2;
                    Sound += 10;
                }
            }
            if (getY > 45 / 8 * Main.getConfiguration().getScale() && getY < 146 / 8 * Main.getConfiguration().getScale()) {
                Debug = !Debug;
                num = 4;
            }
        }
    }

    void exit(){

        if(Scale == Main.getConfiguration().getScale()){
            Main.getConfiguration().setMusic((int) Music);
            Main.getConfiguration().setSound((int) Sound);
            Main.getConfiguration().setSensor(Sensor);
            Main.getConfiguration().setDebug(Debug);
            Main.getConfiguration().save(Main.getConfiguration().getConf());
            m.setScreen(new GameScreen(m));
        } else{
            Main.getConfiguration().setMusic((int) Music);
            Main.getConfiguration().setSound((int) Sound);
            Main.getConfiguration().setSensor(Sensor);
            Main.getConfiguration().setDebug(Debug);
            Main.getConfiguration().setScale((float) Scale);
            Main.getConfiguration().save(Main.getConfiguration().getConf());
            f1 = new Font().gFont((float) (8 * Scale),"fonts/GFont.ttf");
                if(!Gdx.graphics.isFullscreen()&&!m.isAndroid()) {
                    Gdx.graphics.setWindowedMode((int)( 16*7*Scale), (int)( 16*5*Scale));
                }
            dispose();
            m.setScreen(new GameScreen(m));
            //Gdx.app.exit();
        }

    }

    String boolStr(boolean bool){
        if(bool){
            return "T";
        }else{
            return "F";
        }
    }

    @Override
    public void resize(int width, int height) {

        int ry = height - this.scrH;
        int rx = width - this.scrW;
        scrW = width;
        scrH = height;
        viewport.update(width, height);
        cam.translate(rx/2, ry/2,0);
        cam.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        f1.dispose();
        font.dispose();
        batch.dispose();
    }
}
