package com.elkazajo.lastcaravan.levels.painters;

import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.RegularPainter;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.Room;
import com.watabou.utils.Rect;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Temporary painter for the first LAST CARAVAN steppe prototype.
 *
 * It still uses SPD terrain and textures, but removes dungeon-style
 * doors and widens connections between generated areas.
 */
public class SteppePainter extends RegularPainter {

    @Override
    protected void paintDoors(Level level, ArrayList<Room> rooms) {

        // Let SPD calculate all valid room connections first.
        super.paintDoors(level, rooms);

        Set<Room.Door> processedDoors = new HashSet<>();

        for (Room room : rooms) {

            for (Room other : room.connected.keySet()) {

                Room.Door door = room.connected.get(other);

                if (door == null || !processedDoors.add(door)) {
                    continue;
                }

                // Outdoor areas should not look like rooms connected by doors.
                door.type = Room.Door.Type.EMPTY;
                Painter.set(level, door, Terrain.EMPTY);

                widenConnection(level, room, other, door);
            }
        }
    }

    private void widenConnection(
            Level level,
            Room first,
            Room second,
            Room.Door door
    ) {

        Rect intersection = first.intersect(second);

        // Rooms touch along a vertical edge.
        if (intersection.width() == 0) {

            int minY = Math.max(intersection.top + 1, door.y - 1);
            int maxY = Math.min(intersection.bottom - 1, door.y + 1);

            for (int y = minY; y <= maxY; y++) {
                Painter.set(level, door.x, y, Terrain.EMPTY);
            }

        // Rooms touch along a horizontal edge.
        } else if (intersection.height() == 0) {

            int minX = Math.max(intersection.left + 1, door.x - 1);
            int maxX = Math.min(intersection.right - 1, door.x + 1);

            for (int x = minX; x <= maxX; x++) {
                Painter.set(level, x, door.y, Terrain.EMPTY);
            }
        }
    }

    @Override
    protected void decorate(Level level, ArrayList<Room> rooms) {
        // Intentionally empty for now.
        // Steppe-specific terrain decoration will be added later.
    }
}