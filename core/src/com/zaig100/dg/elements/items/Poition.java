package com.zaig100.dg.elements.items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zaig100.dg.objects.Player;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

public class Poition extends Item {
    @Override
    void render(Batch batch, int x, int y) {
        batch.draw(
                Res.hp_potion,
                x * 16 * Configuration.getScale() + 3 * Configuration.getScale(),
                y * 16 * Configuration.getScale() + 3 * Configuration.getScale(),
                16 * Configuration.getScale() - 6 * Configuration.getScale(),
                16 * Configuration.getScale() - 6 * Configuration.getScale()
        );
        Res.getFont((int) (3 * Configuration.getScale())).draw(batch,
                String.valueOf(count),
                x * 16 * Configuration.getScale() + 10 * Configuration.getScale(),
                y * 16 * Configuration.getScale() + 3 * Configuration.getScale()
        );
    }

    @Override
    public boolean use() {
        if (Player.getHp() >= 4 || Player.getHp() >= 0) {
            return false;
        }
        Player.setHp(Player.getHp() + 1);
        return true;
    }
}
