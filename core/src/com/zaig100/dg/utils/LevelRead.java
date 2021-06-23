package com.zaig100.dg.utils;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.zaig100.dg.screen.GameScreen;
import com.zaig100.dg.screen.PlayScreen;
import com.zaig100.dg.utils.contain.ButtonС;
import com.zaig100.dg.utils.contain.CrossbowC;
import com.zaig100.dg.utils.contain.DoorC;
import com.zaig100.dg.utils.contain.FlamefrowerC;
import com.zaig100.dg.utils.contain.FlimsyTileC;
import com.zaig100.dg.utils.contain.HideTrapC;
import com.zaig100.dg.utils.contain.ItemC;
import com.zaig100.dg.utils.contain.SpikeC;
import com.zaig100.dg.utils.contain.SpinneyC;
import com.zaig100.dg.utils.contain.StairC;
import com.zaig100.dg.utils.contain.TabletC;
import com.zaig100.dg.utils.contain.TeleportC;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

public class LevelRead {

    JSONParser jsonParser = new JSONParser();
    JSONObject jsonObject, JO;
    JSONArray jsonArray, JA;
    Iterator iter, Ir;
    Reader in;
    int[] map;
    boolean[][] place = new boolean[3][3];
    String[] func, text;
    int wight, height, SpawnX, SpawnY, i;
    String levelname;
    boolean isSave, isDark;
    ArrayList<HideTrapC> hide_trap = new ArrayList<>();
    ArrayList<TeleportC> teleport = new ArrayList<>();
    ArrayList<StairC> stair = new ArrayList<>();
    ArrayList<ItemC> item = new ArrayList<>();
    ArrayList<FlimsyTileC> flimsy_tile = new ArrayList<>();
    ArrayList<FlamefrowerC> flamefrower = new ArrayList<>();
    ArrayList<CrossbowC> crosbow = new ArrayList<>();
    ArrayList<SpinneyC> spinney = new ArrayList<>();
    ArrayList<SpikeC> spike = new ArrayList<>();
    ArrayList<ButtonС> button = new ArrayList<>();
    ArrayList<DoorC> door = new ArrayList<>();
    ArrayList<TabletC> tablet = new ArrayList<>();

    public LevelRead(String path, boolean isPack) {
        if (isPack) {
            in = new InputStreamReader(Gdx.files.getFileHandle(path, Files.FileType.Absolute).read());
        } else {
            in = new InputStreamReader(Gdx.files.internal(path).read());
        }

        try {
            jsonObject = (JSONObject) jsonParser.parse(in);
            in.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        try {
            levelname = (String) jsonObject.get("LevelName");
        } catch (NullPointerException npe) {
            System.out.println("LevelName invalid");
            PlayScreen.m.setScreen(new GameScreen(PlayScreen.m));
        }

        try {
            SpawnX = ((Long) ((JSONArray) jsonObject.get("Spawn")).get(0)).intValue();
            SpawnY = ((Long) ((JSONArray) jsonObject.get("Spawn")).get(1)).intValue();
        } catch (NullPointerException npe) {
            System.out.println("Spawn invalid in map " + levelname);
            PlayScreen.m.setScreen(new GameScreen(PlayScreen.m));
        }

        map_read();
        hide_trap_read();
        telepotr_read();
        stair_read();
        item_read();
        flimsy_tile_read();
        flamefrower_read();
        crossbow_read();
        spinney_read();
        spike_read();
        button_read();
        door_read();
        tablet_read();
        if (jsonObject.get("ReMap") != null) {
            if ((Boolean) jsonObject.get("ReMap")) {
                map_correct();
            }
        }
        if (jsonObject.get("save") != null) {
            isSave = (Boolean) jsonObject.get("save");
        } else {
            isSave = false;
        }
    }

    private void map_read() {
        try {
            jsonArray = (JSONArray) jsonObject.get("map");
            wight = ((JSONArray) jsonArray.get(0)).size();
            height = jsonArray.size();
            map = new int[wight * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < wight; j++) {
                    map[(height - i - 1) * wight + j] = ((Long) ((JSONArray) jsonArray.get(i)).get(j)).intValue();
                }
            }
        } catch (NullPointerException npe) {
            System.out.println("Map invalid in level " + levelname);
            PlayScreen.m.setScreen(new GameScreen(PlayScreen.m));
        }
    }

