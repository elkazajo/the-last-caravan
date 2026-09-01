package com.elkazajo.lastcaravan.items;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.elkazajo.lastcaravan.levels.SteppeLevel;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;

public class WaterSupplyCache extends Item {

    {
        // Temporary icon. Later we will replace it with our own canister sprite.
        image = ItemSpriteSheet.WATERSKIN;
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
                "lastcaravan.items.watersupplycache.name");
    }

    @Override
    public String desc() {
        return Messages.get(
                "lastcaravan.items.watersupplycache.desc");
    }

    @Override
    public boolean doPickUp(Hero hero, int pos) {

        boolean pickedUp = super.doPickUp(hero, pos);

        if (pickedUp) {

            GLog.p(
                    Messages.get(
                            "lastcaravan.items.watersupplycache.collected"));

            if (Dungeon.level instanceof SteppeLevel) {

                ((SteppeLevel) Dungeon.level)
                        .completeWaterObjective();
            }
        }

        return pickedUp;
    }
}