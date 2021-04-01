package com.zaig100.dg.utils;


import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;


/**
 *
 */
public class LevelReader {

    JSONParser jsonParser = new JSONParser();
    JSONObject jsonObject = null;
    JSONArray jsonArray;
    JSONArray jsonInArray = null;
    Iterator iter;
    Iterator<JSONArray> iterArr;

    String levelName;
    Long widht, height;

    Long[] Spawn = new Long[2];
    boolean spawnFlipX,spawnFlipY;

    Long[] Win = new Long[2];

    Long hide_traps_count = Long.valueOf(0);
    int[][] hide_traps;

    Long teleport_count = Long.valueOf(0);
    int[][] teleport;

    Long flamethrower_count = Long.valueOf(0);
    int[][] flamethrowfer;

    Long crossbow_count = Long.valueOf(0);
    int[][] crossbow;

    Long items_count = Long.valueOf(0);
    int[][] items;

    Long flimsy_tile_count = Long.valueOf(0);
    int[][] flimsy_tile;

    //Long stairs_count;
    //int[][] stairs;
    //String[] stairs_next_path;

    boolean end;
    String nextpath;


    int[][] map;
    Long num;

    boolean isSave;
    Reader in;
    public LevelReader(String path,boolean isPack) throws IOException {
        if(!isPack) {
            in = new InputStreamReader(Gdx.files.internal(path).read());
        }else {
            in = new InputStreamReader(Gdx.files.getFileHandle(path, Files.FileType.Absolute).read());
        }
        try {
            jsonObject = (JSONObject) jsonParser.parse(in);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        in.close();
        if (jsonObject == null) throw new AssertionError();
        levelName = (String) jsonObject.get("LevelName");
        widht = (Long) jsonObject.get("Widht");
        height = (Long) jsonObject.get("Height");

        jsonArray = (JSONArray) jsonObject.get("Spawn");
        iter = jsonArray.iterator();
        Spawn[0] = (Long) iter.next();
        Spawn[1] = (Long) iter.next();
        jsonArray = (JSONArray) jsonObject.get("Win");
        iter = jsonArray.iterator();
        Win[0] = (Long) iter.next();
        Win[1] = (Long) iter.next();
        spawnFlipX = (Boolean) jsonObject.get("spawn_x_flip");
        spawnFlipY = (Boolean) jsonObject.get("spawn_y_flip");
        map = new int[widht.intValue()][height.intValue()];

        jsonArray = (JSONArray) jsonObject.get("map");
        iterArr = jsonArray.iterator();
        for (int i = 0; i < height.intValue(); i++) {
            jsonInArray = iterArr.next();
            iter = jsonInArray.iterator();
            for (int j = 0; j < widht.intValue(); j++) {
                num = (Long) iter.next();
                map[j][height.intValue() - 1 - i] = num.intValue();
            }
        }

        if (jsonObject.get("hide_traps_count") != null && jsonObject.get("hide_traps") != null) {
            hide_traps_count = (Long) jsonObject.get("hide_traps_count");
            hide_traps = new int[2][getHideTrapCount()];
            if (hide_traps_count == 0) {

            } else {
                jsonArray = (JSONArray) jsonObject.get("hide_traps");
                iterArr = jsonArray.iterator();
                for (int i = 0; i < getHideTrapCount(); i++) {
                    jsonInArray = iterArr.next();
                    iter = jsonInArray.iterator();
                    for (int j = 0; j < 2; j++) {
                        num = (Long) iter.next();
                        // System.out.print(num+" ");
                        hide_traps[j][i] = num.intValue();
                    }
                    // System.out.println();
                }
            }
        }
        if (jsonObject.get("teleport_count") != null && jsonObject.get("teleport") != null) {
            teleport_count = (Long) jsonObject.get("teleport_count");
            teleport = new int[4][getTeleportCount()];
            if (teleport_count == 0) {

            } else {
                jsonArray = (JSONArray) jsonObject.get("teleport");
                iterArr = jsonArray.iterator();
                for (int i = 0; i < getTeleportCount(); i++) {
                    jsonInArray = iterArr.next();
                    iter = jsonInArray.iterator();
                    for (int j = 0; j < 4; j++) {
                        num = (Long) iter.next();
                        // System.out.print(num+" ");
                        teleport[j][i] = num.intValue();
                    }
                    // System.out.println();
                }
            }
        }
        if (jsonObject.get("flamethrower_count") != null && jsonObject.get("flamethrower") != null) {
            flamethrower_count = (Long) jsonObject.get("flamethrower_count");
            flamethrowfer = new int[5][getFlamethrowerCount()];
            if (flamethrower_count == 0) {

            } else {
                jsonArray = (JSONArray) jsonObject.get("flamethrower");
                iterArr = jsonArray.iterator();
                for (int i = 0; i < getFlamethrowerCount(); i++) {
                    jsonInArray = iterArr.next();
                    iter = jsonInArray.iterator();
                    for (int j = 0; j < 5; j++) {
                        num = (Long) iter.next();
                        //System.out.print(num+" ");
                        flamethrowfer[j][i] = num.intValue();
                    }
                    //System.out.println();
                }
            }
        }
        if (jsonObject.get("crossbow_count") != null && jsonObject.get("crossbow") != null) {
            crossbow_count = (Long) jsonObject.get("crossbow_count");
            crossbow = new int[7][getCrossbowCount()];
            if (crossbow_count == 0) {

            } else {
                jsonArray = (JSONArray) jsonObject.get("crossbow");
                iterArr = jsonArray.iterator();
                for (int i = 0; i < getCrossbowCount(); i++) {
                    jsonInArray = iterArr.next();
                    iter = jsonInArray.iterator();
                    for (int j = 0; j < 5; j++) {
                        num = (Long) iter.next();
                        //System.out.print(num+" ");
                        crossbow[j][i] = num.intValue();
                    }
                    //System.out.println();
                }
            }
        }
        if (jsonObject.get("items_count") != null && jsonObject.get("items") != null) {
            items_count = (Long) jsonObject.get("items_count");
            items = new int[3][getItemsCount()];
            if (!(items_count == 0))
                jsonArray = (JSONArray) jsonObject.get("items");
            iterArr = jsonArray.iterator();
            for (int i = 0; i < getItemsCount(); i++) {
                jsonInArray = iterArr.next();
                iter = jsonInArray.iterator();
                for (int j = 0; j < 3; j++) {
                    num = (Long) iter.next();
                    //System.out.print(num+" ");
                    items[j][i] = num.intValue();
                }
                //System.out.println();
            }
        }

        if (jsonObject.get("flimsy_tile_count") != null && jsonObject.get("flimsy_tile") != null) {
            flimsy_tile_count = (Long) jsonObject.get("flimsy_tile_count");
            flimsy_tile = new int[2][getFlimstTileCount()];
            if (!(flimsy_tile_count == 0)) {
                jsonArray = (JSONArray) jsonObject.get("flimsy_tile");
                iterArr = jsonArray.iterator();
                for (int i = 0; i < getFlimstTileCount(); i++) {
                    jsonInArray = iterArr.next();
                    iter = jsonInArray.iterator();
                    for (int j = 0; j < 2; j++) {
                        num = (Long) iter.next();
                        //System.out.print(num+" ");
                        flimsy_tile[j][i] = num.intValue();
                    }
                    //System.out.println();
                }
            }
        }

        end = (Boolean) jsonObject.get("end");
        nextpath = (String) jsonObject.get("nextlevelpath");

        isSave = (Boolean) jsonObject.get("save");

        System.out.println(height + " " + getHeight());
        // System.out.println(map[5][6]);

    }

    public int getFlimstTileCount() {
        return flimsy_tile_count.intValue();
    }

    public int[][] getFlimsyTile() {
        return flimsy_tile;
    }

    public int getItemsCount() {
        return items_count.intValue();
    }

    public int[][] getItems() {
        return items;
    }

    public int[][] getCrossbow() {
        return crossbow;
    }

    public int getCrossbowCount() {
        return crossbow_count.intValue();
    }

    public int[][] getFlamethrowfers() {
        return flamethrowfer;
    }

    public int getFlamethrowerCount() {
        return flamethrower_count.intValue();
    }

    public int[][] getTeleport() {
        return teleport;
    }

    public int getTeleportCount() {
        return teleport_count.intValue();
    }

    public int[][] getHideTraps() {
        return hide_traps;
    }
    public int getHideTrapCount() {
        return hide_traps_count.intValue();
    }

    public boolean isEnd() {
        return end;
    }

    public String getNextpath() {
        return nextpath;
    }

    public boolean isSpawnFlipX() {
        return spawnFlipX;
    }

    public String getLevelName() {
        return levelName;
    }

    public int getWidht() {
        return widht.intValue();
    }
    public int getHeight() {
        return height.intValue();
    }

    public int[] getSpawn() {
        return new int[]{Spawn[0].intValue(), Spawn[1].intValue()};
    }
    public int[] getWin() {
        return new int[]{Win[0].intValue(), Win[1].intValue()};
    }

//    public int getStairsCount(){return stairs_count.intValue();}

    public int[][] getMap() {
        return map;
    }

    public boolean isSave() {
        return isSave;
    }

}
