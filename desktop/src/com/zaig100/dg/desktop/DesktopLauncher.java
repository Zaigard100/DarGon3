package com.zaig100.dg.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zaig100.dg.Main;
import com.zaig100.dg.utils.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

public class DesktopLauncher {
	static String[] spl = new String[] {

			"First","Lapenko Forever", "Subscribe to the grup in VK","USSR","Russia",
			"You know","DOOOOOOOOOGY","OPG 'Iron Sleeves' ","Diamonds aren't the best","Mamix",
			"FUGA TV","5opka","We are on YT","Hi","X% bugs","Написано на русском",
			"Written in English","Albert the cat","2021","DAZ","AZD","ZAD","ADZ","Without ads.",
			"I advertise myself","No lag, just frizes","Made by Zaigard100","Netzus is here...", "Are you deluded?",
			"Also try Prelude of the Chambered","Java Edition","Stop how to stop writing splashes?",
			"V^V^V^V^V^V^V^V^V^V^V^V^V^V^V^V^V^V","Welcome to the dungeon DarGon",
			"Another splash","Very Well!!!!","And you are subscribed to groups?","They are already here...",
			"GloblfrudjakfaefEWF","System.out.println('Hello World!!');","frame = new Sprite(fram.getColorBufferTexture());",
			"Remember 0.1beta1","Pay attention to 1 and 14","Also try Portallers 2D","Validol","Now only on PC","Open the chest"
			,"Give money for food","Perhaps the original","Braid in 4K","Last"

	};
	static String[] icon = new String[]{"texture/icon.png","texture/amonghero.png","texture/firedhero.png","texture/shotedhero.png"};
	public static void main (String[] arg) throws IOException {
		Date d = new Date();
		Random r = new Random();
		Main m = new Main(false);

		Configuration setting = new Configuration(m);

		///System.out.println(d.getDay());
		String title = "DarGon: " + spl[r.nextInt(spl.length)];
		if((d.getDay()==4)&&(d.getMonth()==5)) {
			title = "DarGon: " + "Today may be the developer's birthday";
		}

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = title ;
		config.width = (int) (7 *16*setting.getScale());
		config.height = (int) (5 *16*setting.getScale());
		config.resizable = false;
		config.addIcon(icon[r.nextInt(icon.length)], Files.FileType.Internal);
		new LwjglApplication(m, config);
	}
}
