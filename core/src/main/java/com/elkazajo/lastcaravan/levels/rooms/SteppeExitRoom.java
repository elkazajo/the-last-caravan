package com.elkazajo.lastcaravan.levels.rooms;

import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.Room;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard.exit.ExitRoom;
import com.watabou.utils.Point;

public class SteppeExitRoom extends ExitRoom {

    @Override
    public int minWidth() {
        return 9;
    }

    @Override
    public int maxWidth() {
        return 12;
    }

    @Override
    public int minHeight() {
        return 9;
    }

    @Override
    public int maxHeight() {
        return 12;
    }

    @Override
    public int maxConnections(int direction) {

        if (direction == Room.ALL) {
            return 1;
        }

        return 1;
    }

    @Override
    public void paint(Level level) {

        Painter.fill(
                level,
                this,
                Terrain.WALL
        );

        Painter.fill(
                level,
                this,
                1,
                Terrain.EMPTY
        );

        for (Door door : connected.values()) {
            door.set(Door.Type.EMPTY);
        }

        Point end = center();

        int exit =
                level.pointToCell(end);

        Painter.set(
                level,
                exit,
                Terrain.EXIT
        );

        level.transitions.add(
                new LevelTransition(
                        level,
                        exit,
                        LevelTransition.Type.REGULAR_EXIT
                )
        );
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