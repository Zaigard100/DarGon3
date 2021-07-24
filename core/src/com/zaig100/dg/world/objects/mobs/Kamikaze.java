package com.zaig100.dg.world.objects.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zaig100.dg.utils.Configuration;
import com.zaig100.dg.utils.Res;
import com.zaig100.dg.utils.ai.way.MainWay;
import com.zaig100.dg.utils.ai.way.StartWay;
import com.zaig100.dg.utils.contain.mobC.KamikadzeC;
import com.zaig100.dg.world.Player;
import com.zaig100.dg.world.World;
import com.zaig100.dg.world.objects.Arrow;

public class Kamikaze extends Mob {

    int findRadius,iter;
    boolean active = false;
    String instruction;
    StartWay start;

    float timer;

    public Kamikaze(int x, int y,int iter, int findRadius, String tag) {
        super(x, y, tag);
        this.findRadius = findRadius;
        this.iter = iter;
    }

    public Kamikaze(KamikadzeC contain) {
        super(contain.getX(),contain.getY(), contain.getTag());
        type = ObjType.KAMIKAZE;
        findRadius = contain.getFindRadius();
        iter = contain.getIter();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(
                Res.spikedhero,
                wX - World.player.wX,
                wY - World.player.wY,
                16 * Configuration.getScale(),
                16 * Configuration.getScale()
        );
    }

    @Override
    public void frame() {
        if(distance(World.player.getX(),World.player.getY())<=findRadius){
                if(!(World.player.getX()==endX&&World.player.getY()==endY)) {
                    endX = World.player.getX();
                    endY = World.player.getY();
                    active = true;
                }
        }

        if(active){

            if(World.player.getX()==x&&World.player.getY()==y){
                if(!isMove()) {
                    System.out.println("Boom");
                    active = false;
                    World.player.setDamgeScr(0f,1);
                    World.player.setHp(World.player.hp-2);
                    del();
                }
            }
            if(!isMove()) {
                MainWay main = new MainWay(x, y, endX, endY, iter);
                main.init();
                start = (StartWay) main.shortWay();
                x = start.getX();
                y = start.getY();

            }

            move(0.5f);

        }

    }



    private double distance(int oX,int oY){
        return Math.sqrt(((oX-getX())*(oX-getX()))+((oY-getY())*(oY-getY())));
    }

}
