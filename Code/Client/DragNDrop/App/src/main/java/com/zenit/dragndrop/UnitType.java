package com.zenit.dragndrop;

/**
 * Created by Tony Alpskog in 2013.
 */
public enum UnitType {
    SWITCH(0),
    DIMMER(1),
    ROOM_HEATER(2),
    VENT(3),
    SOCKET(4);

    public final int Value;

    private UnitType(int value) {
        Value = value;
    }
}
