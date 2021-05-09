package com.zaig100.dg.utils;

import com.badlogic.gdx.Gdx;
import com.zaig100.dg.Main;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class Save {

    static File conf;

    static JSONObject jsonObject;

    static String path = "levels/01.json";
    static int hp = 4;
    static int potion = 3;
    static int sheld = 2;
    static int torch = 1;

    public  Save(Main m,String pack,String derect) throws IOException {
         path = derect;
        if(m.isAndroid()){
            conf = Gdx.files.local("save"+pack+".json").file();
        }else {
            conf = new File(new File("").getAbsoluteFile(), "save" + pack + ".json");
        }

        JSONParser jsonParser = new JSONParser();

        if(!(conf.exists())) {
            try {
                jsonObject = (JSONObject) jsonParser.parse("{}");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            createDefaultConfig(conf);
        }

        Reader in = new InputStreamReader(new FileInputStream(conf));
        try {
            jsonObject = (JSONObject) jsonParser.parse(in);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        load();
        in.close();
    }

    void load(){
        try {
            path = (String) jsonObject.get("Path");
            hp = ((Long) jsonObject.get("HP")).intValue();
            potion = ((Long) jsonObject.get("Potion")).intValue();
            sheld = ((Long) jsonObject.get("Sheld")).intValue();
            torch = ((Long) jsonObject.get("Torch")).intValue();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    public void save(File f){
        jsonObject.put("Path", path);
        jsonObject.put("HP", new Integer(hp));
        jsonObject.put("Potion", new Integer(potion));
        jsonObject.put("Sheld", new Integer(sheld));
        jsonObject.put("Torch", new Integer(torch));

        try{
            FileWriter file = new FileWriter(f);
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }

    }

    private void createDefaultConfig(File f) {
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        save(f);
    }

    public File getConf() {
        return conf;
    }

    public void setConf(File conf) {
        Save.conf = conf;
    }

    public String getsPath() {
        return path;
    }

    public void setPath(String path) {
        Save.path = path;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        Save.hp = hp;
    }

    public int getPotion() {
        return potion;
    }

    public void setPotion(int potion) {
        Save.potion = potion;
    }

    public int getSheld() {
        return sheld;
    }

    public void setSheld(int sheld) {
        Save.sheld = sheld;
    }

    public int getTorch() {
        return torch;
    }

    public void setTorch(int torch) {
        Save.torch = torch;
    }

}
