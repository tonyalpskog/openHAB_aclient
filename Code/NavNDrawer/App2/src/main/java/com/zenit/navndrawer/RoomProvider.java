package com.zenit.navndrawer;

import android.content.Context;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Tony Alpskog in 2013.
 */
public class RoomProvider {
    HashMap<UUID, Room> roomHash;
    UUID initialRoomId;

    public RoomProvider(Context context) {
        roomHash = new HashMap<UUID, Room>();
        createRooms();
    }

    public boolean add(Room room) {
        roomHash.put(room.getId(), room);
        return true;
    }

    public Room get(UUID uuid) {
        return roomHash.get(uuid);
    }

    public Room getInitialRoom() {
        return get(initialRoomId);
    }

    private void createRooms() {
        Room room0Center = new Room(R.drawable.room_0_c);
        add(room0Center);
        Room room0East = new Room(R.drawable.room_0_e);
        add(room0East);
        Room room0North = new Room(R.drawable.room_0_n);
        add(room0North);
        Room room0NorthEast = new Room(R.drawable.room_0_ne);
        add(room0NorthEast);
        Room room0NorthWest = new Room(R.drawable.room_0_nw);
        add(room0NorthWest);
        Room room0South = new Room(R.drawable.room_0_s);
        add(room0South);
        Room room0SouthEast = new Room(R.drawable.room_0_se);
        add(room0SouthEast);
        Room room0SouthWest = new Room(R.drawable.room_0_sw);
        add(room0SouthWest);
        Room room0West = new Room(R.drawable.room_0_w);
        add(room0West);

        Room room1Center = new Room(R.drawable.room_1_c);
        add(room1Center);
        Room room1East = new Room(R.drawable.room_1_e);
        add(room1East);
        Room room1North = new Room(R.drawable.room_1_n);
        add(room1North);
        Room room1NorthEast = new Room(R.drawable.room_1_ne);
        add(room1NorthEast);
        Room room1NorthWest = new Room(R.drawable.room_1_nw);
        add(room1NorthWest);
        Room room1South = new Room(R.drawable.room_1_s);
        add(room1South);
        Room room1SouthEast = new Room(R.drawable.room_1_se);
        add(room1SouthEast);
        Room room1SouthWest = new Room(R.drawable.room_1_sw);
        add(room1SouthWest);
        Room room1West = new Room(R.drawable.room_1_w);
        add(room1West);

        Room room2Center = new Room(R.drawable.room_2_c);
        add(room2Center);
        Room room2East = new Room(R.drawable.room_2_e);
        add(room2East);
        Room room2North = new Room(R.drawable.room_2_n);
        add(room2North);
        Room room2NorthEast = new Room(R.drawable.room_2_ne);
        add(room2NorthEast);
        Room room2NorthWest = new Room(R.drawable.room_2_nw);
        add(room2NorthWest);
        Room room2South = new Room(R.drawable.room_2_s);
        add(room2South);
        Room room2SouthEast = new Room(R.drawable.room_2_se);
        add(room2SouthEast);
        Room room2SouthWest = new Room(R.drawable.room_2_sw);
        add(room2SouthWest);
        Room room2West = new Room(R.drawable.room_2_w);
        add(room2West);

        initialRoomId = room0Center.getId();

        room0Center.setAlignment(room0West, Direction.LEFT);
        room0Center.setAlignment(room0East, Direction.RIGHT);
        room0Center.setAlignment(room0North, Direction.UP);
        room0Center.setAlignment(room0South, Direction.DOWN);
        room0Center.setAlignment(room1Center, Direction.ABOVE);

        room0East.setAlignment(room0Center, Direction.LEFT);
        room0East.setAlignment(room0NorthEast, Direction.UP);
        room0East.setAlignment(room0SouthEast, Direction.DOWN);
        room0East.setAlignment(room1East, Direction.ABOVE);

        room0SouthEast.setAlignment(room0South, Direction.LEFT);
        room0SouthEast.setAlignment(room0East, Direction.UP);
        room0SouthEast.setAlignment(room1SouthEast, Direction.ABOVE);

        room0NorthEast.setAlignment(room0North, Direction.LEFT);
        room0NorthEast.setAlignment(room0East, Direction.DOWN);
        room0NorthEast.setAlignment(room1NorthEast, Direction.ABOVE);

        room0North.setAlignment(room0NorthWest, Direction.LEFT);
        room0North.setAlignment(room0NorthEast, Direction.RIGHT);
        room0North.setAlignment(room0Center, Direction.DOWN);
        room0North.setAlignment(room1North, Direction.ABOVE);

        room0NorthWest.setAlignment(room0North, Direction.RIGHT);
        room0NorthWest.setAlignment(room0West, Direction.DOWN);
        room0NorthWest.setAlignment(room1NorthWest, Direction.ABOVE);

        room0West.setAlignment(room0Center, Direction.RIGHT);
        room0West.setAlignment(room0NorthWest, Direction.UP);
        room0West.setAlignment(room0SouthWest, Direction.DOWN);
        room0West.setAlignment(room1West, Direction.ABOVE);

        room0SouthWest.setAlignment(room0South, Direction.RIGHT);
        room0SouthWest.setAlignment(room0West, Direction.UP);
        room0SouthWest.setAlignment(room1SouthWest, Direction.ABOVE);

        room0South.setAlignment(room0SouthWest, Direction.LEFT);
        room0South.setAlignment(room0SouthEast, Direction.RIGHT);
        room0South.setAlignment(room0Center, Direction.UP);
        room0South.setAlignment(room1South, Direction.ABOVE);







//        room0Center.setAlignment(room0East, Direction.LEFT);
//        room0Center.setAlignment(room0East, Direction.RIGHT);
//        room0Center.setAlignment(room0East, Direction.UP);
//        room0Center.setAlignment(room0East, Direction.DOWN);
//        room0Center.setAlignment(room0East, Direction.BELOW);
//        room0Center.setAlignment(room0East, Direction.ABOVE);
//        for(int i = 1; i < 28; i++) {
//            Room room
//            add(new Room())
//        }
    }
}
