package com.elkazajo.lastcaravan.levels;

import com.elkazajo.lastcaravan.levels.painters.SteppePainter;
import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.levels.RegularLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;

public class SteppeLevel extends RegularLevel {

    {
        color1 = 0xC7A96B;
        color2 = 0x8F7545;
    }

    @Override
    protected int standardRooms(boolean forceMax) {
        return 5;
    }

    @Override
    protected int specialRooms(boolean forceMax) {
        return 0;
    }

    @Override
    protected int nTraps() {
        return 0;
    }

    @Override
    protected Painter painter() {
        return new SteppePainter()
                .setWater(0.05f, 2)
                .setGrass(0.65f, 6);
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_SEWERS;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_SEWERS;
    }
}