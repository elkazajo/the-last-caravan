package com.elkazajo.lastcaravan.items;

import com.elkazajo.lastcaravan.LastCaravanRun;
import com.elkazajo.lastcaravan.scout.ScoutState;
import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class ScoutWater extends Item {

    public static final String AC_DRINK = "DRINK";

    private static final int WATER_RESTORED = 25;

    {
        image = ItemSpriteSheet.WATERSKIN;
        stackable = true;
        defaultAction = AC_DRINK;
    }

    public ScoutWater() {
        this(1);
    }

    public ScoutWater(int amount) {
        quantity = amount;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {

        ArrayList<String> actions = super.actions(hero);
        actions.add(0, AC_DRINK);

        return actions;
    }

    @Override
    public String actionName(String action, Hero hero) {

        if (AC_DRINK.equals(action)) {
            return Messages.get(
                    "lastcaravan.items.scoutwater.ac_drink"
            );
        }

        return super.actionName(action, hero);
    }

    @Override
    public void execute(Hero hero, String action) {

        if (!AC_DRINK.equals(action)) {
            super.execute(hero, action);
            return;
        }

        ScoutState scout = LastCaravanRun.scout();

        if (scout.water() >= scout.maxWater()) {
            GLog.i(
                    Messages.get(
                            "lastcaravan.items.scoutwater.full"
                    )
            );
            return;
        }

        detach(hero.belongings.backpack);
        scout.restoreWater(WATER_RESTORED);

        Sample.INSTANCE.play(Assets.Sounds.DRINK);
        hero.spendAndNext(1f);

        GLog.p(
                Messages.get(
                        "lastcaravan.items.scoutwater.used",
                        scout.water(),
                        scout.maxWater()
                )
        );
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
                "lastcaravan.items.scoutwater.desc",
                WATER_RESTORED
        );
    }
}
