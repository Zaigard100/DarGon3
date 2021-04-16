package com.zaig100.dg.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Calendar;

public class Res {

    static int num;

    static final Calendar c = Calendar.getInstance();
    static int month = c.get(Calendar.MONTH);
    static int day = c.get(Calendar.DAY_OF_MONTH);

    static public Texture ui = new Texture(Gdx.files.internal("texture/UI.png"));
    static public Texture tileset = new Texture(Gdx.files.internal("texture/TileSet.png"));
    static public Texture logo;

    static public TextureRegion play_button;
    static public TextureRegion play_button_toched;

    static public TextureRegion tutorial_button;
    static public TextureRegion tutorial_button_toched;

    static public TextureRegion settings_button;
    static public TextureRegion settings_button_toched;

    static public TextureRegion nextlv;
    static public Texture dark;

    static public Texture bag;
    static public Texture hp_potion;
    static public Texture sheld;
    static public Texture torch;

    static public Texture teleport;
    static public Texture damage;

    static public TextureRegion flamethrowfer;

    static public TextureRegion arrow;

    static public Texture vk;

    static Sprite hero;

    static public Texture amonghero;
    static public Texture firedhero;
    static public Texture shotedhero;

    static public Texture pause_dark;

    static public Texture joystick;
    static public Texture use;
    /*
        public Music mainmenu;
        public Music ingame;
        public Music settingmisc;
    */
    static public Sound[] click = new Sound[3];
    static public Sound wasted;
    static public Sound lov;

    static public Texture boards;

    static public Texture clvl2;
    static public Texture clvl1;
    static public Texture clvl0;

    static Sprite spr = null;

    static Sprite spr1;

    /**
     * Resource Loading
     */
    static public void sprL() {


        Res.play_button = new Sprite(ui, 0, 0, 32, 32);
        Res.play_button_toched = new Sprite(ui, 32, 0, 32, 32);

        Res.tutorial_button = new Sprite(ui, 0, 32, 21, 21);
        Res.tutorial_button_toched = new Sprite(ui, 21, 32, 21, 21);

        Res.settings_button = new Sprite(ui, 42, 32, 21, 21);
        Res.settings_button_toched = new Sprite(ui, 63, 32, 21, 21);

        Res.nextlv = new TextureRegion(new Texture(Gdx.files.internal("texture/nextlevel.png")));
        Res.logo = new Texture(Gdx.files.internal("texture/logo.png"));
        Res.dark = new Texture(Gdx.files.internal("texture/Dark.png"));

        Res.bag = new Texture(Gdx.files.internal("texture/bag.png"));
        Res.hp_potion = new Texture(Gdx.files.internal("texture/hp_potion.png"));
        Res.sheld = new Texture(Gdx.files.internal("texture/sheld.png"));
        Res.torch = new Texture(Gdx.files.internal("texture/torch_spritesheet.png"));

        Res.teleport = new Texture(Gdx.files.internal("texture/teleporter.png"));
        Res.damage = new Texture(Gdx.files.internal("texture/Damage.png"));
        Res.flamethrowfer = new TextureRegion(new Texture(Gdx.files.internal("texture/flamethrower.png")));
        Res.arrow = new TextureRegion(new Texture(Gdx.files.internal("texture/arrow.png")));

        Res.amonghero = new Texture(Gdx.files.internal("texture/amonghero.png"));
        Res.firedhero = new Texture(Gdx.files.internal("texture/firedhero.png"));
        Res.shotedhero = new Texture(Gdx.files.internal("texture/shotedhero.png"));

        Res.pause_dark = new Texture(Gdx.files.internal("texture/resume.png"));

        Res.joystick = new Texture(Gdx.files.internal("texture/Joystick.png"));
        Res.use = new Texture(Gdx.files.internal("texture/use.png"));

        Res.vk = new Texture(Gdx.files.internal("texture/vk.png"));

/*
        mainmenu = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainmenu.mp3"));
        ingame = Gdx.audio.newMusic(Gdx.files.internal("sounds/ingame.mp3"));
        settingmisc = Gdx.audio.newMusic(Gdx.files.internal("sounds/settings.mp3"));
*/
        Res.click[0] = Gdx.audio.newSound(Gdx.files.internal("sounds/click.mp3"));
        Res.click[1] = Gdx.audio.newSound(Gdx.files.internal("sounds/click1.mp3"));
        Res.click[2] = Gdx.audio.newSound(Gdx.files.internal("sounds/click2.mp3"));
        Res.wasted = Gdx.audio.newSound(Gdx.files.internal("sounds/wasted.mp3"));
        Res.lov = Gdx.audio.newSound(Gdx.files.internal("sounds/lov.mp3"));
        Res.boards = new Texture(Gdx.files.internal("texture/Dark.png"));

        Res.clvl2 = new Texture(Gdx.files.internal("texture/2_clvl.png"));
        Res.clvl1 = new Texture(Gdx.files.internal("texture/1_clvl.png"));
        Res.clvl0 = new Texture(Gdx.files.internal("texture/0_clvl.png"));

    }

