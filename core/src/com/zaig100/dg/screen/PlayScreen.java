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
import com.zaig100.dg.objects.Obj;
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
    ArrayList<Obj> objectsU = new ArrayList<>();
    ArrayList<Obj> objectsO = new ArrayList<>();
    ArrayList<Stair> stair = new ArrayList<>();
    StairC stairС;
    TeleportC teleportC;
    HideTrapC hideTrapC;
    FlamefrowerC flamefrowerС;
    CrossbowC crossbowC;
    ItemC itemC;
    FlimsyTileC flimsyTileC;
    SpinneyC spinneyC;
    SpikeC spikeC;

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
        object_load();

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
        Joystick.frame((int) ((Gdx.graphics.getWidth() - 16 * 7 * Configuration.getScale()) / 2), (int) ((Gdx.graphics.getHeight() - 16 * 5 * Configuration.getScale()) / 2));
        Joystick.frame((int) ((Gdx.graphics.getWidth() - 16 * 7 * Configuration.getScale()) / 2), (int) ((Gdx.graphics.getHeight() - 16 * 5 * Configuration.getScale()) / 2));
        if (!Joystick.isJoystick() && Gdx.input.isTouched()) {
            sensor_timer += delta;
            if (sensor_timer > 1f) {
                is_pause = !is_pause;
                sensor_timer = 0;
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

        if (stair.size() > 0) {
            for (i = 0; i < stair.size(); i++) {
                stair.get(i).render(batch);
                stair.get(i).frame();
                if (stair.get(i).isExit()) {
                    System.out.println(stairС.isEnd());
                    dispose();
                    if (stair.get(i).isEnd()) {
                        m.setScreen(new GameScreen(m));
                    } else {
                        if (isPack) {
                            m.setScreen(new PlayScreen(m, stair.get(i).getNext_path(), player, isPack, packname, derectory));
                        } else {
                            m.setScreen(new PlayScreen(m, stair.get(i).getNext_path(), player, isPack));
                        }
                    }
                }
            }
        }

        if (objectsU.size() > 0) {
            for (i = 0; i < objectsU.size(); i++) {
                objectsU.get(i).render(batch);
                if (!is_pause) objectsU.get(i).frame();
            }
        }
        Player.render(batch);
        if (!is_pause) Player.tick(0.2f);
        if (!is_pause) Player.frame();

        if (objectsO.size() > 0) {
            for (i = 0; i < objectsO.size(); i++) {
                objectsO.get(i).render(batch);
                if (!is_pause) objectsO.get(i).frame();
            }
        }

        map.dark_render(batch);
        if (!is_pause) {
            Player.render_bag(batch, f4);
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
        Joystick.render(batch);
        if (debag) debugShow();
            batch.end();
        fbo.end();
        if (Player.getX() != lR.getSpawnX() || Player.getY() != lR.getSpawnY()) start = false;

        frame = new Sprite(fbo.getColorBufferTexture());

        fbo2.begin();
        batch.begin();

        frame.draw(batch);

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
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Joystick.isUse()) {
            m.setScreen(new GameScreen(m));

        }
    }

    private void object_load() {
        map = new Map(lR.getWight(), lR.getHeight(), lR.getMap(), true);
        player.setX(lR.getSpawnX());
        player.setY(lR.getSpawnY());
        player.wCordNormalize();
        player.setMap(map);
        map.setDark(!lR.isDark());


        iter = lR.getStair().iterator();
        while (iter.hasNext()) {
            stairС = (StairC) iter.next();
            stair.add(new Stair(stairС.getX(), stairС.getY(), stairС.getFlipX(), stairС.getNext(), stairС.isEnd(), stairС.getTag()));
        }

        iter = lR.getTeleport().iterator();
        while (iter.hasNext()) {
            teleportC = (TeleportC) iter.next();
            objectsU.add(new Teleport(teleportC.getX(), teleportC.getY(), teleportC.getTx(), teleportC.getTy(), teleportC.getTag()));
        }

        iter = lR.getHide_trap().iterator();
        while (iter.hasNext()) {
            hideTrapC = (HideTrapC) iter.next();
            objectsU.add(new HideTrap(hideTrapC.getX(), hideTrapC.getY(), hideTrapC.getTag()));
        }

        iter = lR.getFlamefrower().iterator();
        while (iter.hasNext()) {
            flamefrowerС = (FlamefrowerC) iter.next();
            objectsO.add(new Flamefrower(flamefrowerС.getX(), flamefrowerС.getY(), flamefrowerС.getStage(), flamefrowerС.getMax(), flamefrowerС.getRot(), flamefrowerС.getTag()));
        }

        iter = lR.getCrosbow().iterator();
        while (iter.hasNext()) {
            crossbowC = (CrossbowC) iter.next();
            objectsO.add(new Crossbow(crossbowC.getX(), crossbowC.getY(), crossbowC.getDx(), crossbowC.getDy(), crossbowC.getAngle(), crossbowC.getTag()));
        }
        iter = lR.getItem().iterator();
        while (iter.hasNext()) {
            itemC = (ItemC) iter.next();
            objectsO.add(new Item(itemC.getX(), itemC.getY(), itemC.getId(), itemC.getTag()));
        }

        iter = lR.getFlimsy_tile().iterator();
        while (iter.hasNext()) {
            flimsyTileC = (FlimsyTileC) iter.next();
            objectsU.add(new FlimsyTile(flimsyTileC.getX(), flimsyTileC.getY(), flimsyTileC.getStage(), flimsyTileC.getTag()));
        }

        iter = lR.getSpinney().iterator();
        while (iter.hasNext()) {
            spinneyC = (SpinneyC) iter.next();
            objectsO.add(new Spinney(spinneyC.getX(), spinneyC.getY(), spinneyC.getWight(), spinneyC.getHeight(), spinneyC.getTag()));
        }

        iter = lR.getSpike().iterator();
        while (iter.hasNext()) {
            spikeC = (SpikeC) iter.next();
            objectsU.add(new Spike(spikeC.getX(), spikeC.getY(), spikeC.isActive(), spikeC.getTag()));
        }

    }

    private void object_update() {

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
        batch.draw(Res.pause_dark, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        f1.draw(batch, "Pause", 2.2f * 16 * Configuration.getScale(), 4.5f * 16 * Configuration.getScale());
        if (menu == 0) {
            f2.draw(batch, ">Resume<", 2.2f * 16 * Configuration.getScale(), 3.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Main menu", 2.2f * 16 * Configuration.getScale(), 2.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Load save", 2.2f * 16 * Configuration.getScale(), 1.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Exit(Hold)", 2.2f * 16 * Configuration.getScale(), 0.5f * 16 * Configuration.getScale());
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Joystick.isUse()) {
                is_pause = false;
            }
        }
        if (menu == 1) {
            f2.draw(batch, " Resume", 2.2f * 16 * Configuration.getScale(), 3.5f * 16 * Configuration.getScale());
            f2.draw(batch, ">Main menu<", 2.2f * 16 * Configuration.getScale(), 2.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Load save", 2.2f * 16 * Configuration.getScale(), 1.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Exit(Hold)", 2.2f * 16 * Configuration.getScale(), 0.5f * 16 * Configuration.getScale());
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Joystick.isUse()) {
                dispose();
                m.setScreen(new GameScreen(m));
            }
        }
        if(menu==2) {
            f2.draw(batch, " Resume", 2.2f * 16 * Configuration.getScale(), 3.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Main menu", 2.2f * 16 * Configuration.getScale(), 2.5f * 16 * Configuration.getScale());
            f2.draw(batch, ">Load save<", 2.2f * 16 * Configuration.getScale(), 1.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Exit(Hold)", 2.2f * 16 * Configuration.getScale(), 0.5f * 16 * Configuration.getScale());
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Joystick.isUse()) {
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
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Joystick.isUse()) {
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.S) || (!Joystick.isUse() && Gdx.input.justTouched())) {
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
