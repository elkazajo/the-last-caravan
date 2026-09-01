package com.elkazajo.lastcaravan.levels.rooms;

import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.connection.ConnectionRoom;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class RoadRoom extends ConnectionRoom {

    private final boolean horizontal;

    public RoadRoom() {
        horizontal = Random.Int(2) == 0;
    }

    @Override
    public int minWidth() {
        return horizontal ? 10 : 5;
    }

    @Override
    public int maxWidth() {
        return horizontal ? 16 : 7;
    }

    @Override
    public int minHeight() {
        return horizontal ? 5 : 10;
    }

    @Override
    public int maxHeight() {
        return horizontal ? 7 : 16;
    }

    @Override
    public void paint(Level level) {

        Painter.fill(level, this, Terrain.WALL);

        // Temporary road surface.
        // EMPTY_SP gives us a different floor type while we still use SPD textures.
        Painter.fill(level, this, 1, Terrain.EMPTY_SP);

        for (Door door : connected.values()) {
            door.set(Door.Type.EMPTY);
        }
    }

    @Override
    public boolean canPlaceGrass(Point point) {
        return false;
    }

    @Override
    public boolean canPlaceWater(Point point) {
        return false;
    }
}