package com.zaig100.dg;

import android.os.Bundle;
import android.os.Environment;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.zaig100.dg.Main;
import com.zaig100.dg.utils.Configuration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		Main m = new Main(true);
		Main.setSd(Environment.getExternalStorageDirectory());

		initialize(m, config);
	}
}
