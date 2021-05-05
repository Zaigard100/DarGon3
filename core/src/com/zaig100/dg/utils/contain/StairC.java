package com.zaig100.dg.utils.contain;

public class StairC {
    int x, y;
    String next;
    boolean flipX, isEnd = false;

    public StairC(int x, int y, boolean flipX, String next) {
        this.x = x;
        this.y = y;
        this.flipX = flipX;
        this.next = next;
        if (next == null || next == "END") {
            isEnd = true;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getFlipX() {
        return flipX;
    }

    public String getNext() {
        return next;
    }

    public boolean isEnd() {
        return isEnd;
    }

}
