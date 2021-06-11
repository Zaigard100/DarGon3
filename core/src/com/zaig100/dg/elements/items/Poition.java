package com.zaig100.dg.elements.items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zaig100.dg.objects.Player;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

import org.json.simple.JSONObject;

public class Poition extends Item {

    public Poition() {
        super();
        type = ItemType.POITION;
    }

    public Poition(int i) {
        super(i);
        type = ItemType.POITION;
    }

    @Override
    public void render(Batch batch, int x, int y) {
        batch.draw(
                Res.hp_potion,
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
        if (Player.getHp() < 4 && Player.getHp() > 0) {
            Player.setHp(Player.getHp() + 1);
            return true;
        }
        return false;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Type", type.toString());
        json.put("Count", count);
        return json;
    }
}
