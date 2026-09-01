package com.elkazajo.lastcaravan.items;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

import java.util.ArrayList;

public class Bandage extends Item {

    public static final String AC_USE = "USE";

    {
        image = ItemSpriteSheet.KIT;
        stackable = true;
        defaultAction = AC_USE;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {

        ArrayList<String> actions =
                super.actions(hero);

        actions.add(0, AC_USE);

        return actions;
    }

    @Override
    public String actionName(
            String action,
            Hero hero
    ) {

        if (AC_USE.equals(action)) {
            return Messages.get(
                    "lastcaravan.items.bandage.ac_use"
            );
        }

        return super.actionName(action, hero);
    }

    @Override
    public void execute(
            Hero hero,
            String action
    ) {

        if (!AC_USE.equals(action)) {
            super.execute(hero, action);
            return;
        }

        if (hero.HP >= hero.HT) {

            GLog.i(
                    Messages.get(
                            "lastcaravan.items.bandage.full_health"
                    )
            );

            return;
        }

        detach(
                hero.belongings.backpack
        );

        hero.HP =
                Math.min(
                        hero.HT,
                        hero.HP + 20
                );

        GLog.p(
                Messages.get(
                        "lastcaravan.items.bandage.used"
                )
        );

        hero.spendAndNext(1f);
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
                "lastcaravan.items.bandage.name"
        );
    }

    @Override
    public String desc() {
        return Messages.get(
                "lastcaravan.items.bandage.desc"
        );
    }
}