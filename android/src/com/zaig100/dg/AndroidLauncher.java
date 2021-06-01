package com.zaig100.dg;

import android.content.Context;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getContext();
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        Main m = new Main(true);
        Main.setSd(getExternalFilesDir("dargon"));

        initialize(m, config);
    }
}
