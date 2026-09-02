package com.elkazajo.lastcaravan.items;

import com.elkazajo.lastcaravan.noise.NoiseSystem;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class ScoutKnife extends MeleeWeapon {

    private static final int ATTACK_NOISE_RADIUS = 0;

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
    public int proc(Char attacker, Char defender, int damage) {
        NoiseSystem.emit(attacker.pos, ATTACK_NOISE_RADIUS);
        return super.proc(attacker, defender, damage);
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
