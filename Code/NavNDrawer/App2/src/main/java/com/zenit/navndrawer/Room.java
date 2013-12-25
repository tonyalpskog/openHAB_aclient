package com.zenit.navndrawer;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Tony Alpskog in 2013.
 */
public class Room {
    private int drawableResourceId;
    private HashMap<Direction, Room> roomAlignment;
    private UUID id;

    public Room(int drawableId) {
        drawableResourceId = drawableId;
        id = UUID.randomUUID();
        roomAlignment = new HashMap<Direction, Room>(6);
    }

    public void setAlignment(Room room, Direction alignment) {
        roomAlignment.put(alignment, room);
    }

    public Room getRoomByAlignment(Direction direction) {
        return roomAlignment.get(direction);
    }

    public UUID getId() {
        return id;
    }

    public int getDrawableResourceId() {
        return drawableResourceId;
    }
}
