package com.zaig100.dg.objects;

import com.zaig100.dg.utils.Res;

public class HideTrap {
    int x,y;
    Player player;
    boolean active;
    public HideTrap(int x, int y, Player player){
        this.x = x;
        this.y = y;
        this.player = player;
        this.active = true;
    }
    public HideTrap(int x, int y, boolean active, Player player){
        this.x = x;
        this.y = y;
        this.player = player;
        this.active = active;
    }
    public void frame(Res res){
        if(active) {
            if ((player.getX() == x) && (player.getY() == y)) {
                if(!player.isSheld()){
                    player.setHp(player.getHp() - 1);
                }
                player.setDamgeScr(0f,1);
                res.lov.play(1.0f);
                active =false;
            }
        }
    }
}
