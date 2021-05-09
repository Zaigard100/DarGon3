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
import com.zaig100.dg.objects.Spike;
import com.zaig100.dg.objects.Spinney;
import com.zaig100.dg.objects.Stair;
import com.zaig100.dg.objects.Teleport;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Font;
import com.zaig100.dg.utils.Joystick;
import com.zaig100.dg.utils.LevelRead;
import com.zaig100.dg.utils.Res;
import com.zaig100.dg.utils.Save;
import com.zaig100.dg.utils.contain.CrossbowC;
import com.zaig100.dg.utils.contain.FlamefrowerC;
import com.zaig100.dg.utils.contain.FlimsyTileC;
import com.zaig100.dg.utils.contain.HideTrapC;
import com.zaig100.dg.utils.contain.ItemC;
import com.zaig100.dg.utils.contain.SpikeC;
import com.zaig100.dg.utils.contain.SpinneyC;
import com.zaig100.dg.utils.contain.StairC;
import com.zaig100.dg.utils.contain.TeleportC;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class PlayScreen implements Screen {

    FrameBuffer fbo,fbo2;
    SpriteBatch batch;
    OrthographicCamera cam;
    Main m;
    Sprite frame;
    static LevelRead lR;
    static Font font;
    static Random random;
    static BitmapFont f1,f2,f3,f4;
    static int height;
    static int width;

    static int scrW, scrH;
    String path, line;

    static Map map;
    Player player;
    int hp;

    int menu = 0;

    ArrayList<Stair> stair = new ArrayList<>();
    StairC stairС;
    ArrayList<Teleport> tp = new ArrayList<>();
    TeleportC teleportC;
    ArrayList<HideTrap> hideTrap = new ArrayList<>();
    HideTrapC hideTrapC;
    ArrayList<Flamefrower> flame = new ArrayList<>();
    FlamefrowerC flamefrowerС;
    ArrayList<Crossbow> crossbow = new ArrayList<>();
    CrossbowC crossbowC;
    ArrayList<Item> item = new ArrayList<>();
    ItemC itemC;
    ArrayList<FlimsyTile> flimsyTiles = new ArrayList<>();
    FlimsyTileC flimsyTileC;
    ArrayList<Spinney> spinney = new ArrayList<>();
    SpinneyC spinneyC;
    ArrayList<Spike> spike = new ArrayList<>();
    SpikeC spikeC;

    static Joystick joystick;

    Save save;
    boolean start = true;

    boolean is_pause = false, debag = false;
    float exit_timer = 0, sensor_timer = 0f;

    boolean fir1 = true;
    int i;

    Viewport viewport;

    Iterator iter;

    boolean isPack;
    String packname = "";
    String derectory = "";

    public PlayScreen(Main m, String path, Player p, boolean isPack) {
        this.m = m;
        this.path = path;
        this.player = p;
        this.isPack = isPack;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        fbo = new FrameBuffer(Pixmap.Format.RGB888, width, height, false);
        fbo2 = new FrameBuffer(Pixmap.Format.RGB888, width, height, false);
    }

    public PlayScreen(Main m, String path, Player p, boolean isPack, String packname, String derectory) {
        this.m = m;
        this.path = path;
        this.player = p;
        this.isPack = isPack;
        this.packname = packname;
        this.derectory = derectory;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        fbo = new FrameBuffer(Pixmap.Format.RGB888, width, height, false);
        fbo2 = new FrameBuffer(Pixmap.Format.RGB888, width, height, false);
    }

    @Override
    public void show() {
        random = new Random();
        cam = new OrthographicCamera(width, height);
        viewport = new ScreenViewport(cam);
        font = new Font();
        joystick = new Joystick();
        debag = Configuration.isDebug();
        f1 = font.gFont(11 * Main.getConfiguration().getScale(), "fonts/GFont.ttf");
        f2 = font.gFont(6 * Main.getConfiguration().getScale(), "fonts/GFont.ttf");
        f4 = font.gFont(3 * Main.getConfiguration().getScale(), "fonts/GFont.ttf");
        f3 = new BitmapFont();
        f3.setColor(Color.WHITE);
        if (isPack) {
            lR = new LevelRead(derectory + path, isPack);
        } else {
            lR = new LevelRead(path, isPack);
        }


        map = new Map(lR.getWight(), lR.getHeight(), lR.getMap(), true);
        player.setX(lR.getSpawnX());
        player.setY(lR.getSpawnY());
        player.wCordNormalize();
        player.setMap(map);
        map.setDark(!lR.isDark());

        if (lR.isStair()) {
            iter = lR.getStair().iterator();
            while (iter.hasNext()) {
                stairС = (StairC) iter.next();
                stair.add(new Stair(stairС.getX(), stairС.getY(), stairС.getFlipX(), stairС.getNext(), stairС.isEnd()));
            }

        }

        if (lR.isTeleport()) {
            iter = lR.getTeleport().iterator();
            while (iter.hasNext()) {
                teleportC = (TeleportC) iter.next();
                tp.add(new Teleport(teleportC.getX(), teleportC.getY(), teleportC.getTx(), teleportC.getTy()));
            }

        }
        if (lR.isHideTrap()) {
            iter = lR.getHide_trap().iterator();
            while (iter.hasNext()) {
                hideTrapC = (HideTrapC) iter.next();
                hideTrap.add(new HideTrap(hideTrapC.getX(), hideTrapC.getY()));
            }
        }
        if (lR.isFlamefrower()) {
            iter = lR.getFlamefrower().iterator();
            while (iter.hasNext()) {
                flamefrowerС = (FlamefrowerC) iter.next();
                flame.add(new Flamefrower(flamefrowerС.getX(), flamefrowerС.getY(), flamefrowerС.getStage(), flamefrowerС.getMax(), flamefrowerС.getRot()));
            }

        }
        if (lR.isCrossbow()) {
            iter = lR.getCrosbow().iterator();
            while (iter.hasNext()) {
                crossbowC = (CrossbowC) iter.next();
                crossbow.add(new Crossbow(crossbowC.getX(), crossbowC.getY(), crossbowC.getDx(), crossbowC.getDy(), crossbowC.getAngle()));
            }
        }

        if (lR.isItem()) {
            iter = lR.getItem().iterator();
            while (iter.hasNext()) {
                itemC = (ItemC) iter.next();
                item.add(new Item(itemC.getX(), itemC.getY(), itemC.getId()));
            }
        }

        if (lR.isFlimsyTile()) {
            iter = lR.getFlimsy_tile().iterator();
            while (iter.hasNext()) {
                flimsyTileC = (FlimsyTileC) iter.next();
                flimsyTiles.add(new FlimsyTile(flimsyTileC.getX(), flimsyTileC.getY(), flimsyTileC.getStage()));
            }
        }

        if (lR.isSpinney()) {
            iter = lR.getSpinney().iterator();
            while (iter.hasNext()) {
                spinneyC = (SpinneyC) iter.next();
                spinney.add(new Spinney(spinneyC.getX(), spinneyC.getY(), spinneyC.getWight(), spinneyC.getHeight()));
            }
        }

        if (lR.isSpike()) {
            iter = lR.getSpike().iterator();
            while (iter.hasNext()) {
                spikeC = (SpikeC) iter.next();
                spike.add(new Spike(spikeC.getX(), spikeC.getY(), spikeC.isActive()));
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
        } else {
            sensor_timer = 0;
        }
        fbo.begin();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        map.render(batch);

        object_update();

        map.dark_render(batch);
        if (!is_pause) {
            Player.render_bag(batch, f4, joystick);
            if (start)
                f2.draw(batch, lR.getLevelname(), 8 * Configuration.getScale(), 4.5f * 16 * Configuration.getScale());
        }
        if (Player.getHp() <= 0 && !is_pause) {
            wasted_render();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            is_pause = !is_pause;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            debag = !debag;
        }
        if (is_pause) {
            pauseMenu();

        }
        joystick.render(batch);
        if (debag) debugShow();
            batch.end();
        fbo.end();
        if (Player.getX() != lR.getSpawnX() || Player.getY() != lR.getSpawnY()) start = false;

        if (!is_pause) Player.tick(0.1f);
        if (isPack) {
            if (lR.isStair()) {
                for (i = 0; i < stair.size(); i++) {
                    stair.get(i).frame_isPack(m, joystick, isPack, packname, derectory);
                    if (stair.get(i).isExit()) {
                        dispose();
                        if (stair.get(i).isEnd()) {
                            m.setScreen(new GameScreen(m));
                        } else {
                            m.setScreen(new PlayScreen(m, stair.get(i).getNext_path(), player, isPack, packname, derectory));
                        }
                    }
                }
            }
        } else {
            if (lR.isStair()) {
                for (i = 0; i < stair.size(); i++) {
                    stair.get(i).frame(m, joystick, isPack);
                    if (stair.get(i).isExit()) {
                        System.out.println(stairС.isEnd());
                        dispose();
                        if (stair.get(i).isEnd()) {
                            m.setScreen(new GameScreen(m));
                        } else {
                            m.setScreen(new PlayScreen(m, stair.get(i).getNext_path(), player, isPack));
                        }
                    }
                }
            }
        }

        frame = new Sprite(fbo.getColorBufferTexture());


        fbo2.begin();
        batch.begin();

        frame.draw(batch);
        //frame.setPosition(space/2,0);

        batch.end();
        fbo2.end();

        frame = new Sprite(fbo2.getColorBufferTexture());
        frame.setPosition((scrW - 16 * 7 * Configuration.getScale()) / 2, (scrH - 16 * 5 * Configuration.getScale()) / 2);

        cam.update();

        batch.begin();

        batch.draw(Res.boards, 0, 0, 16 * 7 * Configuration.getScale() * 10, 16 * 5 * Configuration.getScale() * 10);
        if (is_pause) {
            batch.draw(Res.pause_dark, 0, 0, 16 * 7 * Configuration.getScale() * 10, 16 * 5 * Configuration.getScale() * 10);
        }
        frame.draw(batch);

        batch.end();
    }

    private void wasted_render() {
        if (fir1) Res.wasted.play(2.0f);
        fir1 = false;
        f1.draw(batch, "Wasted", 2 * 16 * Configuration.getScale(), 4.5f * 16 * Configuration.getScale());
        f2.draw(batch, "Press Space", 2 * 16 * Configuration.getScale(), 0.5f * 16 * Configuration.getScale());
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || joystick.isUse()) {
            m.setScreen(new GameScreen(m));

        }
    }

    private void object_update() {
        if (lR.isFlimsyTile()) {
            for (i = 0; i < flimsyTiles.size(); i++) {
                if (!is_pause) flimsyTiles.get(i).tick(1f);
                flimsyTiles.get(i).render(batch);
                if (!is_pause) flimsyTiles.get(i).frame();
            }
        }

        if (lR.isStair()) {
            for (i = 0; i < stair.size(); i++) {
                stair.get(i).render(batch);
                if (!is_pause) stair.get(i).frame(m, joystick, isPack);
            }
        }

        if (lR.isTeleport()) {
            for (i = 0; i < tp.size(); i++) {
                tp.get(i).render(batch);
                if (!is_pause) tp.get(i).frame(joystick);
            }
        }
        if (lR.isHideTrap()) {
            for (i = 0; i < hideTrap.size(); i++) {
                if (!is_pause) hideTrap.get(i).frame();
            }
        }
        if (lR.isSpike()) {
            for (i = 0; i < spike.size(); i++) {
                if (!is_pause) spike.get(i).frame();
                if (!is_pause) spike.get(i).tick(1.5f);
                spike.get(i).render(batch);
            }
        }
        if (lR.isItem()) {
            for (i = 0; i < item.size(); i++) {
                item.get(i).render(batch);
                if (!is_pause) item.get(i).frame();
            }
        }
        player.render(batch);

        if (lR.isFlamefrower()) {
            for (i = 0; i < flame.size(); i++) {
                if (!is_pause) flame.get(i).tick(1.5f);
                if (!is_pause) flame.get(i).tick1(0.1f);
                flame.get(i).render(batch);
                if (!is_pause) flame.get(i).frame();
            }
        }
        if (lR.isCrossbow()) {
            for (i = 0; i < crossbow.size(); i++) {
                if (!is_pause) crossbow.get(i).tick(1.5f);
                crossbow.get(i).render(batch);
                if (!is_pause) crossbow.get(i).frame();
            }
        }
        if (lR.isSpinney()) {
            for (i = 0; i < spinney.size(); i++) {
                spinney.get(i).render(batch);
            }
        }
    }

    void debugShow() {
        line = "Debug:";
        f3.draw(batch, line, 10, 5f * 16 * Configuration.getScale() - 10);
        line = "FPS:" + Gdx.graphics.getFramesPerSecond();
        f3.draw(batch, line, 10, 5f * 16 * Configuration.getScale() - 25);
        line = "X:" + Player.getX() + "  Y:" + Player.getY();
        f3.draw(batch, line, 10, 5f * 16 * Configuration.getScale() - 40);
        line = "WX:" + Player.get_wX() + "  WY:" + Player.get_wY();
        f3.draw(batch, line, 10, 5f * 16 * Configuration.getScale() - 55);
        line = "JavaHeap:" + (((int) Gdx.app.getJavaHeap()) / (8 * 1024)) + " KB";
        f3.draw(batch, line, 10, 5f * 16 * Configuration.getScale() - 70);
        line = "NativeHeap:" + (((int) Gdx.app.getNativeHeap()) / (8 * 1024)) + " KB";
        f3.draw(batch, line, 10, 5f * 16 * Configuration.getScale() - 85);
    }



    void pauseMenu() {
        joystick.frame((int) (scrW - 16 * 7 * Configuration.getScale()) / 2, (int) (scrH - 16 * 5 * Configuration.getScale()) / 2);

        batch.draw(Res.pause_dark, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        f1.draw(batch, "Pause", 2.2f * 16 * Configuration.getScale(), 4.5f * 16 * Configuration.getScale());
        if (menu == 0) {
            f2.draw(batch, ">Resume<", 2.2f * 16 * Configuration.getScale(), 3.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Main menu", 2.2f * 16 * Configuration.getScale(), 2.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Load save", 2.2f * 16 * Configuration.getScale(), 1.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Exit(Hold)", 2.2f * 16 * Configuration.getScale(), 0.5f * 16 * Configuration.getScale());
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || joystick.isUse()) {
                is_pause = false;
            }
        }
        if (menu == 1) {
            f2.draw(batch, " Resume", 2.2f * 16 * Configuration.getScale(), 3.5f * 16 * Configuration.getScale());
            f2.draw(batch, ">Main menu<", 2.2f * 16 * Configuration.getScale(), 2.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Load save", 2.2f * 16 * Configuration.getScale(), 1.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Exit(Hold)", 2.2f * 16 * Configuration.getScale(), 0.5f * 16 * Configuration.getScale());
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || joystick.isUse()) {
                dispose();
                m.setScreen(new GameScreen(m));
            }
        }
        if(menu==2) {
            f2.draw(batch, " Resume", 2.2f * 16 * Configuration.getScale(), 3.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Main menu", 2.2f * 16 * Configuration.getScale(), 2.5f * 16 * Configuration.getScale());
            f2.draw(batch, ">Load save<", 2.2f * 16 * Configuration.getScale(), 1.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Exit(Hold)", 2.2f * 16 * Configuration.getScale(), 0.5f * 16 * Configuration.getScale());
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || joystick.isUse()) {
                dispose();
                if (isPack) {
                    m.setScreen(new PlayScreen(m, save.getsPath(), new Player(0, 0, null, save.getHp(), save.getPotion(), save.getSheld(), save.getTorch()), isPack, packname, derectory));
                } else
                    m.setScreen(new PlayScreen(m, save.getsPath(), new Player(0, 0, null, save.getHp(), save.getPotion(), save.getSheld(), save.getTorch()), isPack));
            }

        }

        if(menu==3) {
            f2.draw(batch, " Resume", 2.2f * 16 * Configuration.getScale(), 3.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Main menu", 2.2f * 16 * Configuration.getScale(), 2.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Load save", 2.2f * 16 * Configuration.getScale(), 1.5f * 16 * Configuration.getScale());
            f2.draw(batch, ">Exit(Hold)<", 2.2f * 16 * Configuration.getScale(), 0.5f * 16 * Configuration.getScale());
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || joystick.isUse()) {
                exit_timer += Gdx.graphics.getDeltaTime();
                if (exit_timer > 1.0f) {
                    System.exit(0);
                }
            } else {
                exit_timer = 0;
            }
        }else{
            exit_timer = 0;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            menu--;
            Res.click[random.nextInt(2)].play(Main.getConfiguration().getSound());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)||(!joystick.isUse()&&Gdx.input.justTouched())) {
            menu++;
            Res.click[random.nextInt(2)].play(Main.getConfiguration().getSound());
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
        fbo.dispose();
        fbo2.dispose();
        int ry = height - PlayScreen.height;
        int rx = width - PlayScreen.width;
        this.width = width;
        this.height = height;
        scrW = width;
        scrH = height;
        viewport.update(width, height);
        cam.translate(rx / 2, ry / 2, cam.position.z);
        cam.update();
        fbo = new FrameBuffer(Pixmap.Format.RGB888, width, height, false);
        fbo2 = new FrameBuffer(Pixmap.Format.RGB888, width, height, false);
    }

    @Override
    public void pause() {
        is_pause = true;
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        System.out.println("123");
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
