package com.elkazajo.lastcaravan.items;

import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class ScoutWater extends Item {

    {
        image = ItemSpriteSheet.WATERSKIN;
        stackable = true;
    }

    public ScoutWater() {
        this(1);
    }

    public ScoutWater(int amount) {
        quantity = amount;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public String name() {
        return Messages.get(
                "lastcaravan.items.scoutwater.name"
        );
    }

    @Override
    public String desc() {
        return Messages.get(
                "lastcaravan.items.scoutwater.desc"
        );
    }
}