package com.zaig100.dg.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

public class Map {


    int mapWidht,mapHeight;
    boolean isDark;
    public int[] map;
    int j;
    int i;

    public Map(int mapWidht, int mapHeight, int[] map, boolean isDark) {
        this.mapWidht = mapWidht;
        this.mapHeight = mapHeight;
        this.map = map;
        this.isDark = isDark;

    }

    public boolean isGround(int oldX, int oldY) {
        if ((oldX >= mapWidht) || (oldY >= mapHeight) || (oldX < 0) || (oldY < 0)) {
            return false;
        } else {
            return map[oldX + oldY * mapWidht] == 11;
        }
    }


    public void render(SpriteBatch batch) {
        for (i = Player.getY() - 4; i < Player.getY() + 4; i++) {
            for (j = Player.getX() - 5; j < Player.getX() + 5; j++) {
                if (i >= 0 && i < mapHeight && j >= 0 && j < mapWidht) {
                    if (map[j + mapWidht * i] == 11) {
                        batch.draw(Res.tile(0, 0), -Player.wX + (j + 3) * 16 * Configuration.getScale(), -Player.wY + (i + 2) * 16 * Configuration.getScale(), 16 * Configuration.getScale(), 16 * Configuration.getScale());
                    }
                    if (map[j + mapWidht * i] == 12) {
                        batch.draw(Res.tile(1, 1), -Player.wX + (j + 3) * 16 * Configuration.getScale(), -Player.wY + (i + 2) * 16 * Configuration.getScale(), 16 * Configuration.getScale(), 16 * Configuration.getScale());
                    }
                    if (map[j + mapWidht * i] == 13) {
                        batch.draw(Res.tile(2, 0), -Player.wX + (j + 3) * 16 * Configuration.getScale(), -Player.wY + (i + 2) * 16 * Configuration.getScale(), 16 * Configuration.getScale(), 16 * Configuration.getScale());
                    }
                    if (map[j + mapWidht * i] == 14) {
                        batch.draw(Res.tile(1, 0), -Player.wX + (j + 3) * 16 * Configuration.getScale(), -Player.wY + (i + 2) * 16 * Configuration.getScale(), 16 * Configuration.getScale(), 16 * Configuration.getScale());
                    }
                    if (map[j + mapWidht * i] == 15) {
                        batch.draw(Res.tile(2, 1), -Player.wX + (j + 3) * 16 * Configuration.getScale(), -Player.wY + (i + 2) * 16 * Configuration.getScale(), 16 * Configuration.getScale(), 16 * Configuration.getScale());
                    }
                    if (map[j + mapWidht * i] == 16) {
                        batch.draw(Res.tile(3, 0), -Player.wX + (j + 3) * 16 * Configuration.getScale(), -Player.wY + (i + 2) * 16 * Configuration.getScale(), 16 * Configuration.getScale(), 16 * Configuration.getScale());
                    }
                    if (map[j + mapWidht * i] == 17) {
                        batch.draw(Res.tile(3, 1), -Player.wX + (j + 3) * 16 * Configuration.getScale(), -Player.wY + (i + 2) * 16 * Configuration.getScale(), 16 * Configuration.getScale(), 16 * Configuration.getScale());
                    }
                    if (map[j + mapWidht * i] == 18) {
                        batch.draw(Res.tile(4, 1), -Player.wX + (j + 3) * 16 * Configuration.getScale(), -Player.wY + (i + 2) * 16 * Configuration.getScale(), 16 * Configuration.getScale(), 16 * Configuration.getScale());
                    }
                    if (map[j + mapWidht * i] == 19) {
                        batch.draw(Res.tile(4, 0), -Player.wX + (j + 3) * 16 * Configuration.getScale(), -Player.wY + (i + 2) * 16 * Configuration.getScale(), 16 * Configuration.getScale(), 16 * Configuration.getScale());
                    }
                }
            }
        }
    }

    public void dark_render(SpriteBatch batch) {
        batch.draw(Res.boards, 7 * 16 * Configuration.getScale(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(Res.boards, 0, 5 * 16 * Configuration.getScale(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (isDark) {
            batch.draw(Res.dark, -16 * Configuration.getScale() * 4, -16 * Configuration.getScale() * 3, 240 * Configuration.getScale(), 176 * Configuration.getScale());
        }
    }

    public void tag_activate(String func) {
        System.out.println(Integer.parseInt(func.split(">")[0].split("-")[1].split(",")[0]) + mapWidht * Integer.parseInt(func.split(">")[0].split("-")[1].split(",")[1]));
        if (func.split("-")[0].equals("map")) {
            map[Integer.parseInt(func.split(">")[0].split("-")[1].split(",")[0]) + mapWidht * Integer.parseInt(func.split(">")[0].split("-")[1].split(",")[1])] = Integer.parseInt(func.split(">")[1]);
        }
        switch (func.split(">")[0]) {
            case "isDark":
                if (func.split(">")[1].equals("++") || func.split(">")[1].equals("--")) {
                    isDark = !isDark;
                } else {
                    isDark = Boolean.parseBoolean((func.split(">")[1]));
                }
                break;
        }
    }

    public int getMapWidht() {
        return mapWidht;
    }

    public int getMapHeight() {
        return mapHeight;
    }


    public boolean isDark() {
        return isDark;
    }


    public void setDark(boolean dark) {
        isDark = dark;
    }

}
