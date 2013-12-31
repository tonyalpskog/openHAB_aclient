package com.zenit.habclient;

import android.app.Application;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Tony Alpskog in 2013.
 */
public class HABApplication extends Application {
    private HashMap<UUID, HashMap<UUID, GraphicUnit>> roomUnitList = new HashMap<UUID, HashMap<UUID, GraphicUnit>>();
    RoomProvider roomProvider = null;
    UUID currentConfigRoom = null;
    UUID currentFlipperRoom = null;

    public Room getConfigRoom() {
        Room room = getRoom(currentConfigRoom);
        currentConfigRoom = room.getId();
        return room;
    }

    public void setConfigRoom(Room room) {
        currentConfigRoom = room.getId();
    }

    public Room getFlipperRoom() {
        Room room = getRoom(currentFlipperRoom);
        currentFlipperRoom = room.getId();
        return room;
    }

    public void setFlipperRoom(Room room) {
        currentFlipperRoom = room.getId();
    }

    private Room getRoom(UUID roomId) {
        if(roomProvider == null)
            roomProvider = new RoomProvider(getApplicationContext());

        if(roomId == null)
            return roomProvider.getInitialRoom();
        else
            return roomProvider.get(roomId);
    }
}