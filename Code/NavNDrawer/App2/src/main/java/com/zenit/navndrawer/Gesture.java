package com.zenit.navndrawer;

/**
 * Created by Tony Alpskog on 2013-12-21.
 */
public enum Gesture {
    SWIPE_UP(0),
    SWIPE_DOWN(1),
    SWIPE_LEFT(2),
    SWIPE_RIGHT(3),
    PINCH_IN(4),
    PINCH_OUT(5);

    public final int Value;

    private Gesture(int value) {
        Value = value;
    }
}
