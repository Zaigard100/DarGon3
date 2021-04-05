package com.zaig100.dg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.screen.GameScreen;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

import java.io.File;
import java.io.IOException;

public class Main extends Game {
    static Res res;
    static boolean isAndroid;
    static File sd = null;
    static Configuration configuration;
    Texture tex;
    SpriteBatch batch;
    int wight, height;
    boolean first = true;

    public Main(boolean isAndroid) {
        Main.isAndroid = isAndroid;
    }

    @Override
    public void create() {
        res = new Res();
        wight = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        tex = new Texture(Gdx.files.internal("zaigard.jpg"));
        res.sprL();
        try {
            configuration = new Configuration(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Start");
    }

    @Override
    public void render() {
        if (first) {
            Gdx.gl.glClearColor(0.8705882f, 0.890196f, 0.8745098f, 0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
            batch.begin();
            batch.draw(tex, wight / 2 - 150, height / 2 - 150, 300, 300);
            batch.end();

            if (Gdx.input.justTouched()) {
                setScreen(new GameScreen(this));
                first = false;
            }
        }
        super.render();

    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        tex.dispose();
        res.dispose();
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static void setConfiguration(Configuration configuration) {
        Main.configuration = configuration;
    }

    public boolean isAndroid() {
        return isAndroid;
    }

    public static File getSd() {
        return sd;
    }

    public static void setSd(File sd) {
        Main.sd = sd;
    }

    public static Res getRes() {
		return res;
	}

	public static void setRes(Res res) {
		Main.res = res;
	}
}
