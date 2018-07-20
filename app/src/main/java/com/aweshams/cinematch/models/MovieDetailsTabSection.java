package com.aweshams.cinematch.models;

/**
 * Created by irteza on 2018-05-19.
 */

public enum MovieDetailsTabSection implements ActionTabSection {
    UNKNOWN(-1),
    INFO(0),
    CAST(1),
    REVIEWS(2);

    private int value;

    MovieDetailsTabSection(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
