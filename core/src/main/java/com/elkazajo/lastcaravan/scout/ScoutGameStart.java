package com.elkazajo.lastcaravan.scout;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;

public final class ScoutGameStart {

    private ScoutGameStart() {
    }

    public static void startNewGame(int slot) {

        GamesInProgress.curSlot = slot;

        // Temporary technical compatibility with SPD.
        // The player no longer chooses an SPD class.
        GamesInProgress.selectedClass =
                HeroClass.WARRIOR;

        Dungeon.hero = null;

        Dungeon.daily = false;
        Dungeon.dailyReplay = false;

        // LAST CARAVAN currently does not use SPD challenges
        // or custom seeds.
        SPDSettings.challenges(0);
        SPDSettings.customSeed("");

        Dungeon.initSeed();

        ActionIndicator.clearAction();

        InterlevelScene.mode =
                InterlevelScene.Mode.DESCEND;

        ShatteredPixelDungeon.switchScene(
                InterlevelScene.class
        );
    }
}