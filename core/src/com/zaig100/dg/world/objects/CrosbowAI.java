package com.zaig100.dg.world.objects;

import com.zaig100.dg.utils.contain.CrossbowAIC;
import com.zaig100.dg.utils.contain.CrossbowC;
import com.zaig100.dg.world.World;

public class CrosbowAI extends Crossbow{

    int distance;
    boolean diagonal;

    public CrosbowAI(CrossbowAIC contain) {
        super(contain);
        type = ObjType.CROSSBOW_AI;
        this.distance = contain.getDistanse();
        this.diagonal = contain.isDiagonal();
    }

    @Override
    public void frame() {
        super.frame();

        if(World.player.getX() == x){
            if(y>World.player.getY()){
                dy = 1;
            }
            if(y<World.player.getY()){
                dy = -1;
            }
        }

        if(World.player.getY() == y){
            if(y>World.player.getX()){
                dy = -1;
            }
            if(y<World.player.getX()){
                dy = 1;
            }
        }

        if(diagonal){

            if(x-y == World.player.getX()-World.player.getY()){
                if(y<World.player.getY()) {
                    dy = -1;
                    dx = 1;
                }
                if(y<World.player.getY()) {
                    dy = 1;
                    dx = -1;
                }
            }

            if(-x-y == -World.player.getX()-World.player.getY()){
                if(y<World.player.getY()) {
                    dy = 1;
                    dx = 1;
                }
                if(y<World.player.getY()) {
                    dy = -1;
                    dx = -1;
                }
            }

        }
    }
}
