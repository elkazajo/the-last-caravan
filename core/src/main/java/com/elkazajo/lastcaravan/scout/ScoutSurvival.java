package com.elkazajo.lastcaravan.scout;

import com.elkazajo.lastcaravan.LastCaravanRun;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public final class ScoutSurvival implements Hero.Doom {

    private static final float IMPAIRED_MULTIPLIER = 0.85f;

    private static final ScoutSurvival INSTANCE = new ScoutSurvival();

    private ScoutSurvival() {
    }

    public static void spendTime(
            Hero hero,
            ScoutState scout,
            float time) {

        ScoutState.Hydration before = scout.hydration();

        scout.spendExpeditionTime(time);

        ScoutState.Hydration after = scout.hydration();

        if (after.ordinal() > before.ordinal()) {
            warn(after);
        }

        if (before == ScoutState.Hydration.DEHYDRATED) {

            int damage = scout.spendDehydratedTime(time);

            if (damage > 0) {
                GLog.n(
                        Messages.get(
                                "lastcaravan.scout.survival.damage"
                        )
                );

                hero.damage(damage, INSTANCE);

                if (hero.isAlive()) {
                    hero.interrupt();
                }
            }
        }
    }

    public static float speedMultiplier() {
        return isImpaired() ? IMPAIRED_MULTIPLIER : 1f;
    }

    public static float accuracyMultiplier() {
        return isImpaired() ? IMPAIRED_MULTIPLIER : 1f;
    }

    private static boolean isImpaired() {
        return LastCaravanRun.phase() == LastCaravanRun.Phase.EXPEDITION
                && LastCaravanRun.scout().hydration().isImpaired();
    }

    private static void warn(ScoutState.Hydration hydration) {

        String message;

        switch (hydration) {
            case THIRSTY:
                message = "lastcaravan.scout.survival.thirsty";
                break;
            case CRITICAL:
                message = "lastcaravan.scout.survival.critical";
                break;
            case DEHYDRATED:
                message = "lastcaravan.scout.survival.dehydrated";
                break;
            default:
                return;
        }

        GLog.w(Messages.get(message));
    }

    @Override
    public void onDeath() {
        Dungeon.fail(this);
        GLog.n(
                Messages.get(
                        "lastcaravan.scout.survival.death"
                )
        );
    }
}
