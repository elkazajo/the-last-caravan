package com.elkazajo.lastcaravan.levels.rooms;

import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard.StandardRoom;

public class OpenSteppeRoom extends StandardRoom {

    @Override
    public float[] sizeCatProbs() {
        // Treat this as a large/giant gameplay area.
        return new float[] { 0f, 0f, 1f };
    }
    
    @Override
    public int sizeFactor() {
        return 1;
    }

    @Override
    public int minWidth() {
        return 10;
    }

    @Override
    public int maxWidth() {
        return 18;
    }

    @Override
    public int minHeight() {
        return 10;
    }

    @Override
    public int maxHeight() {
        return 15;
    }

    @Override
    public void paint(Level level) {

        // Solid outer border.
        Painter.fill(level, this, Terrain.WALL);

        // Large open walkable interior.
        Painter.fill(level, this, 1, Terrain.EMPTY);

        // Connections should represent open paths, not dungeon doors.
        for (Door door : connected.values()) {
            door.set(Door.Type.EMPTY);
        }
    }
}