package com.zenit.navndrawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Tony Alpskog in 2013.
 */
public class RoomProvider {
    HashMap<UUID, Room> roomHash;
    UUID initialRoomId;
    private Context mContext = null;

    public RoomProvider(Context context) {
        mContext = context;
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
        return roomHash.get(initialRoomId);
    }

    private Bitmap getBitmap(int bitmapResourceId) {
        return BitmapFactory.decodeResource(mContext.getResources(), bitmapResourceId);
    }

    private void createRooms() {
        Room room0Center = new Room(getBitmap(R.drawable.room_0_c));
        add(room0Center);
        Room room0East = new Room(getBitmap(R.drawable.room_0_e));
        add(room0East);
        Room room0North = new Room(getBitmap(R.drawable.room_0_n));
        add(room0North);
        Room room0NorthEast = new Room(getBitmap(R.drawable.room_0_ne));
        add(room0NorthEast);
        Room room0NorthWest = new Room(getBitmap(R.drawable.room_0_nw));
        add(room0NorthWest);
        Room room0South = new Room(getBitmap(R.drawable.room_0_s));
        add(room0South);
        Room room0SouthEast = new Room(getBitmap(R.drawable.room_0_se));
        add(room0SouthEast);
        Room room0SouthWest = new Room(getBitmap(R.drawable.room_0_sw));
        add(room0SouthWest);
        Room room0West = new Room(getBitmap(R.drawable.room_0_w));
        add(room0West);

        Room room1Center = new Room(getBitmap(R.drawable.room_1_c));
        add(room1Center);
        Room room1East = new Room(getBitmap(R.drawable.room_1_e));
        add(room1East);
        Room room1North = new Room(getBitmap(R.drawable.room_1_n));
        add(room1North);
        Room room1NorthEast = new Room(getBitmap(R.drawable.room_1_ne));
        add(room1NorthEast);
        Room room1NorthWest = new Room(getBitmap(R.drawable.room_1_nw));
        add(room1NorthWest);
        Room room1South = new Room(getBitmap(R.drawable.room_1_s));
        add(room1South);
        Room room1SouthEast = new Room(getBitmap(R.drawable.room_1_se));
        add(room1SouthEast);
        Room room1SouthWest = new Room(getBitmap(R.drawable.room_1_sw));
        add(room1SouthWest);
        Room room1West = new Room(getBitmap(R.drawable.room_1_w));
        add(room1West);

        Room room2Center = new Room(getBitmap(R.drawable.room_2_c));
        add(room2Center);
        Room room2East = new Room(getBitmap(R.drawable.room_2_e));
        add(room2East);
        Room room2North = new Room(getBitmap(R.drawable.room_2_n));
        add(room2North);
        Room room2NorthEast = new Room(getBitmap(R.drawable.room_2_ne));
        add(room2NorthEast);
        Room room2NorthWest = new Room(getBitmap(R.drawable.room_2_nw));
        add(room2NorthWest);
        Room room2South = new Room(getBitmap(R.drawable.room_2_s));
        add(room2South);
        Room room2SouthEast = new Room(getBitmap(R.drawable.room_2_se));
        add(room2SouthEast);
        Room room2SouthWest = new Room(getBitmap(R.drawable.room_2_sw));
        add(room2SouthWest);
        Room room2West = new Room(getBitmap(R.drawable.room_2_w));
        add(room2West);

        initialRoomId = room0Center.getId();

        //Aligning basement
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

        //Aligning ground level
        room1Center.setAlignment(room1West, Direction.LEFT);
        room1Center.setAlignment(room1East, Direction.RIGHT);
        room1Center.setAlignment(room1North, Direction.UP);
        room1Center.setAlignment(room1South, Direction.DOWN);
        room1Center.setAlignment(room0Center, Direction.BELOW);
        room1Center.setAlignment(room2Center, Direction.ABOVE);

        room1North.setAlignment(room1NorthWest, Direction.LEFT);
        room1North.setAlignment(room1NorthEast, Direction.RIGHT);
        room1North.setAlignment(room1Center, Direction.DOWN);
        room1North.setAlignment(room0North, Direction.BELOW);
        room1North.setAlignment(room2North, Direction.ABOVE);

        room1NorthWest.setAlignment(room1North, Direction.RIGHT);
        room1NorthWest.setAlignment(room1West, Direction.DOWN);
        room1NorthWest.setAlignment(room0NorthWest, Direction.BELOW);
        room1NorthWest.setAlignment(room2NorthWest, Direction.ABOVE);

        room1West.setAlignment(room1Center, Direction.RIGHT);
        room1West.setAlignment(room1NorthWest, Direction.UP);
        room1West.setAlignment(room1SouthWest, Direction.DOWN);
        room1West.setAlignment(room0West, Direction.BELOW);
        room1West.setAlignment(room2West, Direction.ABOVE);

        room1SouthWest.setAlignment(room1South, Direction.RIGHT);
        room1SouthWest.setAlignment(room1West, Direction.UP);
        room1SouthWest.setAlignment(room0SouthWest, Direction.BELOW);
        room1SouthWest.setAlignment(room2SouthWest, Direction.ABOVE);

        room1South.setAlignment(room1SouthWest, Direction.LEFT);
        room1South.setAlignment(room1SouthEast, Direction.RIGHT);
        room1South.setAlignment(room1Center, Direction.UP);
        room1South.setAlignment(room0South, Direction.BELOW);
        room1South.setAlignment(room2South, Direction.ABOVE);

        room1SouthEast.setAlignment(room1South, Direction.LEFT);
        room1SouthEast.setAlignment(room1East, Direction.UP);
        room1SouthEast.setAlignment(room0SouthEast, Direction.BELOW);
        room1SouthEast.setAlignment(room2SouthEast, Direction.ABOVE);

        room1East.setAlignment(room1Center, Direction.LEFT);
        room1East.setAlignment(room1NorthEast, Direction.UP);
        room1East.setAlignment(room1SouthEast, Direction.DOWN);
        room1East.setAlignment(room0East, Direction.BELOW);
        room1East.setAlignment(room2East, Direction.ABOVE);

        room1NorthEast.setAlignment(room1North, Direction.LEFT);
        room1NorthEast.setAlignment(room1East, Direction.DOWN);
        room1NorthEast.setAlignment(room0NorthEast, Direction.BELOW);
        room1NorthEast.setAlignment(room2NorthEast, Direction.ABOVE);

        //Alignment upper level
        room2Center.setAlignment(room2West, Direction.LEFT);
        room2Center.setAlignment(room2East, Direction.RIGHT);
        room2Center.setAlignment(room2North, Direction.UP);
        room2Center.setAlignment(room2South, Direction.DOWN);
        room2Center.setAlignment(room1Center, Direction.BELOW);

        room2North.setAlignment(room2NorthWest, Direction.LEFT);
        room2North.setAlignment(room2NorthEast, Direction.RIGHT);
        room2North.setAlignment(room2Center, Direction.DOWN);
        room2North.setAlignment(room1North, Direction.BELOW);

        room2NorthWest.setAlignment(room2North, Direction.RIGHT);
        room2NorthWest.setAlignment(room2West, Direction.DOWN);
        room2NorthWest.setAlignment(room1NorthWest, Direction.BELOW);

        room2West.setAlignment(room2Center, Direction.RIGHT);
        room2West.setAlignment(room2NorthWest, Direction.UP);
        room2West.setAlignment(room2SouthWest, Direction.DOWN);
        room2West.setAlignment(room1West, Direction.BELOW);

        room2SouthWest.setAlignment(room2South, Direction.RIGHT);
        room2SouthWest.setAlignment(room2West, Direction.UP);
        room2SouthWest.setAlignment(room1SouthWest, Direction.BELOW);

        room2South.setAlignment(room2SouthWest, Direction.LEFT);
        room2South.setAlignment(room2SouthEast, Direction.RIGHT);
        room2South.setAlignment(room2Center, Direction.UP);
        room2South.setAlignment(room1South, Direction.BELOW);

        room2SouthEast.setAlignment(room2South, Direction.LEFT);
        room2SouthEast.setAlignment(room2East, Direction.UP);
        room2SouthEast.setAlignment(room1SouthEast, Direction.BELOW);

        room2East.setAlignment(room2Center, Direction.LEFT);
        room2East.setAlignment(room2NorthEast, Direction.UP);
        room2East.setAlignment(room2SouthEast, Direction.DOWN);
        room2East.setAlignment(room1East, Direction.BELOW);

        room2NorthEast.setAlignment(room2North, Direction.LEFT);
        room2NorthEast.setAlignment(room2East, Direction.DOWN);
        room2NorthEast.setAlignment(room1NorthEast, Direction.BELOW);
    }
}