    static public Sprite crossbow(boolean stage) {


        if (stage) {
            Res.num = 0;
        } else {
            Res.num = 1;
        }
        return new Sprite(new Texture(Gdx.files.internal("texture/crossbow.png")), 16 * num, 0, 16, 16);

    }


    static public Sprite fire(int stage) {
        Res.spr1 = new Sprite(new Texture(Gdx.files.internal("texture/Fire.png")), 16 * stage, 0, 16, 16);

        return Res.spr1;
    }


    static public Sprite HP(int count) {


        if (count <= 0) {
            Res.spr = null;
        }
        if (count == 1) {
            Res.spr = new Sprite(new Texture(Gdx.files.internal("texture/НP_1.png")), 0, 0, 16, 16);
        }
        if (count == 2) {
            Res.spr = new Sprite(new Texture(Gdx.files.internal("texture/НP_2.png")), 0, 0, 16, 16);
        }
        if (count == 3) {
            Res.spr = new Sprite(new Texture(Gdx.files.internal("texture/НP_3.png")), 0, 0, 16, 16);
        }
        if (count >= 4) {
            Res.spr = new Sprite(new Texture(Gdx.files.internal("texture/НP_4.png")), 0, 0, 16, 16);
        }

        return Res.spr;
    }

    static public Sprite hero(boolean flip, boolean walked, int stage) {
        if (!walked) {
            Res.hero = new Sprite(new Texture(Gdx.files.internal("texture/idle.png")), stage * 16, 0, 16, 16);
        } else {
            Res.hero = new Sprite(new Texture(Gdx.files.internal("texture/run.png")), stage * 16, 0, 16, 16);
        }
        if ((Res.month == 11 && Res.day == 31) || (Res.month == 0 && Res.day <= 7)) {
            Res.hero = new Sprite(new Texture(Gdx.files.internal("texture/NGSprite.png")), stage * 16, 0, 16, 16);
        }
        Res.hero.flip(flip, false);
        return Res.hero;
    }

    /**
     * @return Return tile
     */
    static public Sprite tile(int x, int y) {
        return new Sprite(Res.tileset, x * 16, y * 16, 16, 16);
    }


    static public void dispose() {
/*
        mainmenu.dispose();
        ingame.dispose();
        settingmisc.dispose();
*/
        Res.clvl0.dispose();
        Res.clvl1.dispose();
        Res.clvl2.dispose();
        Res.wasted.dispose();
        Res.click[0].dispose();
        Res.click[1].dispose();
        Res.click[2].dispose();
        Res.wasted.dispose();
        Res.lov.dispose();
        Res.ui.dispose();
        Res.tileset.dispose();
        Res.logo.dispose();
        Res.bag.dispose();
        Res.boards.dispose();
        Res.damage.dispose();
        Res.dark.dispose();
        Res.firedhero.dispose();
        Res.joystick.dispose();
        Res.pause_dark.dispose();
        Res.hp_potion.dispose();
        Res.teleport.dispose();
        Res.torch.dispose();
        Res.amonghero.dispose();
        Res.sheld.dispose();
        Res.shotedhero.dispose();
        Res.use.dispose();
    }
}
