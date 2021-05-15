package com.zaig100.dg.utils.contain;

public class CrossbowC extends ObjC {
    int dx, dy, angle;
    float tick_sec = 0.5f;

    public CrossbowC(int x, int y, int dx, int dy, int angle, String tag) {
        super(x, y, tag);
        this.dx = dx;
        this.dy = dy;
        this.angle = angle;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getAngle() {
        return angle;
    }

    public float getTick_sec() {
        return tick_sec;
    }

    public void setTick_sec(float tick_sec) {
        this.tick_sec = tick_sec;
    }
}
