package com.elkazajo.lastcaravan.levels.rooms;

import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.Room;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard.StandardRoom;
import com.watabou.utils.Point;
import com.elkazajo.lastcaravan.items.WaterSupplyCache;

public class FarmRoom extends StandardRoom {

    @Override
    public int minWidth() {
        return 12;
    }

    @Override
    public int maxWidth() {
        return 16;
    }

    @Override
    public int minHeight() {
        return 12;
    }

    @Override
    public int maxHeight() {
        return 16;
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

        // Temporary outer boundary while using SPD tiles.
        Painter.fill(level, this, Terrain.WALL);

        // Open farm territory.
        Painter.fill(level, this, 1, Terrain.EMPTY);

        Point center = center();

        paintFields(level, center);

        paintStorageArea(level, center);

        paintMainPaths(level, center);

        paintWell(level);

        for (Door door : connected.values()) {
            door.set(Door.Type.EMPTY);
        }
    }

    private void paintFields(Level level, Point center) {

        int startX = left + 2;
        int endX = center.x - 2;

        int fieldWidth = endX - startX + 1;

        if (fieldWidth <= 0) {
            return;
        }

        for (int y = top + 2; y <= bottom - 2; y += 2) {

            Painter.fill(
                    level,
                    startX,
                    y,
                    fieldWidth,
                    1,
                    Terrain.FURROWED_GRASS);
        }
    }

    private void paintStorageArea(Level level, Point center) {

        int storageLeft = center.x + 2;
        int storageTop = top + 2;

        int storageWidth = right - storageLeft;
        int storageHeight = 4;

        if (storageWidth > 0) {

            Painter.fill(
                    level,
                    storageLeft,
                    storageTop,
                    storageWidth,
                    storageHeight,
                    Terrain.EMPTY_SP);
        }
    }

    private void paintMainPaths(Level level, Point center) {

        // North-south path.
        Painter.fill(
                level,
                center.x - 1,
                top + 1,
                3,
                height() - 2,
                Terrain.EMPTY);

        // West-east path.
        Painter.fill(
                level,
                left + 1,
                center.y - 1,
                width() - 2,
                3,
                Terrain.EMPTY);
    }

    @Override
    public boolean canPlaceGrass(Point point) {
        return false;
    }

    @Override
    public boolean canPlaceWater(Point point) {
        return false;
    }

    private void paintWell(Level level) {

        Point well = new Point(
                right - 3,
                bottom - 3);

        // EMPTY_WELL is only a temporary visual representation.
        // We deliberately do not use SPD's magical WELL mechanics.
        Painter.set(
                level,
                well,
                Terrain.EMPTY_WELL);

        Point supplies = new Point(
                well.x - 1,
                well.y);

        level.drop(
                new WaterSupplyCache(),
                level.pointToCell(supplies));
    }
}