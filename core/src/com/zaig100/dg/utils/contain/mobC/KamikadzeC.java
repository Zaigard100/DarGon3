package com.zaig100.dg.utils.contain.mobC;

import com.zaig100.dg.utils.contain.ObjC;

public class KamikadzeC extends ObjC {
    int iter,findRadius;

    public KamikadzeC(int x, int y, String tag, int iter, int findRadius) {
        super(x, y, tag);
        this.iter = iter;
        this.findRadius = findRadius;
    }

    public int getIter() {
        return iter;
    }

    public int getFindRadius() {
        return findRadius;
    }
}
