package com.zaig100.dg.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

public class Map {


    int mapWidht,mapHeight;
    boolean isDark;
    int[][] map;
    int j;int i;

    public Map(int mapWidht , int mapHeight, int[][] map, boolean isDark){
        this.mapWidht = mapWidht;
        this.mapHeight = mapHeight;
        this.map = map;
        this.isDark = isDark;

    }

    public boolean isGround(int oldX,int oldY){
        if((oldX>=mapWidht) || (oldY>=mapHeight) || (oldX<0) || (oldY<0)){
            return false;
        }else {
            return map[oldX][oldY] == 11;
        }
    }



    public void render(SpriteBatch batch, Res res, int wX, int wY,Player player, Configuration config){
        for (i = player.getY()-4; i < player.getY()+4; i++) {
            for (j = player.getX()-5; j < player.getX()+5; j++) {
                if(i>=0&&i<mapHeight&&j>=0&&j<mapWidht) {
                    if (map[j][i] == 11) {
                        batch.draw(res.tile(0, 0), -wX + (j + 3) * 16 * config.getScale(), -wY + (i + 2) * 16 * config.getScale(), 16 * config.getScale(), 16 * config.getScale());
                    }
                    if (map[j][i] == 12) {
                        batch.draw(res.tile(1, 1), -wX + (j + 3) * 16 * config.getScale(), -wY + (i + 2) * 16 * config.getScale(), 16 * config.getScale(), 16 * config.getScale());
                    }
                    if (map[j][i] == 13) {
                        batch.draw(res.tile(2, 0), -wX + (j + 3) * 16 * config.getScale(), -wY + (i + 2) * 16 * config.getScale(), 16 * config.getScale(), 16 * config.getScale());
                    }
                    if (map[j][i] == 14) {
                        batch.draw(res.tile(1, 0), -wX + (j + 3) * 16 * config.getScale(), -wY + (i + 2) * 16 * config.getScale(), 16 * config.getScale(), 16 * config.getScale());
                    }
                    if (map[j][i] == 15) {
                        batch.draw(res.tile(2, 1), -wX + (j + 3) * 16 * config.getScale(), -wY + (i + 2) * 16 * config.getScale(), 16 * config.getScale(), 16 * config.getScale());
                    }
                    if (map[j][i] == 16) {
                        batch.draw(res.tile(3, 0), -wX + (j + 3) * 16 * config.getScale(), -wY + (i + 2) * 16 * config.getScale(), 16 * config.getScale(), 16 * config.getScale());
                    }
                    if (map[j][i] == 17) {
                        batch.draw(res.tile(3, 1), -wX + (j + 3) * 16 * config.getScale(), -wY + (i + 2) * 16 * config.getScale(), 16 * config.getScale(), 16 * config.getScale());
                    }
                    if (map[j][i] == 18) {
                        batch.draw(res.tile(4, 1), -wX + (j + 3) * 16 * config.getScale(), -wY + (i + 2) * 16 * config.getScale(), 16 * config.getScale(), 16 * config.getScale());
                    }
                    if (map[j][i] == 19) {
                        batch.draw(res.tile(4, 0), -wX + (j + 3) * 16 * config.getScale(), -wY + (i + 2) * 16 * config.getScale(), 16 * config.getScale(), 16 * config.getScale());
                    }
                }
            }
        }
    }
    public void dark_render(SpriteBatch batch, Res res,Configuration config){
        batch.draw(res.boards,7*16*config.getScale(),0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.draw(res.boards,0,5*16*config.getScale(), Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        if(isDark){
           batch.draw(res.dark,-16*config.getScale()*4,-16*config.getScale()*3,240*config.getScale(),176*config.getScale());
        }
    }

    public int getMapWidht() {
        return mapWidht;
    }

    public int getMapHeight() {
        return mapHeight;
    }


    //public boolean isDark() { return isDark;}


    public void setDark(boolean dark) {
        isDark = dark;
    }

}
