package com.zaig100.dg.elements.items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zaig100.dg.objects.Player;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;

public class Torch extends Item {
    @Override
    void render(Batch batch, int x, int y) {
        batch.draw(
                Res.torch,
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
        if (!Player.getMap().isDark()) {
            return false;
        }
        Player.getMap().setDark(false);
        return true;
    }
}
