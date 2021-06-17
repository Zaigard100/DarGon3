package com.zaig100.dg.utils.contain;

import com.zaig100.dg.elements.items.Item;
import com.zaig100.dg.elements.items.Key;
import com.zaig100.dg.elements.items.Poition;
import com.zaig100.dg.elements.items.Sheld;
import com.zaig100.dg.elements.items.Torch;

public class ItemC extends ObjC {
    Item item;
    boolean active;

    public ItemC(int x, int y, String name, boolean active, String tag) {
        super(x, y, tag);
        switch (name) {
            case "poition":
                item = new Poition(1);
                break;
            case "sheld":
                item = new Sheld(1);
                break;
            case "torch":
                item = new Torch(1);
                break;
            default:
                if (name.split(":")[0].equals("key")) {
                    item = new Key(
                            name.split(":")[1].split(",")[0],
                            Float.parseFloat(name.split(":")[1].split(",")[1]),
                            Float.parseFloat(name.split(":")[1].split(",")[2]),
                            Float.parseFloat(name.split(":")[1].split(",")[3])
                    );
                }
                break;
        }
        this.active = active;
    }

    public Item getItem() {
        return item;
    }

    public boolean isActive() {
        return active;
    }
}
