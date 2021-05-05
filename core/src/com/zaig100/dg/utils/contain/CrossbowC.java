package com.zaig100.dg.utils.contain;

public class CrossbowC {
    int x, y, dx, dy, angle;

    public CrossbowC(int x, int y, int dx, int dy, int angle) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.angle = angle;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
