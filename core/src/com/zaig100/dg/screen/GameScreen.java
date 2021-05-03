package com.zaig100.dg.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zaig100.dg.Main;
import com.zaig100.dg.objects.Player;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.LevelRead;
import com.zaig100.dg.utils.LevelReader;
import com.zaig100.dg.utils.Res;

import java.io.IOException;
import java.util.Random;

public class GameScreen implements Screen {
    FrameBuffer fbo, fbo2;
    SpriteBatch batch;
    OrthographicCamera cam;
    static Main m;
    Sprite frame;
    LevelReader lR;
    LevelRead lR2;
    static int height;
    static int width;
    static int scrW, scrH;
    Viewport viewport;
    int[][] map;
    int ry, rx;
    Random random = new Random();
    int tick = 0;
    int mX, mY;
    boolean b_Play = false;
    boolean b_Settings = false;
    boolean b_Tutorial = false;
    boolean isPres = false;
    boolean isExit = false;
    int button = 0;

    public GameScreen(Main m) {
        GameScreen.m = m;
        batch = new SpriteBatch();
        try {
            lR = new LevelReader("levels/01.json", false);
            lR2 = new LevelRead("levels/Test2.json", false);
            map = lR.getMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        fbo = new FrameBuffer(Pixmap.Format.RGB888, width, height, false);
        fbo2 = new FrameBuffer(Pixmap.Format.RGB888, width, height, false);
    }

    public void show() {

        scrH = height;
        scrW = width;
        cam = new OrthographicCamera();
        cam.translate(width/2, height/2, 0);
        viewport = new ScreenViewport(cam);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.F11)) {
            if (!Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            } else {
                Gdx.graphics.setWindowedMode((int) (16 * 7 * Configuration.getScale()), (int) (16 * 5 * Configuration.getScale()));
            }
        }

        fbo.begin();
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        backgraund();
        button_draw();
        logo_draw();
        sensor((int) (scrW - 16 * 7 * Main.getConfiguration().getScale()), (int) (scrH - 16 * 5 * Main.getConfiguration().getScale()));
        batch.end();
        fbo.end();

        frame = new Sprite(fbo.getColorBufferTexture());


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (isExit) {
            switch (button) {
                case 1:
                    dispose();
                    m.setScreen(new Extension(m));
                    break;
                case 2:
                    dispose();
                    m.setScreen(new PlayScreen(m, "levels/01.json", new Player(0, 0, null), false));
                    break;
                case 3:
                    dispose();
                    m.setScreen(new Setting(m));
                    break;
            }
        }

        fbo2.begin();
        batch.begin();

        frame.setPosition((scrW - 16 * 7 * Main.getConfiguration().getScale()) / 2, -(scrH - 16 * 5 * Main.getConfiguration().getScale()) / 2);
        frame.draw(batch);

        batch.end();
        fbo2.end();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        frame = new Sprite(fbo2.getColorBufferTexture());
        frame.setPosition(0, 0);


        batch.begin();

        batch.draw(Res.pause_dark, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        frame.draw(batch);

        batch.end();
    }

