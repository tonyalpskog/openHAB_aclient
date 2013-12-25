package com.zenit.navndrawer;

import android.app.Application;

import com.zenit.dragndrop.GraphicUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Tony Alpskog in 2013.
 */
public class HABApplication extends Application {
    private List<HashMap<UUID, GraphicUnit>> roomUnitList = new ArrayList<HashMap<UUID, GraphicUnit>>(3);

    public HashMap<UUID, GraphicUnit> getUnitHash(int room) {
        if(roomUnitList.size() == 0)
            initRoomList();

        return roomUnitList.get(room);
    }

    private void initRoomList() {
        roomUnitList.add(0, new HashMap<UUID, GraphicUnit>());
        roomUnitList.add(1, new HashMap<UUID, GraphicUnit>());
        roomUnitList.add(2, new HashMap<UUID, GraphicUnit>());
    }
}
