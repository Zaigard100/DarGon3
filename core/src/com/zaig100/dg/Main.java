package com.zaig100.dg;

import com.badlogic.gdx.Game;
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
	public Main(boolean isAndroid ){
		Main.isAndroid = isAndroid;
	}
	
	@Override
	public void create () { res = new Res(); res.sprL();
		try {
			configuration = new Configuration(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
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
