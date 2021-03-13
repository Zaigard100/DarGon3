package com.zaig100.dg.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Calendar;

public class  Res {

    int num;

    final Calendar c = Calendar.getInstance();
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    public Texture ui = new Texture(Gdx.files.internal("texture/UI.png"));
    public Texture tileset = new Texture(Gdx.files.internal("texture/TileSet.png"));
    public Texture logo;

    public Sprite play_button;
    public Sprite play_button_toched;

    public Sprite tutorial_button;
    public Sprite tutorial_button_toched;

    public Sprite settings_button;
    public Sprite settings_button_toched;

    public Sprite nextlv;
    public Sprite dark;

    public Sprite bag;
    public Sprite hp_potion;
    public Sprite sheld;
    public Sprite torch;

    public Sprite teleport;
    public Sprite damage;

    public Sprite flamethrowfer;

    public Sprite arrow;

    Sprite hero;
    Sprite tile;

    public Sprite amonghero;
    public Sprite firedhero;
    public Sprite shotedhero;

    public Sprite pause_dark;

    public Sprite joystick;
    public Sprite use;
/*
    public Music mainmenu;
    public Music ingame;
    public Music settingmisc;
*/
    public Sound[] click = new Sound[3];
    public Sound wasted;
    public Sound lov;

    public Sprite boards;

    Sprite spr = null;

    Sprite spr1;

    /**
     * Resource Loading
     */
    public void sprL(){


        play_button = new Sprite(ui,0,0,32,32);
        play_button_toched = new Sprite(ui,32,0,32,32);

        tutorial_button = new Sprite(ui,0,32,21,21);
        tutorial_button_toched = new Sprite(ui,21,32,21,21);

        settings_button = new Sprite(ui,42,32,21,21);
        settings_button_toched = new Sprite(ui,63,32,21,21);

        nextlv = new Sprite(new Texture(Gdx.files.internal("texture/nextlevel.png")),0,0,16,16);
        logo =  new Texture(Gdx.files.internal("texture/logo.png"));
        dark = new Sprite(new Texture(Gdx.files.internal("texture/Dark.png")),0,0,240,176);

        bag = new Sprite(new Texture(Gdx.files.internal("texture/bag.png")),0,0,16,16);
        hp_potion =new Sprite(new Texture(Gdx.files.internal("texture/hp_potion.png")));
        sheld =new Sprite(new Texture(Gdx.files.internal("texture/sheld.png")));
        torch =new Sprite(new Texture(Gdx.files.internal("texture/torch_spritesheet.png")),0,0,16,16);

        teleport = new Sprite(new Texture(Gdx.files.internal("texture/teleporter.png")),0,0,16,16);
        damage = new Sprite(new Texture(Gdx.files.internal("texture/Damage.png")),0,0,112,80);
        flamethrowfer = new Sprite(new Texture(Gdx.files.internal("texture/flamethrower.png")),0,0,16,16);
        arrow = new Sprite(new Texture((Gdx.files.internal("texture/arrow.png"))));

        amonghero = new Sprite(new Texture((Gdx.files.internal("texture/amonghero.png"))));
        firedhero = new Sprite(new Texture((Gdx.files.internal("texture/firedhero.png"))));
        shotedhero = new Sprite(new Texture((Gdx.files.internal("texture/shotedhero.png"))));

        pause_dark = new Sprite(new Texture(Gdx.files.internal("texture/resume.png")));

        joystick = new Sprite(new Texture(Gdx.files.internal("texture/Joystick.png")));
        use = new Sprite(new Texture(Gdx.files.internal("texture/use.png")));
/*
        mainmenu = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainmenu.mp3"));
        ingame = Gdx.audio.newMusic(Gdx.files.internal("sounds/ingame.mp3"));
        settingmisc = Gdx.audio.newMusic(Gdx.files.internal("sounds/settings.mp3"));
*/
        click[0] = Gdx.audio.newSound(Gdx.files.internal("sounds/click.mp3"));
        click[1] = Gdx.audio.newSound(Gdx.files.internal("sounds/click1.mp3"));
        click[2] = Gdx.audio.newSound(Gdx.files.internal("sounds/click2.mp3"));
        wasted = Gdx.audio.newSound(Gdx.files.internal("sounds/wasted.mp3"));
        lov = Gdx.audio.newSound(Gdx.files.internal("sounds/lov.mp3"));
        boards = new Sprite(new Texture(Gdx.files.internal("texture/Dark.png")),0,0,16,16);


    }

    public Sprite crossbow(boolean stage){


        if(stage){
            num =0;
        }else{
            num = 1;
        }
        return new Sprite(new Texture(Gdx.files.internal("texture/crossbow.png")),16*num,0,16,16);

    }


    public Sprite fire(int stage){
         spr1 =  new Sprite(new Texture(Gdx.files.internal("texture/Fire.png")),16*stage,0,16,16);

        return spr1;
    }



    public Sprite HP(int count){


        if(count <= 0){
            spr = null;
        }
        if(count == 1){
            spr = new Sprite(new Texture(Gdx.files.internal("texture/НP_1.png")),0,0,16,16);
        }
        if(count == 2){
            spr = new Sprite(new Texture(Gdx.files.internal("texture/НP_2.png")),0,0,16,16);
        }
        if(count == 3){
            spr = new Sprite(new Texture(Gdx.files.internal("texture/НP_3.png")),0,0,16,16);
        }
        if(count >= 4){
            spr = new Sprite(new Texture(Gdx.files.internal("texture/НP_4.png")),0,0,16,16);
        }

        return spr;
    }

    public  Sprite hero(boolean flip,boolean walked,int stage){
        if(!walked) {
            hero = new Sprite(new Texture(Gdx.files.internal("texture/idle.png")), stage * 16, 0, 16, 16);
        }else {
            hero = new Sprite(new Texture(Gdx.files.internal("texture/run.png")), stage * 16, 0, 16, 16);
        }
        if((month==11&&day==31)||(month==0&&day<=7)){
            hero =new Sprite(new Texture(Gdx.files.internal("texture/NGSprite.png")), stage * 16, 0, 16, 16);
        }
        hero.flip(flip,false);
        return hero;
    }

    /**
     * @return Return tile
     */
    public Sprite tile(int x,int y){
        tile = new Sprite(tileset,x*16,y*16,16,16);
        return tile;
    }


    public void dispose() {
/*
        mainmenu.dispose();
        ingame.dispose();
        settingmisc.dispose();
*/
        wasted.dispose();
        click[0].dispose();
        click[1].dispose();
        click[2].dispose();
        wasted.dispose();
        lov.dispose();
        ui.dispose();
        tileset.dispose();
        logo.dispose();
    }
}
