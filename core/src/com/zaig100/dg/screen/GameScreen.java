package com.zaig100.dg.screen;

import com.badlogic.gdx.Gdx;
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
import com.zaig100.dg.render.GameRenderer;
import com.zaig100.dg.utils.Res;

import java.io.IOException;

public class GameScreen implements Screen {
    FrameBuffer fbo,fbo2;
    SpriteBatch batch;
    OrthographicCamera cam;
    static Main m;
    Sprite frame;
    GameRenderer gRend;
    static int height;
    static int width;
    static int scrW,scrH;
    Viewport viewport;

    int ry,rx;

    public GameScreen(Main m) {
        GameScreen.m = m;
        batch = new SpriteBatch();
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
        try {
            gRend = new GameRenderer(m);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Main.getRes().sprL();
        //gRend.renderGameStart();



    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.justTouched()) {

        }

        fbo.begin();
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        gRend.renderMenu(batch, scrW, scrH);
        gRend.sensor(Main.getRes(), (int) (scrW - 16 * 7 * Main.getConfiguration().getScale()), (int) (scrH - 16 * 5 * Main.getConfiguration().getScale()));
        fbo.end();

        frame = new Sprite(fbo.getColorBufferTexture());


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (gRend.isExit()) {
            switch (gRend.getButton()) {
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
        System.out.println("123");
        batch.dispose();
        fbo.dispose();
        fbo2.dispose();
    }
}
