package com.zaig100.dg.elements.items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zaig100.dg.objects.Player;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

import org.json.simple.JSONObject;

public class Torch extends Item {

    public Torch() {
        type = ItemType.TORCH;
    }

    public Torch(int count) {
        super(count);
        type = ItemType.TORCH;
    }

    @Override
    public void render(Batch batch, int x, int y) {
        batch.draw(
                Res.torch,
                x * 16 * Configuration.getScale() + 19 * Configuration.getScale(),
                y * 16 * Configuration.getScale() + 19 * Configuration.getScale(),
                16 * Configuration.getScale() - 6 * Configuration.getScale(),
                16 * Configuration.getScale() - 6 * Configuration.getScale()
        );
        Res.getFont((int) (3 * Configuration.getScale())).draw(batch,
                String.valueOf(count),
                x * 16 * Configuration.getScale() + 28 * Configuration.getScale(),
                y * 16 * Configuration.getScale() + 22 * Configuration.getScale()
        );
    }

    @Override
    public boolean use() {
        if (!Player.getMap().isDark()) {
            return false;
        }
        Player.getMap().setDark(false);
        return true;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Type", type.toString());
        json.put("Count", count);
        return json;
    }

}
