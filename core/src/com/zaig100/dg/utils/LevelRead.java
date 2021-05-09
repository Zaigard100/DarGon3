package com.zaig100.dg.utils;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.zaig100.dg.utils.contain.CrossbowC;
import com.zaig100.dg.utils.contain.FlamefrowerC;
import com.zaig100.dg.utils.contain.FlimsyTileC;
import com.zaig100.dg.utils.contain.HideTrapC;
import com.zaig100.dg.utils.contain.ItemC;
import com.zaig100.dg.utils.contain.SpikeC;
import com.zaig100.dg.utils.contain.SpinneyC;
import com.zaig100.dg.utils.contain.StairC;
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
    JSONArray jsonArray;
    Iterator iter;
    Reader in;
    int[] map;
    int wight, height, SpawnX, SpawnY;
    String levelname;
    boolean isSave, isDark;
    boolean isHideTrap, isTeleport, isStair, isItem, isFlimsyTile, isFlamefrower, isCrossbow, isSpinney, isSpike;
    ArrayList<com.zaig100.dg.utils.contain.HideTrapC> hide_trap = new ArrayList<>();
    ArrayList<com.zaig100.dg.utils.contain.TeleportC> teleport = new ArrayList<>();
    ArrayList<com.zaig100.dg.utils.contain.StairC> stair = new ArrayList<>();
    ArrayList<com.zaig100.dg.utils.contain.ItemC> item = new ArrayList<>();
    ArrayList<com.zaig100.dg.utils.contain.FlimsyTileC> flimsy_tile = new ArrayList<>();
    ArrayList<com.zaig100.dg.utils.contain.FlamefrowerC> flamefrower = new ArrayList<>();
    ArrayList<com.zaig100.dg.utils.contain.CrossbowC> crosbow = new ArrayList<>();
    ArrayList<SpinneyC> spinney = new ArrayList<>();
    ArrayList<SpikeC> spike = new ArrayList<com.zaig100.dg.utils.contain.SpikeC>();

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

        levelname = (String) jsonObject.get("LevelName");
        SpawnX = ((Long) ((JSONArray) jsonObject.get("Spawn")).get(0)).intValue();
        SpawnY = ((Long) ((JSONArray) jsonObject.get("Spawn")).get(1)).intValue();

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

        isSave = (Boolean) jsonObject.get("save");

    }

    private void map_read() {
        jsonArray = (JSONArray) jsonObject.get("map");
        wight = ((JSONArray) jsonArray.get(0)).size();
        height = jsonArray.size();
        map = new int[wight * height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < wight; j++) {
                map[(height - i - 1) * wight + j] = ((Long) ((JSONArray) jsonArray.get(i)).get(j)).intValue();
            }
        }
    }

    private void hide_trap_read() {
        isHideTrap = jsonObject.get("HideTrap") != null;
        if (isHideTrap) {
            System.out.println("122");
            jsonArray = (JSONArray) jsonObject.get("HideTrap");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                JO = (JSONObject) iter.next();
                hide_trap.add(new com.zaig100.dg.utils.contain.HideTrapC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue()));

            }
        }
    }

    private void telepotr_read() {
        isTeleport = jsonObject.get("Teleport") != null;
        if (isTeleport) {
            jsonArray = (JSONArray) jsonObject.get("Teleport");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                JO = (JSONObject) iter.next();
                teleport.add(new com.zaig100.dg.utils.contain.TeleportC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Long) JO.get("TX")).intValue(), ((Long) JO.get("TY")).intValue()));
            }
        }
    }

    private void stair_read() {
        isStair = jsonObject.get("Stair") != null;
        if (isStair) {
            jsonArray = (JSONArray) jsonObject.get("Stair");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                JO = (JSONObject) iter.next();
                stair.add(new StairC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Boolean) JO.get("FlipX")), (String) JO.get("Next"), (Boolean) JO.get("End")));
            }
        }
    }

    private void item_read() {
        isItem = jsonObject.get("Item") != null;
        if (isItem) {
            jsonArray = (JSONArray) jsonObject.get("Item");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                JO = (JSONObject) iter.next();
                item.add(new com.zaig100.dg.utils.contain.ItemC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Long) JO.get("ID")).intValue()));
            }
        }
    }

    private void flimsy_tile_read() {
        isFlimsyTile = jsonObject.get("FlimsyTile") != null;
        if (isFlimsyTile) {
            jsonArray = (JSONArray) jsonObject.get("FlimsyTile");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                JO = (JSONObject) iter.next();
                flimsy_tile.add(new FlimsyTileC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Long) JO.get("Stage")).intValue()));
            }
        }
    }

    private void flamefrower_read() {
        isFlamefrower = jsonObject.get("Flamefrower") != null;
        if (isFlamefrower) {
            jsonArray = (JSONArray) jsonObject.get("Flamefrower");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                JO = (JSONObject) iter.next();
                flamefrower.add(new com.zaig100.dg.utils.contain.FlamefrowerC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Long) JO.get("Stage")).intValue(), ((Long) JO.get("Max")).intValue(), ((Long) JO.get("Rot")).intValue()));
            }
        }
    }

    private void crossbow_read() {
        isCrossbow = jsonObject.get("Crossbow") != null;
        if (isCrossbow) {
            jsonArray = (JSONArray) jsonObject.get("Crossbow");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                JO = (JSONObject) iter.next();
                crosbow.add(new com.zaig100.dg.utils.contain.CrossbowC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Long) JO.get("DX")).intValue(), ((Long) JO.get("DY")).intValue(), ((Long) JO.get("Angle")).intValue()));
            }
        }
    }

    private void spinney_read() {
        isSpinney = jsonObject.get("Spinney") != null;
        if (isSpinney) {
            jsonArray = (JSONArray) jsonObject.get("Spinney");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                JO = (JSONObject) iter.next();
                spinney.add(new com.zaig100.dg.utils.contain.SpinneyC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Long) JO.get("Widht")).intValue(), ((Long) JO.get("Height")).intValue()));
            }
        }
    }

    private void spike_read() {
        isSpike = jsonObject.get("Spike") != null;
        if (isSpinney) {
            jsonArray = (JSONArray) jsonObject.get("Spike");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                JO = (JSONObject) iter.next();
                spike.add(new com.zaig100.dg.utils.contain.SpikeC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), (Boolean) JO.get("Active")));
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

    public boolean isHideTrap() {
        return isHideTrap;
    }

    public boolean isTeleport() {
        return isTeleport;
    }

    public boolean isStair() {
        return isStair;
    }

    public boolean isItem() {
        return isItem;
    }

    public boolean isFlimsyTile() {
        return isFlimsyTile;
    }

    public boolean isFlamefrower() {
        return isFlamefrower;
    }

    public boolean isCrossbow() {
        return isCrossbow;
    }

    public boolean isSpinney() {
        return isSpinney;
    }

    public boolean isSpike() {
        return isSpike;
    }
}

