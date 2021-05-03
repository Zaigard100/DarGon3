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
import java.util.ArrayList;
import java.util.Iterator;

public class LevelRead {

    JSONParser jsonParser = new JSONParser();
    JSONObject jsonObject, JO;
    JSONArray jsonArray;
    Iterator iter;
    Reader in;
    long time;

    int[] map;
    int wight, height, SpawnX, SpawnY;
    String levelname;
    boolean flipX, flipY, isSave;

    ArrayList<HideTrapC> hide_trap = new ArrayList<>();
    ArrayList<TeleportC> teleport = new ArrayList<>();
    ArrayList<StairC> stair = new ArrayList<>();
    ArrayList<ItemC> item = new ArrayList<>();
    ArrayList<FlimsyTileC> flimsy_tile = new ArrayList<>();
    ArrayList<FlamefrowerC> flamefrower = new ArrayList<>();
    ArrayList<CrossbowC> crosbow = new ArrayList<>();

    public LevelRead(String path, boolean isPack) {
        time = System.currentTimeMillis();
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
        flipX = (Boolean) jsonObject.get("spawn_x_flip");
        flipY = (Boolean) jsonObject.get("spawn_y_flip");

        map_read();
        hide_trap_read();
        telepotr_read();
        stair_read();
        item_read();
        flimsy_tile_read();
        flamefrower_read();
        crossbow_read();

        isSave = (Boolean) jsonObject.get("save");

        System.out.println(System.currentTimeMillis() - time);

    }

    private void map_read() {
        jsonArray = (JSONArray) jsonObject.get("map");
        wight = ((JSONArray) jsonArray.get(0)).size();
        height = jsonArray.size();
        map = new int[wight * height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < wight; j++) {
                map[i * wight + j] = ((Long) ((JSONArray) jsonArray.get(i)).get(j)).intValue();
            }
        }
    }

    private void hide_trap_read() {
        if (jsonObject.get("HideTraps") != null) {
            jsonArray = (JSONArray) jsonObject.get("HideTraps");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                JO = (JSONObject) iter.next();
                hide_trap.add(new HideTrapC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue()));
            }
        }
    }

    private void telepotr_read() {
        if (jsonObject.get("Teleport") != null) {
            jsonArray = (JSONArray) jsonObject.get("Teleport");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                JO = (JSONObject) iter.next();
                teleport.add(new TeleportC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Long) JO.get("TX")).intValue(), ((Long) JO.get("TY")).intValue()));
            }
        }
    }

    private void stair_read() {
        if (jsonObject.get("Stair") != null) {
            jsonArray = (JSONArray) jsonObject.get("Stair");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                JO = (JSONObject) iter.next();
                stair.add(new StairC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), (String) JO.get("Next")));
            }
        }
    }

    private void item_read() {
        if (jsonObject.get("Item") != null) {
            jsonArray = (JSONArray) jsonObject.get("Item");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                JO = (JSONObject) iter.next();
                item.add(new ItemC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Long) JO.get("ID")).intValue()));
            }
        }
    }

    private void flimsy_tile_read() {
        if (jsonObject.get("FlimsyTile") != null) {
            jsonArray = (JSONArray) jsonObject.get("FlimsyTile");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                JO = (JSONObject) iter.next();
                flimsy_tile.add(new FlimsyTileC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Long) JO.get("Stage")).intValue()));
            }
        }
    }

    private void flamefrower_read() {
        if (jsonObject.get("Flamefrower") != null) {
            jsonArray = (JSONArray) jsonObject.get("Flamefrower");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                JO = (JSONObject) iter.next();
                flamefrower.add(new FlamefrowerC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Long) JO.get("Stage")).intValue(), ((Long) JO.get("Max")).intValue(), ((Long) JO.get("Rot")).intValue()));
            }
        }
    }

    private void crossbow_read() {
        if (jsonObject.get("Crossbow") != null) {
            jsonArray = (JSONArray) jsonObject.get("Crossbow");
            iter = jsonArray.iterator();
            while (iter.hasNext()) {
                JO = (JSONObject) iter.next();
                crosbow.add(new CrossbowC(((Long) JO.get("X")).intValue(), ((Long) JO.get("Y")).intValue(), ((Long) JO.get("DX")).intValue(), ((Long) JO.get("DY")).intValue(), ((Long) JO.get("Angle")).intValue()));
            }
        }
    }


}

class HideTrapC {
    int x, y;

    public HideTrapC(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class TeleportC {
    int x, y, tx, ty;

    public TeleportC(int x, int y, int tx, int ty) {
        this.x = x;
        this.y = y;
        this.tx = tx;
        this.ty = ty;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTx() {
        return tx;
    }

    public int getTy() {
        return ty;
    }
}

class StairC {
    int x, y;
    String next;

    public StairC(int x, int y, String next) {
        this.x = x;
        this.y = y;
        this.next = next;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getNext() {
        return next;
    }
}

class ItemC {
    int x, y, id;

    public ItemC(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }
}

class FlimsyTileC {
    int x, y, stage;

    public FlimsyTileC(int x, int y, int stage) {
        this.x = x;
        this.y = y;
        this.stage = stage;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getStage() {
        return stage;
    }
}

class FlamefrowerC {
    int x, y, stage, max, rot;

    public FlamefrowerC(int x, int y, int stage, int max, int rot) {
        this.x = x;
        this.y = y;
        this.stage = stage;
        this.max = max;
        this.rot = rot;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getStage() {
        return stage;
    }

    public int getMax() {
        return max;
    }

    public int getRot() {
        return rot;
    }
}

class CrossbowC {
    int x, y, dx, dy, angle;

    public CrossbowC(int x, int y, int dx, int dy, int angle) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.angle = angle;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getAngle() {
        return angle;
    }
}