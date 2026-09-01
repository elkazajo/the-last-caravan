package com.elkazajo.lastcaravan.items;

import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class ScoutKnife extends MeleeWeapon {

    {
        image = ItemSpriteSheet.DAGGER;
        tier = 1;
        bones = false;
    }

    @Override
    public int min(int lvl) {
        return 5 + lvl;
    }

    @Override
    public int max(int lvl) {
        return 8 + 2 * lvl;
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
                "lastcaravan.items.scoutknife.name"
        );
    }

    @Override
    public String desc() {
        return Messages.get(
                "lastcaravan.items.scoutknife.desc"
        );
    }
}