    private void hide_trap_read() {
        if (jsonObject.get("HideTrap") != null) {
            i = 0;
            jsonArray = (JSONArray) jsonObject.get("HideTrap");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                try {
                    JO = (JSONObject) iter.next();
                    if ((String) JO.get("Tag") != null) {
                        hide_trap.add(new com.zaig100.dg.utils.contain.HideTrapC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), (Boolean) JO.get("Active"), (String) JO.get("Tag")));
                    } else {
                        hide_trap.add(new com.zaig100.dg.utils.contain.HideTrapC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), (Boolean) JO.get("Active"), "HT" + i));
                    }
                    i++;
                } catch (NullPointerException npe) {
                    System.out.println("HideTrap invalid in array " + i + " in map " + levelname);
                    PlayScreen.m.setScreen(new GameScreen(PlayScreen.m));
                }
            }
        }
    }

    private void telepotr_read() {
        if (jsonObject.get("Teleport") != null) {
            i = 0;
            jsonArray = (JSONArray) jsonObject.get("Teleport");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                try {
                    JO = (JSONObject) iter.next();
                    if ((String) JO.get("Tag") != null) {
                        teleport.add(new com.zaig100.dg.utils.contain.TeleportC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Long) JO.get("TX")).intValue(), ((Long) JO.get("TY")).intValue(), (String) JO.get("Tag")));
                    } else {
                        teleport.add(new com.zaig100.dg.utils.contain.TeleportC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Long) JO.get("TX")).intValue(), ((Long) JO.get("TY")).intValue(), "Tp" + i));
                    }
                    i++;
                } catch (NullPointerException npe) {
                    System.out.println("Teleport invalid in array " + i + " in map " + levelname);
                    PlayScreen.m.setScreen(new GameScreen(PlayScreen.m));
                }
            }
        }
    }

    private void stair_read() {
        if (jsonObject.get("Stair") != null) {
            i = 0;
            jsonArray = (JSONArray) jsonObject.get("Stair");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                try {
                    JO = (JSONObject) iter.next();
                    if ((String) JO.get("Tag") != null) {
                        stair.add(new StairC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Boolean) JO.get("FlipX")), (String) JO.get("Next"), (Boolean) JO.get("End"), (String) JO.get("Tag")));
                    } else {
                        stair.add(new StairC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Boolean) JO.get("FlipX")), (String) JO.get("Next"), (Boolean) JO.get("End"), "St" + i));
                    }
                    i++;
                } catch (NullPointerException npe) {
                    System.out.println("Stair invalid in array " + i + " in map " + levelname);
                    PlayScreen.m.setScreen(new GameScreen(PlayScreen.m));
                }
            }
        }
    }

    private void item_read() {
        if (jsonObject.get("Item") != null) {
            i = 0;
            jsonArray = (JSONArray) jsonObject.get("Item");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                try {
                    JO = (JSONObject) iter.next();
                    if ((String) JO.get("Tag") != null) {
                        item.add(new com.zaig100.dg.utils.contain.ItemC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), (String) JO.get("ID"), (Boolean) JO.get("Active"), (String) JO.get("Tag")));
                    } else {
                        item.add(new com.zaig100.dg.utils.contain.ItemC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), (String) JO.get("ID"), (Boolean) JO.get("Active"), "I" + i));
                    }
                    i++;
                } catch (NullPointerException npe) {
                    System.out.println("Item invalid in array " + i + " in map " + levelname);
                    PlayScreen.m.setScreen(new GameScreen(PlayScreen.m));
                }
            }
        }
    }

    private void flimsy_tile_read() {
        if (jsonObject.get("FlimsyTile") != null) {
            i = 0;
            jsonArray = (JSONArray) jsonObject.get("FlimsyTile");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                try {
                    JO = (JSONObject) iter.next();
                    if ((String) JO.get("Tag") != null) {
                        flimsy_tile.add(new FlimsyTileC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Long) JO.get("Stage")).intValue(), (String) JO.get("Tag")));
                    } else {
                        flimsy_tile.add(new FlimsyTileC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Long) JO.get("Stage")).intValue(), "FT" + i));
                    }
                    if (JO.get("Tick") != null)
                        flimsy_tile.get(flimsy_tile.size() - 1).setTick_sec((Double) JO.get("Tick"));
                    i++;
                } catch (NullPointerException npe) {
                    System.out.println("FlimsyTile invalid in array " + i + " in map " + levelname);
                    PlayScreen.m.setScreen(new GameScreen(PlayScreen.m));
                }
            }
        }
    }

    private void flamefrower_read() {
        if (jsonObject.get("Flamefrower") != null) {
            i = 0;
            jsonArray = (JSONArray) jsonObject.get("Flamefrower");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                try {
                    JO = (JSONObject) iter.next();
                    if ((String) JO.get("Tag") != null) {
                        flamefrower.add(new com.zaig100.dg.utils.contain.FlamefrowerC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Long) JO.get("Stage")).intValue(), ((Long) JO.get("Max")).intValue(), ((Long) JO.get("Rot")).intValue(), (String) JO.get("Tag")));
                    } else {
                        flamefrower.add(new com.zaig100.dg.utils.contain.FlamefrowerC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Long) JO.get("Stage")).intValue(), ((Long) JO.get("Max")).intValue(), ((Long) JO.get("Rot")).intValue(), "Ff" + i));
                    }
                    if (JO.get("Tick") != null)
                        flamefrower.get(flamefrower.size() - 1).setTick_sec((Double) JO.get("Tick"));
                    i++;

                } catch (NullPointerException npe) {
                    System.out.println("Flamefrower invalid in array " + i + " in map " + levelname);
                    PlayScreen.m.setScreen(new GameScreen(PlayScreen.m));
                }
            }
        }
    }

    private void crossbow_read() {
        if (jsonObject.get("Crossbow") != null) {
            i = 0;
            jsonArray = (JSONArray) jsonObject.get("Crossbow");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                try {
                    JO = (JSONObject) iter.next();
                    if ((String) JO.get("Tag") != null) {
                        crosbow.add(new com.zaig100.dg.utils.contain.CrossbowC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Long) JO.get("DX")).intValue(), ((Long) JO.get("DY")).intValue(), ((Long) JO.get("Angle")).intValue(), (String) JO.get("Tag")));
                    } else {
                        crosbow.add(new com.zaig100.dg.utils.contain.CrossbowC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Long) JO.get("DX")).intValue(), ((Long) JO.get("DY")).intValue(), ((Long) JO.get("Angle")).intValue(), "Cb" + i));
                    }
                    if (JO.get("Tick") != null)
                        crosbow.get(crosbow.size() - 1).setTick_sec((double) ((Long) JO.get("Tick")));
                    i++;

                } catch (NullPointerException npe) {
                    System.out.println("Crossbow invalid in array " + i + " in map " + levelname);
                    PlayScreen.m.setScreen(new GameScreen(PlayScreen.m));
                }
            }
        }
    }

    private void spinney_read() {
        if (jsonObject.get("Spinney") != null) {
            i = 0;
            jsonArray = (JSONArray) jsonObject.get("Spinney");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                try {
                    JO = (JSONObject) iter.next();
                    if ((String) JO.get("Tag") != null) {
                        spinney.add(new com.zaig100.dg.utils.contain.SpinneyC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Long) JO.get("Widht")).intValue(), ((Long) JO.get("Height")).intValue(), (String) JO.get("Tag")));
                    } else {
                        spinney.add(new com.zaig100.dg.utils.contain.SpinneyC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Long) JO.get("Widht")).intValue(), ((Long) JO.get("Height")).intValue(), "Sn" + i));
                    }
                    i++;
                } catch (NullPointerException npe) {
                    System.out.println("Spinney invalid in array " + i + " in map " + levelname);
                    PlayScreen.m.setScreen(new GameScreen(PlayScreen.m));
                }
            }
        }
    }

    private void spike_read() {
        if (jsonObject.get("Spike") != null) {
            i = 0;
            jsonArray = (JSONArray) jsonObject.get("Spike");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                try {
                    JO = (JSONObject) iter.next();
                    if ((String) JO.get("Tag") != null) {
                        spike.add(new com.zaig100.dg.utils.contain.SpikeC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), (Boolean) JO.get("Active"), (String) JO.get("Tag")));
                    } else {
                        spike.add(new com.zaig100.dg.utils.contain.SpikeC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), (Boolean) JO.get("Active"), "Sk" + i));
                    }
                    if (JO.get("Tick") != null)
                        spike.get(spike.size() - 1).setTick_sec((Double) JO.get("Tick"));
                    i++;
                } catch (NullPointerException npe) {
                    System.out.println("Spike invalid in array " + i + " in map " + levelname);
                    PlayScreen.m.setScreen(new GameScreen(PlayScreen.m));
                }
            }
        }
    }

    private void button_read() {
        if (jsonObject.get("Button") != null) {
            i = 0;
            jsonArray = (JSONArray) jsonObject.get("Button");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                try {
                    JO = (JSONObject) iter.next();
                    JA = (JSONArray) JO.get("Function");
                    func = new String[JA.size()];
                    for (int j = 0; j < func.length; j++) {
                        func[j] = (String) JA.get(j);
                    }
                    if ((String) JO.get("Tag") != null) {
                        button.add(new com.zaig100.dg.utils.contain.ButtonС(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), func, (String) JO.get("Tag")));
                    } else {
                        button.add(new com.zaig100.dg.utils.contain.ButtonС(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), func, "Bt" + i));
                    }
                    i++;
                } catch (NullPointerException npe) {
                    System.out.println("Button invalid in array " + i + " in map " + levelname);
                    PlayScreen.m.setScreen(new GameScreen(PlayScreen.m));
                }
            }
        }
    }

    private void tablet_read() {
        if (jsonObject.get("Tablet") != null) {
            i = 0;
            jsonArray = (JSONArray) jsonObject.get("Tablet");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                try {
                    JO = (JSONObject) iter.next();
                    JA = (JSONArray) JO.get("Text");
                    text = new String[JA.size()];
                    for (int j = 0; j < text.length; j++) {
                        text[j] = (String) JA.get(j);
                    }
                    if ((String) JO.get("Tag") != null) {
                        tablet.add(new com.zaig100.dg.utils.contain.TabletC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), text, (String) JO.get("Tag")));
                    } else {
                        tablet.add(new com.zaig100.dg.utils.contain.TabletC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), text, "Tb" + i));
                    }
                    i++;
                } catch (NullPointerException npe) {
                    System.out.println("Tablet invalid in array " + i + " in map " + levelname);
                    PlayScreen.m.setScreen(new GameScreen(PlayScreen.m));
                }
            }
        }
    }

    private void door_read() {
        if (jsonObject.get("Door") != null) {
            i = 0;
            jsonArray = (JSONArray) jsonObject.get("Door");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                try {
                    JO = (JSONObject) iter.next();
                    if ((String) JO.get("Tag") != null) {
                        door.add(new com.zaig100.dg.utils.contain.DoorC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), (String) JO.get("KeyTag"), (Boolean) JO.get("IsOpen"), ((Long) JO.get("Facing")).intValue(), (String) JO.get("Tag")));
                    } else {
                        door.add(new com.zaig100.dg.utils.contain.DoorC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), (String) JO.get("KeyTag"), (Boolean) JO.get("IsOpen"), ((Long) JO.get("Facing")).intValue(), "Bt" + i));
                    }
                    i++;
                } catch (NullPointerException npe) {
                    System.out.println("Door invalid in array " + i + " in map " + levelname);
                    PlayScreen.m.setScreen(new GameScreen(PlayScreen.m));
                }
            }
        }
    }

    private void map_correct() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < wight; j++) {
                for (int i1 = 0; i1 <= 2; i1++) {
                    for (int j1 = 0; j1 <= 2; j1++) {
                        place[i1][j1] = false;
                    }
                }
                try {
                    place[0][0] = map[(height - (i - 1) - 1) * wight + (j - 1)] == 11;
                } catch (ArrayIndexOutOfBoundsException e) {
                    place[0][0] = false;
                }
                try {
                    place[0][1] = map[(height - i - 1) * wight + (j - 1)] == 11;
                } catch (ArrayIndexOutOfBoundsException e) {
                    place[0][1] = false;
                }
                try {
                    place[0][2] = map[(height - (i + 1) - 1) * wight + (j - 1)] == 11;
                } catch (ArrayIndexOutOfBoundsException e) {
                    place[0][2] = false;
                }

                try {
                    place[1][0] = map[(height - (i - 1) - 1) * wight + j] == 11;
                } catch (ArrayIndexOutOfBoundsException e) {
                    place[1][0] = false;
                }
                try {
                    place[1][1] = map[(height - i - 1) * wight + j] == 11;
                } catch (ArrayIndexOutOfBoundsException e) {
                    place[1][1] = false;
                }
                try {
                    place[1][2] = map[(height - (i + 1) - 1) * wight + j] == 11;
                } catch (ArrayIndexOutOfBoundsException e) {
                    place[1][2] = false;
                }

                try {
                    place[2][0] = map[(height - (i - 1) - 1) * wight + (j + 1)] == 11;
                } catch (ArrayIndexOutOfBoundsException e) {
                    place[2][0] = false;
                }
                try {
                    place[2][1] = map[(height - i - 1) * wight + (j + 1)] == 11;
                } catch (ArrayIndexOutOfBoundsException e) {
                    place[2][1] = false;
                }
                try {
                    place[2][2] = map[(height - (i + 1) - 1) * wight + (j + 1)] == 11;
                } catch (ArrayIndexOutOfBoundsException e) {
                    place[2][2] = false;
                }

                if (!place[1][1]) {
                    if (place[1][0] && place[2][1]) {
                        map[(height - i - 1) * wight + j] = 13;
                    }
                    if (place[1][2] && place[2][1]) {
                        map[(height - i - 1) * wight + j] = 15;
                    }
                    if (place[0][1] && place[1][0]) {
                        map[(height - i - 1) * wight + j] = 14;
                    }
                    if (place[0][1] && place[1][2]) {
                        map[(height - i - 1) * wight + j] = 12;
                    }

                    if (place[0][1] && place[1][0] && place[2][1]) {
                        map[(height - i - 1) * wight + j] = 16;
                    }
                    if (place[0][1] && place[1][0] && place[1][2]) {
                        map[(height - i - 1) * wight + j] = 19;
                    }
                    if (place[0][1] && place[1][2] && place[2][1]) {
                        map[(height - i - 1) * wight + j] = 17;
                    }

                    if (place[1][0] && place[1][2] && place[2][1]) {
                        map[(height - i - 1) * wight + j] = 18;
                    }
                }
            }
        }
    }

    public int[] getMap() {
        return map;
    }

    public int getWight() {
        return wight;
    }

    public int getHeight() {
        return height;
    }

    public int getSpawnX() {
        return SpawnX;
    }

    public int getSpawnY() {
        return SpawnY;
    }

    public String getLevelname() {
        return levelname;
    }

    public ArrayList<DoorC> getDoor() {
        return door;
    }

    public ArrayList<TabletC> getTablet() {
        return tablet;
    }

    public boolean isSave() {
        return isSave;
    }

    public boolean isDark() {
        return isDark;
    }

    public ArrayList<HideTrapC> getHide_trap() {
        return hide_trap;
    }

    public ArrayList<TeleportC> getTeleport() {
        return teleport;
    }

    public ArrayList<StairC> getStair() {
        return stair;
    }

    public ArrayList<ItemC> getItem() {
        return item;
    }

    public ArrayList<FlimsyTileC> getFlimsy_tile() {
        return flimsy_tile;
    }

    public ArrayList<FlamefrowerC> getFlamefrower() {
        return flamefrower;
    }

    public ArrayList<CrossbowC> getCrosbow() {
        return crosbow;
    }

    public ArrayList<SpinneyC> getSpinney() {
        return spinney;
    }

    public ArrayList<SpikeC> getSpike() {
        return spike;
    }

    public ArrayList<ButtonС> getButton() {
        return button;
    }
}

