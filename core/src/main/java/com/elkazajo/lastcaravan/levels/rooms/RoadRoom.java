package com.elkazajo.lastcaravan.levels.rooms;

import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.Room;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.connection.ConnectionRoom;
import com.watabou.utils.Point;

public class RoadRoom extends ConnectionRoom {

    @Override
    public int minWidth() {
        return 8;
    }

    @Override
    public int maxWidth() {
        return 12;
    }

    @Override
    public int minHeight() {
        return 8;
    }

    @Override
    public int maxHeight() {
        return 12;
    }

    @Override
    public int maxConnections(int direction) {

        if (direction == Room.ALL) {
            return 2;
        }

        return 1;
    }

    @Override
    public void paint(Level level) {

        // Temporary outer boundary while we still use SPD terrain.
        Painter.fill(level, this, Terrain.WALL);

        // The area around the road is normal outdoor ground.
        Painter.fill(level, this, 1, Terrain.EMPTY);

        Point hub = new Point(
                (left + right) / 2,
                (top + bottom) / 2);

        paintRoadPatch(level, hub);

        for (Door door : connected.values()) {

            Point start = pointInside(door, 1);

            if (door.x == left || door.x == right) {

                Point middle = new Point(
                        hub.x,
                        start.y);

                paintWideRoad(level, start, middle);
                paintWideRoad(level, middle, hub);

            } else {

                Point middle = new Point(
                        start.x,
                        hub.y);

                paintWideRoad(level, start, middle);
                paintWideRoad(level, middle, hub);
            }

            door.set(Door.Type.EMPTY);
        }
    }

    private void paintRoadPatch(Level level, Point point) {

        for (int x = point.x - 1; x <= point.x + 1; x++) {
            for (int y = point.y - 1; y <= point.y + 1; y++) {

                if (x > left && x < right
                        && y > top && y < bottom) {

                    Painter.set(
                            level,
                            x,
                            y,
                            Terrain.EMPTY_SP);
                }
            }
        }
    }

    private void paintWideRoad(
            Level level,
            Point from,
            Point to) {

        if (from.x == to.x) {

            for (int offset = -1; offset <= 1; offset++) {

                int x = Math.max(
                        left + 1,
                        Math.min(right - 1, from.x + offset));

                Painter.drawLine(
                        level,
                        new Point(x, from.y),
                        new Point(x, to.y),
                        Terrain.EMPTY_SP);
            }

        } else if (from.y == to.y) {

            for (int offset = -1; offset <= 1; offset++) {

                int y = Math.max(
                        top + 1,
                        Math.min(bottom - 1, from.y + offset));

                Painter.drawLine(
                        level,
                        new Point(from.x, y),
                        new Point(to.x, y),
                        Terrain.EMPTY_SP);
            }
        }
    }
}