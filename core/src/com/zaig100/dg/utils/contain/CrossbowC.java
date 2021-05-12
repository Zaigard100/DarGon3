package com.zaig100.dg.utils.contain;

public class CrossbowC extends ObjC {
    int dx, dy, angle;

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
}
