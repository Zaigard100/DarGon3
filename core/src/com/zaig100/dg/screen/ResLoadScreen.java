package com.zaig100.dg.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.zaig100.dg.Main;
import com.zaig100.dg.utils.Res;

public class ResLoadScreen implements Screen {

    Main m;
    int wight, height;
    SpriteBatch batch;
    BitmapFont font;
    Texture zaigard, progresbar;
    GlyphLayout load_layout, press_layout;
    String progres_msg;
    float tick_timer = 0;
    float color_a = 1;
    byte color_dr = -1;

    public ResLoadScreen(Main main) {
        m = main;
    }

    @Override
    public void show() {
        wight = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        batch = new SpriteBatch();

        font = new BitmapFont();
        font.setColor(0f, 0f, 0f, 0.5f);
        load_layout = new GlyphLayout();
        press_layout = new GlyphLayout();
        zaigard = new Texture("zaigard.jpg");
        progresbar = new Texture("texture/resume.png");
        load_layout.setText(font, "Load resurse: " + String.valueOf(0) + "%", Color.BLACK, Gdx.graphics.getWidth(), Align.center, true);
        press_layout.setText(font, "Press to screen", Color.BLACK, Gdx.graphics.getWidth(), Align.center, true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8705882f, 0.890196f, 0.8745098f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        if (tick_timer > 0.15f) {
            Res.sprL();
            load_layout.setText(font, "Load resurse: " + String.valueOf((float) ((float) Res.i / 16 * 100)) + "%", Color.BLACK, Gdx.graphics.getWidth(), Align.center, true);
            tick_timer = 0;
        }
        batch.begin();
        batch.draw(zaigard, 3 * wight / 8, height / 2 - height / 8, wight / 4, wight / 4);
        batch.draw(progresbar, 3 * wight / 8, height / 2 - height / 6, wight / 4 * (float) ((float) Res.i / 16), wight / 128);
        font.draw(batch, load_layout, 0, height / 2 - height / 4);
        if (Res.end) {
            loadEnd(0.03125f);
        }
        batch.end();

        tick_timer += Gdx.graphics.getDeltaTime();
    }

    private void loadEnd(float speed) {

        if (color_a <= 0) {
            color_dr = 1;
        } else if (color_a >= 1) {
            color_dr = -1;
        }

        color_a += color_dr * speed;

        press_layout.setText(font, "Press to screen", new Color(0, 0, 0, color_a), Gdx.graphics.getWidth(), Align.center, true);
        font.draw(batch, press_layout, 0, height / 2 - height / 3);

        if (Gdx.input.justTouched()) {
            m.setScreen(new GameScreen(m));
        }

    }

    @Override
    public void resize(int width, int height) {

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

    }
}
