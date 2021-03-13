package com.zaig100.dg.utils;

import com.badlogic.gdx.Gdx;
import com.zaig100.dg.Main;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class Configuration {

    File conf;

    JSONObject jsonObject;

    float Scale = 6;
    int Music = 50;
    int Sound = 50;
    boolean sensor = true;
    boolean debug = false;

    public Configuration(Main m) throws IOException {

        if(m.isAndroid()){
            conf = Gdx.files.local("DGConfig.json").file();
        }else {
            conf = new File(new File(".").getAbsoluteFile(),"config.json");
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
            Scale = ((Double) jsonObject.get("Scale")).floatValue();
            Music = ((Long) jsonObject.get("Music")).intValue();
            Sound = ((Long) jsonObject.get("Sound")).intValue();
            sensor = ((Boolean) jsonObject.get("Sensor")).booleanValue();
            debug = ((Boolean) jsonObject.get("Debug")).booleanValue();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    public void save(File f){
        jsonObject.put("Scale", new Float(Scale));
        jsonObject.put("Music", new Integer(Music));
        jsonObject.put("Sound", new Integer(Sound));
        jsonObject.put("Sensor", new Boolean(sensor));
        jsonObject.put("Debug", new Boolean(debug));

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

    public float getScale() {
        return Scale;
    }

    public void setScale(float scale) {
        Scale = scale;
    }

    public int getMusic() {
        return Music;
    }

    public void setMusic(int music) {
        Music = music;
    }

    public int getSound() {
        return Sound;
    }

    public void setSound(int sound) {
        Sound = sound;
    }

    public File getConf(){
        return conf;
    }

    public boolean isSensor() {
        return sensor;
    }

    public void setSensor(boolean sensor) {
        this.sensor = sensor;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

}