    private void backgraund() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (map[j][i] == 11) {
                    batch.draw(Res.tile(0, 0), (j - 2) * 16 * 8 / 8 * Configuration.getScale(), (i - 4) * 16 * 8 / 8 * Configuration.getScale(), 16 * 8 / 8 * Configuration.getScale(), 16 * 8 / 8 * Configuration.getScale());//backgraund
                }
                if (map[j][i] == 12) {
                    batch.draw(Res.tile(1, 1), (j - 2) * 16 * 8 / 8 * Configuration.getScale(), (i - 4) * 16 * 8 / 8 * Configuration.getScale(), 16 * 8 / 8 * Configuration.getScale(), 16 * 8 / 8 * Configuration.getScale());//backgraund
                }
                if (map[j][i] == 13) {
                    batch.draw(Res.tile(2, 0), (j - 2) * 16 * 8 / 8 * Configuration.getScale(), (i - 4) * 16 * 8 / 8 * Configuration.getScale(), 16 * 8 / 8 * Configuration.getScale(), 16 * 8 / 8 * Configuration.getScale());//backgraund
                }
                if (map[j][i] == 14) {
                    batch.draw(Res.tile(1, 0), (j - 2) * 16 * 8 / 8 * Configuration.getScale(), (i - 4) * 16 * 8 / 8 * Configuration.getScale(), 16 * 8 / 8 * Configuration.getScale(), 16 * 8 / 8 * Configuration.getScale());//backgraund
                }
                if (map[j][i] == 15) {
                    batch.draw(Res.tile(2, 1), (j - 2) * 16 * 8 / 8 * Configuration.getScale(), (i - 4) * 16 * 8 / 8 * Configuration.getScale(), 16 * 8 / 8 * Configuration.getScale(), 16 * 8 / 8 * Configuration.getScale());//backgraund
                }
                if (map[j][i] == 16) {
                    batch.draw(Res.tile(3, 0), (j - 2) * 16 * 8 / 8 * Configuration.getScale(), (i - 4) * 16 * 8 / 8 * Configuration.getScale(), 16 * 8 / 8 * Configuration.getScale(), 16 * 8 / 8 * Configuration.getScale());//backgraund
                }
                if (map[j][i] == 18) {
                    batch.draw(Res.tile(4, 1), (j - 2) * 16 * 8 / 8 * Configuration.getScale(), (i - 4) * 16 * 8 / 8 * Configuration.getScale(), 16 * 8 / 8 * Configuration.getScale(), 16 * 8 / 8 * Configuration.getScale());//backgraund
                }
            }
        }
        batch.draw(Res.nextlv, 6 * 16 * Configuration.getScale(), 0, 16 * Configuration.getScale(), 16 * Configuration.getScale());

    }

    private void button_draw() {
        if (b_Play) {
            batch.draw(Res.play_button_toched, 7 * 16 * 4 / 8 * Configuration.getScale() - 32 * 4 / 8 * Configuration.getScale(), 5 * 16 * 4 / 8 * Configuration.getScale() - 32 * 4 / 8 * Configuration.getScale() - 32 * 3 / 8 * Configuration.getScale(), 32 * 8 / 8 * Configuration.getScale(), 32 * 8 / 8 * Configuration.getScale());


            if (tick < 120) {
                b_Tutorial = false;
                b_Play = false;
                b_Settings = false;
                isExit = true;
                button = 2;
                m.setScreen(new PlayScreen(m, "levels/01.json", new Player(0, 0, null), false));
            } else {
                tick++;
            }

        } else {
            batch.draw(Res.play_button, 7 * 16 * 4 / 8 * Configuration.getScale() - 32 * 4 / 8 * Configuration.getScale(), 5 * 16 * 4 / 8 * Configuration.getScale() - 32 * 4 / 8 * Configuration.getScale() - 32 * 3 / 8 * Configuration.getScale(), 32 * 8 / 8 * Configuration.getScale(), 32 * 8 / 8 * Configuration.getScale());//play button
        }
        if (b_Tutorial) {
            batch.draw(Res.tutorial_button_toched, 14 * 8 / 8 * Configuration.getScale(), 5 * 16 * 4 / 8 * Configuration.getScale() - 21 * 4 / 8 * Configuration.getScale() - 32 * 3 / 8 * Configuration.getScale(), 21 * 8 / 8 * Configuration.getScale(), 21 * 8 / 8 * Configuration.getScale());
            if (tick < 120) {
                b_Tutorial = false;
                b_Play = false;
                b_Settings = false;
                isExit = true;
                button = 1;

            } else {
                tick++;
            }
        } else
            batch.draw(Res.tutorial_button, 14 * 8 / 8 * Configuration.getScale(), 5 * 16 * 4 / 8 * Configuration.getScale() - 21 * 4 / 8 * Configuration.getScale() - 32 * 3 / 8 * Configuration.getScale(), 21 * 8 / 8 * Configuration.getScale(), 21 * 8 / 8 * Configuration.getScale());//tutorial

        if (b_Settings) {
            batch.draw(Res.settings_button_toched, 7 * 16 * 8 / 8 * Configuration.getScale() - 21 * 8 / 8 * Configuration.getScale() - 14 * 8 / 8 * Configuration.getScale(), 5 * 16 * 4 / 8 * Configuration.getScale() - 21 * 4 / 8 * Configuration.getScale() - 32 * 3 / 8 * Configuration.getScale(), 21 * 8 / 8 * Configuration.getScale(), 21 * 8 / 8 * Configuration.getScale());
            if (tick < 120) {
                b_Tutorial = false;
                b_Play = false;
                b_Settings = false;
                isExit = true;
                button = 3;
            } else {
                tick++;
            }
        } else {
            batch.draw(Res.settings_button, 7 * 16 * 8 / 8 * Configuration.getScale() - 21 * 8 / 8 * Configuration.getScale() - 14 * 8 / 8 * Configuration.getScale(), 5 * 16 * 4 / 8 * Configuration.getScale() - 21 * 4 / 8 * Configuration.getScale() - 32 * 3 / 8 * Configuration.getScale(), 21 * 8 / 8 * Configuration.getScale(), 21 * 8 / 8 * Configuration.getScale());//settings
        }
    }

    private void logo_draw() {
        batch.draw(Res.logo, 12 * 16 / 8 * Configuration.getScale(), 3.85f * 16 * 8 / 8 * Configuration.getScale() - 16 * 8 / 2 / 8 * Configuration.getScale(), 408 * 1.25f / 8 * Configuration.getScale(), 125 * 1f / 8 * Configuration.getScale());
        batch.draw(Res.vk, 4 * Configuration.getScale(), 4 * Configuration.getScale(), 8 * Configuration.getScale(), 8 * Configuration.getScale());
    }

    public void sensor(int sx, int sy) {
        if (Gdx.input.justTouched()) {
            //System.out.println(Gdx.input.getX());
            //System.out.println(Gdx.graphics.getHeight()-Gdx.input.getY());
            //System.out.println(space);
            mX = Gdx.input.getX() - sx / 2;
            mY = Gdx.graphics.getHeight() - Gdx.input.getY() - sy / 2;

            if (mX > 4 * Configuration.getScale() && mX < 12 * Configuration.getScale()) {
                if (mY > 4 * Configuration.getScale() && mY < 12 * Configuration.getScale()) {
                    Gdx.net.openURI("https://vk.com/dargonzaig100");
                }
            }

            if (mX > 6 * 16 * Configuration.getScale() && mX < 7 * 16 * Configuration.getScale()) {
                if (mY > 0 && mY < 16 * Configuration.getScale()) {
                    Gdx.app.exit();
                }
            }
            if (mX > 0 && mX < 16 * Configuration.getScale()) {
                if (mY > 4 * 16 * Configuration.getScale() && mY < 5 * 16 * Configuration.getScale()) {
                    if ((Configuration.getScale() == 6.0f) && (Configuration.getMusic() == 10) && (Configuration.getSound() == 80)) {
                        m.setScreen(new Render3D(m));
                    }
                    if ((Configuration.getMusic() == 20) && (Configuration.getSound() == 50)) {
                        m.setScreen(new PlayScreen(m, "levels/Test.json", new Player(0, 0, null), false));
                    }
                }
            }
            if ((mY > 109 / 6 * Configuration.getScale()) && (mY < 239 / 6 * Configuration.getScale())) {
                if ((mX > 116 / 8 * Configuration.getScale()) && (mX < 276 / 8 * Configuration.getScale())) {
                    tick = 0;
                    b_Tutorial = true;
                    Res.click[random.nextInt(2)].play(Configuration.getSound());
                    //isPres =true;
                }
            }
            if ((mY > 75 / 6 * Configuration.getScale()) && (mY < 256 / 6 * Configuration.getScale())) {
                if ((mX < 576 / 8 * Configuration.getScale() * 1) && (mX > 320 / 8 * Configuration.getScale() * 1)) {
                    tick = 0;
                    b_Play = true;
                    Res.click[random.nextInt(2)].play(Configuration.getSound());
                    // isPres =true;
                }
            }
            if ((mY > 109 / 6 * Configuration.getScale() * 1) && (mY < 239 / 6 * Configuration.getScale() * 1)) {
                if ((mX > 616 / 8 * Configuration.getScale() * 1) && (mX < 789 / 8 * Configuration.getScale() * 1)) {
                    tick = 0;
                    b_Settings = true;
                    Res.click[random.nextInt(2)].play(Configuration.getSound());
                    // isPres =true;
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        fbo.dispose();
        fbo2.dispose();
        ry = height - scrH;
        rx = width - scrW;
        scrW = width;
        scrH = height;
        viewport.update(width, height);
        cam.translate(rx / 2, ry / 2, 0);
        cam.update();
        fbo = new FrameBuffer(Pixmap.Format.RGB888, width, height, false);
        fbo2 = new FrameBuffer(Pixmap.Format.RGB888, width, height, false);
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
    }
}
