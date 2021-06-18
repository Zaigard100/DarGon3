package com.zaig100.dg.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.LevelRead;
import com.zaig100.dg.utils.Res;
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
import com.zaig100.dg.utils.contain.TeleportC;

import java.util.ArrayList;
import java.util.Iterator;

public class Map {

    private int i1, idNum = 0;
    private StairC stairС;
    private TeleportC teleportC;
    private HideTrapC hideTrapC;
    private FlamefrowerC flamefrowerС;
    private CrossbowC crossbowC;
    private ItemC itemC;
    private FlimsyTileC flimsyTileC;
    private SpinneyC spinneyC;
    private SpikeC spikeC;
    private ButtonС buttonC;
    private DoorC doorC;

    private Iterator iter;

    public ArrayList<Obj> objectsU = new ArrayList<>();
    public ArrayList<Obj> objectsO = new ArrayList<>();
    public ArrayList<Stair> stair = new ArrayList<>();

    int mapWidht, mapHeight;
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


    public void object_update(SpriteBatch batch) {
        if (objectsU.size() > 0) {
            for (i1 = 0; i1 < objectsU.size(); i1++) {
                objectsU.get(i1).render(batch);
                if (!Player.isStop) objectsU.get(i1).frame();
            }
        }
        Player.render(batch);
        if (!Player.isStop) Player.tick(0.2f);
        if (!Player.isStop) Player.frame();

        if (objectsO.size() > 0) {
            for (i1 = 0; i1 < objectsO.size(); i1++) {
                objectsO.get(i1).render(batch);
                if (!Player.isStop) objectsO.get(i1).frame();
            }
        }
    }

    public void object_load(LevelRead lR) {

        Player.setX(lR.getSpawnX());
        Player.setY(lR.getSpawnY());
        Player.wCordNormalize();
        setDark(!lR.isDark());


        iter = lR.getStair().iterator();
        while (iter.hasNext()) {
            stairС = (StairC) iter.next();
            stair.add(new Stair(stairС.getX(), stairС.getY(), stairС.getFlipX(), stairС.getNext(), stairС.isEnd(), stairС.getTag()));
            stair.get(stair.size() - 1).setObjID(idNum);
            idNum++;
        }

        iter = lR.getTeleport().iterator();
        while (iter.hasNext()) {
            teleportC = (TeleportC) iter.next();
            objectsU.add(new Teleport(teleportC.getX(), teleportC.getY(), teleportC.getTx(), teleportC.getTy(), teleportC.getTag()));
            objectsU.get(objectsU.size() - 1).setObjID(idNum);
            idNum++;
        }

        iter = lR.getHide_trap().iterator();
        while (iter.hasNext()) {
            hideTrapC = (HideTrapC) iter.next();
            objectsU.add(new HideTrap(hideTrapC.getX(), hideTrapC.getY(), hideTrapC.isActive(), hideTrapC.getTag()));
            objectsU.get(objectsU.size() - 1).setObjID(idNum);
            idNum++;
        }

        iter = lR.getFlamefrower().iterator();
        while (iter.hasNext()) {
            flamefrowerС = (FlamefrowerC) iter.next();
            objectsO.add(new Flamefrower(flamefrowerС.getX(), flamefrowerС.getY(), flamefrowerС.getStage(), flamefrowerС.getMax(), flamefrowerС.getRot(), flamefrowerС.getTick_sec(), flamefrowerС.getTag()));
            objectsO.get(objectsO.size() - 1).setObjID(idNum);
            idNum++;
        }

        iter = lR.getCrosbow().iterator();
        while (iter.hasNext()) {
            crossbowC = (CrossbowC) iter.next();
            objectsO.add(new Crossbow(crossbowC.getX(), crossbowC.getY(), crossbowC.getDx(), crossbowC.getDy(), crossbowC.getAngle(), crossbowC.getTick_sec(), crossbowC.getTag()));
            objectsO.get(objectsO.size() - 1).setObjID(idNum);
            idNum++;
        }
        iter = lR.getItem().iterator();
        while (iter.hasNext()) {
            itemC = (ItemC) iter.next();
            objectsU.add(new Items(itemC.getX(), itemC.getY(), itemC.getItem(), itemC.isActive(), itemC.getTag()));
            objectsU.get(objectsU.size() - 1).setObjID(idNum);
            idNum++;
        }

        iter = lR.getFlimsy_tile().iterator();
        while (iter.hasNext()) {
            flimsyTileC = (FlimsyTileC) iter.next();
            objectsU.add(new FlimsyTile(flimsyTileC.getX(), flimsyTileC.getY(), flimsyTileC.getStage(), flimsyTileC.getTick_sec(), flimsyTileC.getTag()));
            objectsU.get(objectsU.size() - 1).setObjID(idNum);
            idNum++;
        }

        iter = lR.getSpinney().iterator();
        while (iter.hasNext()) {
            spinneyC = (SpinneyC) iter.next();
            objectsO.add(new Spinney(spinneyC.getX(), spinneyC.getY(), spinneyC.getWight(), spinneyC.getHeight(), spinneyC.getTag()));
            objectsO.get(objectsO.size() - 1).setObjID(idNum);
            idNum++;
        }

        iter = lR.getSpike().iterator();
        while (iter.hasNext()) {
            spikeC = (SpikeC) iter.next();
            objectsU.add(new Spike(spikeC.getX(), spikeC.getY(), spikeC.isActive(), spikeC.getTick_sec(), spikeC.getTag()));
            objectsU.get(objectsU.size() - 1).setObjID(idNum);
            idNum++;
        }

        iter = lR.getButton().iterator();
        while (iter.hasNext()) {
            buttonC = (ButtonС) iter.next();
            //TODO Buutton
            objectsU.add((new Button(buttonC.getX(), buttonC.getY(), buttonC.getFunc(), buttonC.getTag()).setMap(this)));
            objectsU.get(objectsU.size() - 1).setObjID(idNum);
            idNum++;
        }

        iter = lR.getDoor().iterator();
        while (iter.hasNext()) {
            doorC = (DoorC) iter.next();
            objectsO.add(new Door(doorC.getX(), doorC.getY(), doorC.isDoorOpen(), doorC.getKeyTag(), doorC.getFaicing(), doorC.getTag()));
            objectsO.get(stair.size() - 1).setObjID(idNum);
            idNum++;
        }

    }

    public void doFunc(String func) {
        if (stair.size() > 0) {
            for (i = 0; i < stair.size(); i++) {
                if (stair.get(i).getTag().equals(func.split(":")[0])) {
                    stair.get(i).tag_activate(func.split(":")[1]);
                }
            }
        }
        if (objectsU.size() > 0) {
            for (i = 0; i < objectsU.size(); i++) {
                if (objectsU.get(i).getTag().equals(func.split(":")[0])) {
                    objectsU.get(i).tag_activate(func.split(":")[1]);
                }
            }
        }
        if (objectsO.size() > 0) {
            for (i = 0; i < objectsO.size(); i++) {
                if (objectsO.get(i).getTag().equals(func.split(":")[0])) {
                    objectsO.get(i).tag_activate(func.split(":")[1]);
                }
            }
        }
        if (func.split(":")[0].equals("Map")) {
            tag_activate(func.split(":")[1]);
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
