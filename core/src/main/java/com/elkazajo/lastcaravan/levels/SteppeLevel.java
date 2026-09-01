package com.elkazajo.lastcaravan.levels;

import com.elkazajo.lastcaravan.levels.painters.SteppePainter;
import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.levels.RegularLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.elkazajo.lastcaravan.levels.rooms.OpenSteppeRoom;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.Room;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard.entrance.EntranceRoom;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard.exit.ExitRoom;
import com.elkazajo.lastcaravan.levels.rooms.RoadRoom;

import java.util.ArrayList;

public class SteppeLevel extends RegularLevel {

    {
        color1 = 0xC7A96B;
        color2 = 0x8F7545;
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

    @Override
    protected ArrayList<Room> initRooms() {

        ArrayList<Room> rooms = new ArrayList<>();

        rooms.add(roomEntrance = EntranceRoom.createEntrance());

        rooms.add(new OpenSteppeRoom());
        rooms.add(new OpenSteppeRoom());
        rooms.add(new OpenSteppeRoom());

        rooms.add(new RoadRoom());
        rooms.add(new RoadRoom());

        rooms.add(roomExit = ExitRoom.createExit());

        return rooms;
    }
}