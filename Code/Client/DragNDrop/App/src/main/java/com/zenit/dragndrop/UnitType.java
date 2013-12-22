package com.zenit.dragndrop;

/**
 * Created by Tony Alpskog on 2013-12-21.
 */
public enum UnitType {
    SWITCH(1),
    DIMMER(2),
    ROOM_HEATER(3),
    VENT(4),
    SOCKET(5);

    public final int Value;

    private UnitType(int value) {
        Value = value;
    }
}
