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
import com.zaig100.dg.objects.Map;
import com.zaig100.dg.objects.Player;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Font;
import com.zaig100.dg.utils.Joystick;
import com.zaig100.dg.utils.LevelRead;
import com.zaig100.dg.utils.Res;
import com.zaig100.dg.utils.Save;
import com.zaig100.dg.utils.ShaderManager;

import java.io.IOException;
import java.util.Random;

public class PlayScreen implements Screen {

    private FrameBuffer fbo, fbo2;
    private SpriteBatch batch;
    private OrthographicCamera cam;
    public static Main m;
    private Sprite frame;
    static LevelRead lR;
    static Font font;
    static Random random;
    private BitmapFont f1, f2, f3, f4;
    private int height;
    private int width;

    private int scrW, scrH;
    private String path, line;

    //public Map map;

    private int menu = 0;
    //public ArrayList<Obj> objectsU = new ArrayList<>();
    //public ArrayList<Obj> objectsO = new ArrayList<>();
    //public ArrayList<Stair> stair = new ArrayList<>();
    //private StairC stairС;
    //private TeleportC teleportC;
    //private HideTrapC hideTrapC;
    //private FlamefrowerC flamefrowerС;
    //private CrossbowC crossbowC;
    //private ItemC itemC;
    //private FlimsyTileC flimsyTileC;
    //private SpinneyC spinneyC;
    //private SpikeC spikeC;
    //private ButtonС buttonC;
    //private DoorC doorC;
    private Texture fr;
    private Save save;
    private boolean start = true;


    private boolean debag = false;
    private float exit_timer = 0, sensor_timer = 0f;

    private boolean fir1 = true;
    private int i;


    private Viewport viewport;

    //private Iterator iter;

    private boolean isPack;
    private String packname = "";
    private String derectory = "";

    public PlayScreen(Main m, String path, boolean isPack) {
        PlayScreen.m = m;
        this.path = path;
        this.isPack = isPack;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        fbo = new FrameBuffer(Pixmap.Format.RGB888, width, height, false);
        fbo2 = new FrameBuffer(Pixmap.Format.RGB888, width, height, false);
    }

    public PlayScreen(Main m, String path, boolean isPack, String packname, String derectory) {
        PlayScreen.m = m;
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
        Player.setMap(new Map(lR.getWight(), lR.getHeight(), lR.getMap(), true));
        Player.map.object_load(lR);
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
                save.setArr(Player.inventory.inventoryToJSON());
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
                Player.isPause = !Player.isPause;
                Player.isStop = Player.isPause;
                Player.inventarIsOpen = false;
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
        Player.map.render(batch);

        stair_update();

        Player.getMap().object_update(batch);

        Player.map.dark_render(batch);

        render_ui();
    }

    private void second_render() {
        batch.draw(Res.boards, 0, 0, 16 * 7 * Configuration.getScale() * 10, 16 * 5 * Configuration.getScale() * 10);
        if (Player.isPause) {
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
        if (Player.getMap().stair.size() > 0) {
            for (i = 0; i < Player.getMap().stair.size(); i++) {
                Player.getMap().stair.get(i).render(batch);
                Player.getMap().stair.get(i).frame();
                if (Player.getMap().stair.get(i).isExit()) {
                    dispose();
                    if (Player.getMap().stair.get(i).isEnd()) {
                        m.setScreen(new GameScreen(m));
                    } else {
                        if (isPack) {
                            m.setScreen(new PlayScreen(m, Player.getMap().stair.get(i).getNext_path(), isPack, packname, derectory));
                        } else {
                            m.setScreen(new PlayScreen(m, Player.getMap().stair.get(i).getNext_path(), isPack));
                        }
                    }
                }
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
        batch.draw(Res.pause_dark, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        f1.draw(batch, "Pause", 2.2f * 16 * Configuration.getScale(), 4.5f * 16 * Configuration.getScale());
        if (menu == 0) {
            f2.draw(batch, ">Resume<", 2.2f * 16 * Configuration.getScale(), 3.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Main menu", 2.2f * 16 * Configuration.getScale(), 2.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Load save", 2.2f * 16 * Configuration.getScale(), 1.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Exit(Hold)", 2.2f * 16 * Configuration.getScale(), 0.5f * 16 * Configuration.getScale());
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || (Joystick.isUse() && Gdx.input.justTouched())) {
                Player.isPause = false;
                Player.isStop = false;
                Player.inventarIsOpen = false;
            }
        }
        if (menu == 1) {
            f2.draw(batch, " Resume", 2.2f * 16 * Configuration.getScale(), 3.5f * 16 * Configuration.getScale());
            f2.draw(batch, ">Main menu<", 2.2f * 16 * Configuration.getScale(), 2.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Load save", 2.2f * 16 * Configuration.getScale(), 1.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Exit(Hold)", 2.2f * 16 * Configuration.getScale(), 0.5f * 16 * Configuration.getScale());
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || (Joystick.isUse() && Gdx.input.justTouched())) {
                dispose();
                m.setScreen(new GameScreen(m));
                Player.isPause = false;
                Player.isStop = false;
                Player.inventarIsOpen = false;
            }
        }
        if(menu==2) {
            f2.draw(batch, " Resume", 2.2f * 16 * Configuration.getScale(), 3.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Main menu", 2.2f * 16 * Configuration.getScale(), 2.5f * 16 * Configuration.getScale());
            f2.draw(batch, ">Load save<", 2.2f * 16 * Configuration.getScale(), 1.5f * 16 * Configuration.getScale());
            f2.draw(batch, " Exit(Hold)", 2.2f * 16 * Configuration.getScale(), 0.5f * 16 * Configuration.getScale());
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || (Joystick.isUse() && Gdx.input.justTouched())) {
                dispose();
                Player.setHp(save.getHp());
                Player.inventory.jsonToInventory(save.getArr());
                Player.isPause = false;
                Player.isStop = false;
                Player.inventarIsOpen = false;
                Player.setIsSheld(false);
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
                    Player.isPause = false;
                    Player.isStop = false;
                    Player.inventarIsOpen = false;
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

        if (!Player.isPause) {
            Player.render_menu(batch, f4);
            if (start)
                f2.draw(batch, lR.getLevelname(), 8 * Configuration.getScale(), 4.5f * 16 * Configuration.getScale());
        }
        if (Player.getHp() <= 0 && !Player.isPause) {
            wasted_render();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (Player.inventarIsOpen) {
                Player.inventarIsOpen = false;
            } else {
                Player.isPause = !Player.isPause;
            }
            Player.isStop = Player.isPause;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            debag = !debag;
        }
        if (Player.isPause) {
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
        Player.isPause = true;
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
