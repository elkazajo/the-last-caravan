package com.elkazajo.lastcaravan.levels.rooms;

import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.Room;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard.exit.ExitRoom;
import com.watabou.utils.Point;

/**
 * Technical end point used by the SPD LineBuilder.
 *
 * It is intentionally not a real dungeon exit:
 * there is no staircase and no LevelTransition.
 */
public class RouteEndRoom extends ExitRoom {

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

        // Temporary visual landmark at the far end of the route.
        // Later this can become a lookout, abandoned vehicle, signpost, etc.
        Point marker = center();

        Painter.fill(
                level,
                marker.x - 1,
                marker.y - 1,
                3,
                3,
                Terrain.EMPTY_SP
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