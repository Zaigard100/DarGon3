package com.zaig100.dg.utils.contain;

public class DoorC extends ObjC {

    String keyTag;
    boolean isDoorOpen;
    int faicing;


    public DoorC(int x, int y, String keyTag, boolean isDoorOpen, int faicing, String tag) {
        super(x, y, tag);
        this.keyTag = keyTag;
        this.isDoorOpen = isDoorOpen;
        this.faicing = faicing;
    }

    public String getKeyTag() {
        return keyTag;
    }

    public boolean isDoorOpen() {
        return isDoorOpen;
    }

    public int getFaicing() {
        return faicing;
    }
}
