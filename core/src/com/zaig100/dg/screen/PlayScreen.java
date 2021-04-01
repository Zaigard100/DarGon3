package com.zaig100.dg.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zaig100.dg.Main;
import com.zaig100.dg.objects.Crossbow;
import com.zaig100.dg.objects.Flamefrower;
import com.zaig100.dg.objects.FlimsyTile;
import com.zaig100.dg.objects.HideTrap;
import com.zaig100.dg.objects.Item;
import com.zaig100.dg.objects.Map;
import com.zaig100.dg.objects.Player;
import com.zaig100.dg.objects.Stair;
import com.zaig100.dg.objects.Teleport;
import com.zaig100.dg.utils.Font;
import com.zaig100.dg.utils.Joystick;
import com.zaig100.dg.utils.LevelReader;
import com.zaig100.dg.utils.Save;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class PlayScreen implements Screen {

    FrameBuffer fbo,fbo2;
    SpriteBatch batch;
    OrthographicCamera cam;
    Main m;
    Sprite frame;
    static LevelReader lR;
    static Font font;
    static Random random;
    static BitmapFont f1,f2,f3,f4;
    static int height;
    static int width;

    static int scrW,scrH;
    String path,line;

    static Map map;
    Player player;
    static Stair stair;
    int hp;

    int menu = 0;

    static Teleport[] tp;
    HideTrap[] hideTrap;
    static Flamefrower[] flame;
    Crossbow[] crossbow;
    Item[] item;
    FlimsyTile[] flimsyTiles;
    
    static Joystick joystick;

    Save save;
    boolean start = true;

    boolean is_pause = false,debag = false;
    float exit_timer = 0,sensor_timer =0f;

    boolean fir1 = true;
    int i;

    Viewport viewport;

    boolean isPack;
    String packname = "";
    String derectory = "";
    
    public PlayScreen(Main m,String path,Player p,boolean isPack){
        this.m = m;
        this.path=path;
        this.player = p;
        this.isPack= isPack;
    }

    public PlayScreen(Main m, String path, Player p, boolean isPack, String packname, String derectory){
        this.m = m;
        this.path=path;
        this.player = p;
        this.isPack= isPack;
        this.packname = packname;
        this.derectory = derectory;
    }

    @Override
    public void show() {
        random = new Random();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        fbo = new FrameBuffer(Pixmap.Format.RGB888,width,height,false);
        fbo2 = new FrameBuffer(Pixmap.Format.RGB888,width,height,false);
        cam = new OrthographicCamera(width,height);
        viewport = new ScreenViewport(cam);
        font = new Font();

        
        joystick = new Joystick(Main.getConfiguration());

        debag = Main.getConfiguration().isDebug();

        f1 = font.gFont(10*Main.getConfiguration().getScale(),"fonts/GFont.ttf");
        f2 = font.gFont(6*Main.getConfiguration().getScale(),"fonts/GFont.ttf");
        f4 = font.gFont(3*Main.getConfiguration().getScale(),"fonts/GFont.ttf");
        f3 = new BitmapFont();
        f3.setColor(Color.WHITE);
        if (isPack) {
            try {
                lR = new LevelReader(derectory + path, isPack);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                lR = new LevelReader(path, isPack);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        map = new Map(lR.getWidht(),lR.getHeight(),lR.getMap(),true);
        player.setX(lR.getSpawn()[0]);
        player.setY(lR.getSpawn()[1]);
        player.wCordNormalize();
        player.setMap(map);
        stair = new Stair(lR.getWin()[0],lR.getWin()[1],lR.isSpawnFlipX(),lR.getNextpath(),lR.isEnd(),player);
        if(lR.getTeleportCount()>0){

            tp = new Teleport[lR.getTeleportCount()];

            for(i =0;i<lR.getTeleportCount();i++){
                tp[i] = new Teleport(lR.getTeleport()[0][i],lR.getTeleport()[1][i],lR.getTeleport()[2][i],lR.getTeleport()[3][i],player);
            }

        }
        if(lR.getHideTrapCount()>0){

            hideTrap = new HideTrap[lR.getHideTrapCount()];

            for(i =0;i<lR.getHideTrapCount()-1;i++){
                hideTrap[i] = new HideTrap(lR.getHideTraps()[0][i],lR.getHideTraps()[1][i],player);
            }

        }
        if(lR.getFlamethrowerCount()>0){

            flame = new Flamefrower[lR.getFlamethrowerCount()];

            for(i =0;i<lR.getFlamethrowerCount();i++){
                flame[i] = new Flamefrower(lR.getFlamethrowfers()[0][i],lR.getFlamethrowfers()[1][i],lR.getFlamethrowfers()[2][i],lR.getFlamethrowfers()[3][i],lR.getFlamethrowfers()[4][i],player);
            }

        }
        if(lR.getCrossbowCount()>0){

            crossbow = new Crossbow[lR.getCrossbowCount()];

            for(i =0;i<lR.getCrossbowCount();i++){
                crossbow[i] = new Crossbow(lR.getCrossbow()[0][i],lR.getCrossbow()[1][i],lR.getCrossbow()[2][i],lR.getCrossbow()[3][i],lR.getCrossbow()[4][i],player);
            }

        }

        if (lR.getItemsCount() > 0) {
            item = new Item[lR.getItemsCount()];
            for (i = 0; i < lR.getItemsCount(); i++) {
                item[i] = new Item(lR.getItems()[0][i], lR.getItems()[1][i], lR.getItems()[2][i], player);
            }
        }

        if (lR.getFlimstTileCount() > 0) {
            flimsyTiles = new FlimsyTile[lR.getFlimstTileCount()];
            for (i = 0; i < lR.getFlimstTileCount(); i++) {
                flimsyTiles[i] = new FlimsyTile(lR.getFlimsyTile()[0][i], lR.getFlimsyTile()[1][i], player);
            }
        }
        //Main.getRes().sprL();

        cam.position.set(new Vector3(width / 2, height / 2, 0));

        try {
            if (isPack) {
                save = new Save(m, packname, path);
            } else {
                save = new Save(m, "", path);
            }
            if (lR.isSave()) {
                save.setHp(player.getHp());
                save.setPath(path);
                save.setPotion(player.getPotion());
                save.setSheld(player.getSheld());
                save.setTorch(player.getTorch());
                save.save(save.getConf());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void render(float delta) {



        if(!is_pause) player.frame(joystick);
        if(!joystick.isJoystick()&&Gdx.input.isTouched()){
            sensor_timer += delta;
            if(sensor_timer>1f){
                is_pause = !is_pause;
                sensor_timer=0;
            }
        }else{
            sensor_timer = 0;
        }
        fbo.begin();
        batch.setProjectionMatrix(cam.combined);
            batch.begin();

                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                map.render(batch, Main.getRes(),player.get_wX(),player.get_wY(),player,Main.getConfiguration());
                stair.render(batch, Main.getRes(),Main.getConfiguration());
                if(lR.getTeleportCount()>0) {
                    for (i = 0; i < lR.getTeleportCount(); i++) {
                        if(!is_pause) tp[i].frame(joystick);
                        tp[i].render(batch, Main.getRes(),Main.getConfiguration());
                    }
                }
                if(lR.getHideTrapCount()>0) {
                    for (i = 0; i < lR.getHideTrapCount()-1; i++) {
                        if(!is_pause) hideTrap[i].frame(Main.getRes());
                    }
                }
        player.render(batch, Main.getRes());
        if (lR.getFlamethrowerCount() > 0) {
            for (i = 0; i < lR.getFlamethrowerCount(); i++) {
                if (!is_pause) flame[i].frame();
                flame[i].render(batch, Main.getRes(), Main.getConfiguration());
                if (!is_pause) flame[i].tick(1.5f);
                if (!is_pause) flame[i].tick1(0.1f);
            }
        }
        if (lR.getCrossbowCount() > 0) {
            for (i = 0; i < lR.getCrossbowCount(); i++) {
                if (!is_pause) crossbow[i].frame();
                crossbow[i].render(batch, Main.getRes(), Main.getConfiguration());
                if (!is_pause) crossbow[i].tick(1f);
            }
        }

        if (lR.getItemsCount() > 0) {
            for (i = 0; i < lR.getItemsCount(); i++) {
                if (!is_pause) item[i].frame();
                item[i].render(batch, Main.getRes(), Main.getConfiguration());
            }
        }

        if (lR.getFlimstTileCount() > 0) {
            for (i = 0; i < lR.getFlimstTileCount(); i++) {
                if (!is_pause) flimsyTiles[i].frame();
                if (!is_pause) flimsyTiles[i].tick(1.5f);

                flimsyTiles[i].render(batch, Main.getRes(), Main.getConfiguration());
            }
        }

        map.dark_render(batch, Main.getRes(), Main.getConfiguration());
        if (!is_pause) {
            player.render_bag(batch, Main.getRes(), f4, joystick);
            if (start)
                f2.draw(batch, lR.getLevelName(), 8 * Main.getConfiguration().getScale(), 4.5f * 16 * Main.getConfiguration().getScale());
        }
        if (player.getHp() <= 0 && !is_pause) {
            if (fir1) Main.getRes().wasted.play(2.0f);
            fir1 = false;
            f1.draw(batch, "Wasted", 2 * 16 * Main.getConfiguration().getScale(), 4.5f * 16 * Main.getConfiguration().getScale());
            f2.draw(batch, "Press Space", 2 * 16 * Main.getConfiguration().getScale(), 0.5f * 16 * Main.getConfiguration().getScale());
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || joystick.isUse()) {
                m.setScreen(new GameScreen(m));

            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            is_pause = !is_pause;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            debag = !debag;
        }
        if (is_pause) {
            batch.draw(Main.getRes().pause_dark, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            f1.draw(batch, "Pause", 2.2f * 16 * Main.getConfiguration().getScale(), 4.5f * 16 * Main.getConfiguration().getScale());
            pauseMenu();

        }
        joystick.render(batch, Main.getRes());
        if (debag) debugShow();
            batch.end();
        fbo.end();
        if(player.getX()!=lR.getSpawn()[0]||player.getY()!=lR.getSpawn()[1]) start = false;

        if(!is_pause) player.tick(0.1f);
        if (isPack) {
            stair.frame_isPack(m, Main.getRes(), joystick, isPack,packname,derectory);
        }else {
            stair.frame(m, Main.getRes(), joystick, isPack);
        }
        frame = new Sprite(fbo.getColorBufferTexture());


        fbo2.begin();
        batch.begin();

        frame.draw(batch);
        //frame.setPosition(space/2,0);

        batch.end();
        fbo2.end();

        frame = new Sprite(fbo2.getColorBufferTexture());
        frame.setPosition((scrW-16*7*Main.getConfiguration().getScale())/2,(scrH-16*5*Main.getConfiguration().getScale())/2);

        cam.update();

        batch.begin();

            batch.draw(Main.getRes().boards,0,0,16*7*Main.getConfiguration().getScale()*10,16*5*Main.getConfiguration().getScale()*10);
            if(is_pause) {
                batch.draw(Main.getRes().pause_dark, 0, 0, 16 * 7 * Main.getConfiguration().getScale() * 10, 16 * 5 * Main.getConfiguration().getScale() * 10);
            }
            frame.draw(batch);

        batch.end();
    }

    void debugShow(){
        line = "Debag:";
        f3.draw(batch,line,10,5f * 16 * Main.getConfiguration().getScale()-10);
        line = "FPS:"+Gdx.graphics.getFramesPerSecond();
        f3.draw(batch,line,10,5f * 16 * Main.getConfiguration().getScale()-25);
        line = "X:"+player.getX() + "  Y:"+player.getY();
        f3.draw(batch,line,10,5f * 16 * Main.getConfiguration().getScale()-40);
        line = "WX:"+player.get_wX() + "  WY:"+player.get_wY();
        f3.draw(batch,line,10,5f * 16 * Main.getConfiguration().getScale()-55);
    }



    void pauseMenu(){
        joystick.frame((int)(scrW-16*7*Main.getConfiguration().getScale())/2,(int)(scrH-16*5*Main.getConfiguration().getScale())/2);
        if(menu==0) {
            f2.draw(batch, ">Resume<", 2.2f * 16 * Main.getConfiguration().getScale(), 3.5f * 16 * Main.getConfiguration().getScale());
            f2.draw(batch, " Main menu", 2.2f * 16 * Main.getConfiguration().getScale(), 2.5f * 16 * Main.getConfiguration().getScale());
            f2.draw(batch, " Load save", 2.2f * 16 * Main.getConfiguration().getScale(), 1.5f * 16 * Main.getConfiguration().getScale());
            f2.draw(batch, " Exit(Hold)", 2.2f * 16 * Main.getConfiguration().getScale(), 0.5f * 16 * Main.getConfiguration().getScale());
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)||joystick.isUse()) {
                is_pause = false;
            }
        }
        if(menu==1) {
            f2.draw(batch, " Resume", 2.2f * 16 * Main.getConfiguration().getScale(), 3.5f * 16 * Main.getConfiguration().getScale());
            f2.draw(batch, ">Main menu<", 2.2f * 16 * Main.getConfiguration().getScale(), 2.5f * 16 * Main.getConfiguration().getScale());
            f2.draw(batch, " Load save", 2.2f * 16 * Main.getConfiguration().getScale(), 1.5f * 16 * Main.getConfiguration().getScale());
            f2.draw(batch, " Exit(Hold)", 2.2f * 16 * Main.getConfiguration().getScale(), 0.5f * 16 * Main.getConfiguration().getScale());
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)||joystick.isUse()) {
                m.setScreen(new GameScreen(m));
            }
        }
        if(menu==2) {
            f2.draw(batch, " Resume", 2.2f * 16 * Main.getConfiguration().getScale(), 3.5f * 16 * Main.getConfiguration().getScale());
            f2.draw(batch, " Main menu", 2.2f * 16 * Main.getConfiguration().getScale(), 2.5f * 16 * Main.getConfiguration().getScale());
            f2.draw(batch, ">Load save<", 2.2f * 16 * Main.getConfiguration().getScale(), 1.5f * 16 * Main.getConfiguration().getScale());
            f2.draw(batch, " Exit(Hold)", 2.2f * 16 * Main.getConfiguration().getScale(), 0.5f * 16 * Main.getConfiguration().getScale());
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || joystick.isUse()) {
                if (isPack) {
                    m.setScreen(new PlayScreen(m, save.getsPath(), new Player(0, 0, null, save.getHp(), save.getPotion(), save.getSheld(), save.getTorch(), Main.getConfiguration()), isPack, packname, derectory));
                } else
                    m.setScreen(new PlayScreen(m, save.getsPath(), new Player(0, 0, null, save.getHp(), save.getPotion(), save.getSheld(), save.getTorch(), Main.getConfiguration()), isPack));
            }

        }

        if(menu==3) {
            f2.draw(batch, " Resume", 2.2f * 16 * Main.getConfiguration().getScale(), 3.5f * 16 * Main.getConfiguration().getScale());
            f2.draw(batch, " Main menu", 2.2f * 16 * Main.getConfiguration().getScale(), 2.5f * 16 * Main.getConfiguration().getScale());
            f2.draw(batch, " Load save", 2.2f * 16 * Main.getConfiguration().getScale(), 1.5f * 16 * Main.getConfiguration().getScale());
            f2.draw(batch, ">Exit(Hold)<", 2.2f * 16 * Main.getConfiguration().getScale(), 0.5f * 16 * Main.getConfiguration().getScale());
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)||joystick.isUse()) {
                exit_timer+= Gdx.graphics.getDeltaTime();
                if(exit_timer > 1.0f){
                    System.exit(0);
                }
            }else{
                exit_timer = 0;
            }
        }else{
            exit_timer = 0;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            menu--;
            Main.getRes().click[random.nextInt(2)].play(Main.getConfiguration().getSound());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)||(!joystick.isUse()&&Gdx.input.justTouched())) {
            menu++;
            Main.getRes().click[random.nextInt(2)].play(Main.getConfiguration().getSound());
        }

        if(menu>=4){
            menu = 0;
        }

        if(menu<0){
            menu = 3;
        }

    }


    @Override
    public void resize(int width, int height) {
        int ry = height - PlayScreen.height;
        int rx = width - PlayScreen.width;
        scrW = width;
        scrH = height;
        viewport.update(width, height);
        cam.translate(rx/2, ry/2, cam.position.z);
        cam.update();
        fbo = new FrameBuffer(Pixmap.Format.RGB888,width,height,false);
        fbo2 = new FrameBuffer(Pixmap.Format.RGB888,width,height,false);
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
        batch.dispose();
        fbo.dispose();
        fbo2.dispose();
        f1.dispose();
        f2.dispose();
        f3.dispose();
        f4.dispose();
        font.dispose();
    }
}
