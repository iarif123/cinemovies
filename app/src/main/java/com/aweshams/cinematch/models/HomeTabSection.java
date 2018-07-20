package com.aweshams.cinematch.models;

/**
 * Created by irteza on 2018-05-19.
 */

public enum HomeTabSection implements ActionTabSection {
    UNKNOWN(-1),
    NOW_PLAYIING(0);

    private int value;

    HomeTabSection(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
