package com.zenit.dragndrop;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Tony Alpskog on 2013-12-23.
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
