package com.zaig100.dg.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zaig100.dg.Main;
import com.zaig100.dg.objects.Button;
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
import com.zaig100.dg.utils.ShaderManager;
import com.zaig100.dg.utils.contain.ButtonС;
import com.zaig100.dg.utils.contain.CrossbowC;
import com.zaig100.dg.utils.contain.FlamefrowerC;
import com.zaig100.dg.utils.contain.FlimsyTileC;
import com.zaig100.dg.utils.contain.HideTrapC;
import com.zaig100.dg.utils.contain.ItemC;
import com.zaig100.dg.utils.contain.SpikeC;
import com.zaig100.dg.utils.contain.SpinneyC;
import com.zaig100.dg.utils.contain.StairC;
import com.zaig100.dg.utils.contain.TeleportC;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class PlayScreen implements Screen {

    private FrameBuffer fbo, fbo2;
    private SpriteBatch batch;
    private OrthographicCamera cam;
    private Main m;
    private Sprite frame;
    static LevelRead lR;
    static Font font;
    static Random random;
    private BitmapFont f1, f2, f3, f4;
    private int height;
    private int width;

    private int scrW, scrH;
    private String path, line;

    private Map map;

    private int menu = 0;
    public ArrayList<Obj> objectsU = new ArrayList<>();
    public ArrayList<Obj> objectsO = new ArrayList<>();
    public ArrayList<Stair> stair = new ArrayList<>();
    private StairC stairС;
    private TeleportC teleportC;
    private HideTrapC hideTrapC;
    private FlamefrowerC flamefrowerС;
    private CrossbowC crossbowC;
    private ItemC itemC;
    private FlimsyTileC flimsyTileC;
    private SpinneyC spinneyC;
    private SpikeC spikeC;
    private ButtonС buttonC;
    private Texture fr;
    private Save save;
    private boolean start = true;


    private boolean is_pause = false, debag = false;
    private float exit_timer = 0, sensor_timer = 0f;

    private boolean fir1 = true;
    private int i, idNum = 0;


    private Viewport viewport;

    private Iterator iter;

    private boolean isPack;
    private String packname = "";
    private String derectory = "";

    public PlayScreen(Main m, String path, boolean isPack) {
        this.m = m;
        this.path = path;
        this.isPack = isPack;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        fbo = new FrameBuffer(Pixmap.Format.RGB888, width, height, false);
        fbo2 = new FrameBuffer(Pixmap.Format.RGB888, width, height, false);
    }

    public PlayScreen(Main m, String path, boolean isPack, String packname, String derectory) {
        this.m = m;
        this.path = path;
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
        f1 = font.gFont(11 * Configuration.getScale(), "fonts/GFont.ttf");
        f2 = font.gFont(6 * Configuration.getScale(), "fonts/GFont.ttf");
        f4 = font.gFont(3 * Configuration.getScale(), "fonts/GFont.ttf");
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
                save.setHp(Player.getHp());
                save.setPath(path);
                save.setPotion(Player.getPotion());
                save.setSheld(Player.getSheld());
                save.setTorch(Player.getTorch());
                save.save(save.getConf());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void render(float delta) {
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
        first_render();
        batch.end();
        fbo.end();

        if (Player.getX() != lR.getSpawnX() || Player.getY() != lR.getSpawnY()) start = false;

        frame = new Sprite(fbo.getColorBufferTexture());
        frame.setPosition((scrW - 16 * 7 * Configuration.getScale()) / 2, (-scrH + 16 * 5 * Configuration.getScale()) / 2);

        fbo2.begin();
        batch.begin();
        second_render();
        batch.end();
        fbo2.end();


        frame = new Sprite(fbo2.getColorBufferTexture());
        cam.update();

        fbo.begin();
        batch.begin();
        third_render();
        batch.end();
        fbo.end();

        frame = new Sprite(fbo.getColorBufferTexture());

        if (Configuration.getShader() != -1) {
            frame.setFlip(ShaderManager.getShaders().get(Configuration.getShader()).isFlipX(), ShaderManager.getShaders().get(Configuration.getShader()).isFlipY());
        } else {
            frame.setFlip(false, true);
        }

        batch.begin();
        frame.draw(batch);
        batch.end();

    }


    private void first_render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        map.render(batch);

        stair_update();

        object_update();

        map.dark_render(batch);

        render_ui();
    }

    private void second_render() {
        batch.draw(Res.boards, 0, 0, 16 * 7 * Configuration.getScale() * 10, 16 * 5 * Configuration.getScale() * 10);
        if (is_pause) {
            batch.draw(Res.pause_dark, 0, 0, 16 * 7 * Configuration.getScale() * 10, 16 * 5 * Configuration.getScale() * 10);
        }
        frame.draw(batch);
    }

    private void third_render() {
        if (Configuration.getShader() != -1) {
            ShaderManager.getShaders().get(Configuration.getShader()).shader_update();
            batch.setShader(ShaderManager.getShaders().get(Configuration.getShader()).getShader());
        }
        frame.draw(batch);
        if (Configuration.getShader() != -1) {
            batch.setShader(null);
        }
    }

    private void stair_update() {
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
                            m.setScreen(new PlayScreen(m, stair.get(i).getNext_path(), isPack, packname, derectory));
                        } else {
                            m.setScreen(new PlayScreen(m, stair.get(i).getNext_path(), isPack));
                        }
                    }
                }
            }
        }
    }

    private void object_update() {
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
    }
    private void object_load() {
        map = new Map(lR.getWight(), lR.getHeight(), lR.getMap(), true);
        Player.setX(lR.getSpawnX());
        Player.setY(lR.getSpawnY());
        Player.wCordNormalize();
        Player.setMap(map);
        map.setDark(!lR.isDark());


        iter = lR.getStair().iterator();
        while (iter.hasNext()) {
            stairС = (StairC) iter.next();
            stair.add(new Stair(stairС.getX(), stairС.getY(), stairС.getFlipX(), stairС.getNext(), stairС.isEnd(), stairС.getTag()));
            stair.get(stair.size() - 1).setObjID(idNum);
            idNum++;
        }

        iter = lR.getTeleport().iterator();
        while (iter.hasNext()) {
            teleportC = (TeleportC) iter.next();
            objectsU.add(new Teleport(teleportC.getX(), teleportC.getY(), teleportC.getTx(), teleportC.getTy(), teleportC.getTag()));
            objectsU.get(objectsU.size() - 1).setObjID(idNum);
            idNum++;
        }

        iter = lR.getHide_trap().iterator();
        while (iter.hasNext()) {
            hideTrapC = (HideTrapC) iter.next();
            objectsU.add(new HideTrap(hideTrapC.getX(), hideTrapC.getY(), hideTrapC.isActive(), hideTrapC.getTag()));
            objectsU.get(objectsU.size() - 1).setObjID(idNum);
            idNum++;
        }

        iter = lR.getFlamefrower().iterator();
        while (iter.hasNext()) {
            flamefrowerС = (FlamefrowerC) iter.next();
            objectsO.add(new Flamefrower(flamefrowerС.getX(), flamefrowerС.getY(), flamefrowerС.getStage(), flamefrowerС.getMax(), flamefrowerС.getRot(), flamefrowerС.getTick_sec(), flamefrowerС.getTag()));
            objectsO.get(objectsO.size() - 1).setObjID(idNum);
            idNum++;
        }

        iter = lR.getCrosbow().iterator();
        while (iter.hasNext()) {
            crossbowC = (CrossbowC) iter.next();
            objectsO.add(new Crossbow(crossbowC.getX(), crossbowC.getY(), crossbowC.getDx(), crossbowC.getDy(), crossbowC.getAngle(), crossbowC.getTick_sec(), crossbowC.getTag()));
            objectsO.get(objectsO.size() - 1).setObjID(idNum);
            idNum++;
        }
        iter = lR.getItem().iterator();
        while (iter.hasNext()) {
            itemC = (ItemC) iter.next();
            objectsO.add(new Item(itemC.getX(), itemC.getY(), itemC.getId(), itemC.isActive(), itemC.getTag()));
            objectsO.get(objectsO.size() - 1).setObjID(idNum);
            idNum++;
        }

        iter = lR.getFlimsy_tile().iterator();
        while (iter.hasNext()) {
            flimsyTileC = (FlimsyTileC) iter.next();
            objectsU.add(new FlimsyTile(flimsyTileC.getX(), flimsyTileC.getY(), flimsyTileC.getStage(), flimsyTileC.getTick_sec(), flimsyTileC.getTag()));
            objectsU.get(objectsU.size() - 1).setObjID(idNum);
            idNum++;
        }

        iter = lR.getSpinney().iterator();
        while (iter.hasNext()) {
            spinneyC = (SpinneyC) iter.next();
            objectsO.add(new Spinney(spinneyC.getX(), spinneyC.getY(), spinneyC.getWight(), spinneyC.getHeight(), spinneyC.getTag()));
            objectsO.get(objectsO.size() - 1).setObjID(idNum);
            idNum++;
        }

        iter = lR.getSpike().iterator();
        while (iter.hasNext()) {
            spikeC = (SpikeC) iter.next();
            objectsU.add(new Spike(spikeC.getX(), spikeC.getY(), spikeC.isActive(), spikeC.getTick_sec(), spikeC.getTag()));
            objectsU.get(objectsU.size() - 1).setObjID(idNum);
            idNum++;
        }

        iter = lR.getButton().iterator();
        while (iter.hasNext()) {
            buttonC = (ButtonС) iter.next();
            objectsU.add((new Button(buttonC.getX(), buttonC.getY(), buttonC.getFunc(), buttonC.getTag()).setPlayScreen(this)));
            objectsU.get(objectsU.size() - 1).setObjID(idNum);
            idNum++;
        }

    }
    public void doFunc(String func) {
        if (stair.size() > 0) {
            for (i = 0; i < stair.size(); i++) {
                if (stair.get(i).getTag().equals(func.split(":")[0])) {
                    stair.get(i).tag_activate(func.split(":")[1]);
                }
            }
        }
        if (objectsU.size() > 0) {
            for (i = 0; i < objectsU.size(); i++) {
                if (objectsU.get(i).getTag().equals(func.split(":")[0])) {
                    objectsU.get(i).tag_activate(func.split(":")[1]);
                }
            }
        }
        if (objectsO.size() > 0) {
            for (i = 0; i < objectsO.size(); i++) {
                if (objectsO.get(i).getTag().equals(func.split(":")[0])) {
                    objectsO.get(i).tag_activate(func.split(":")[1]);
                }
            }
        }
        if (func.split(":")[0].equals("Map")) {
            map.tag_activate(func.split(":")[1]);
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
                Player.setHp(save.getHp());
                Player.setPotion(save.getPotion());
                Player.setSheld(save.getSheld());
                Player.setTorch(save.getTorch());
                if (isPack) {
                    m.setScreen(new PlayScreen(m, save.getsPath(), isPack, packname, derectory));
                } else
                    m.setScreen(new PlayScreen(m, save.getsPath(), isPack));
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
                    Gdx.app.exit();
                }
            } else {
                exit_timer = 0;
            }
        }else{
            exit_timer = 0;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            menu--;
            Res.click[random.nextInt(2)].play(Configuration.getSound());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S) || (!Joystick.isUse() && Gdx.input.justTouched())) {
            menu++;
            Res.click[random.nextInt(2)].play(Configuration.getSound());
        }

        if (menu >= 4) {
            menu = 0;
        }

        if (menu < 0) {
            menu = 3;
        }

    }

    private void render_ui() {

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

    @Override
    public void resize(int width, int height) {
        fbo.dispose();
        fbo2.dispose();
        int ry = height - this.height;
        int rx = width - this.width;
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
