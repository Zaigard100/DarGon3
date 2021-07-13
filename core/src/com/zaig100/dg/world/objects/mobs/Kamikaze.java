package com.zaig100.dg.world.objects.mobs;

import com.zaig100.dg.utils.ai.way.MainWay;
import com.zaig100.dg.utils.ai.way.StartWay;
import com.zaig100.dg.world.Player;
import com.zaig100.dg.world.World;

public class Kamikaze extends Mob {

    int findRadius,iter;
    boolean active = false;
    String instruction;
    StartWay start;

    public Kamikaze(int x, int y,int iter, int findRadius, String tag) {
        //TODO доделать
        super(x, y, tag);
        this.findRadius = findRadius;
        this.iter = iter;
    }

    @Override
    public void frame() {
        if(distance(World.player.getX(),World.player.getY())<=findRadius){
            if(World.player.getX()!=endX&&World.player.getY()!=getY()) {
                endX = World.player.getX();
                endY = World.player.getY();
                active = true;

            }
        }

        if(active){

            if(getX()==endX&&getY()==endY){
                active = false;
                return;
            }else{
                MainWay main = new MainWay(getX(), getY(), endX, endY, iter);
                main.init();
                start = (StartWay) main.shortWay();
                x = start.getX();
                y = start.getY();
            }

        }
    }

    private double distance(int oX,int oY){
        return Math.sqrt(((oX-getX())*(oX-getX()))+((oY-getY())*(oY-getY())));
    }

}
