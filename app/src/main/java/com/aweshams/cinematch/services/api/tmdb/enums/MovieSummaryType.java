package com.aweshams.cinematch.services.api.tmdb.enums;

public enum MovieSummaryType {
    NOW_PLAYING ("now_playing"),
    TOP_RATED("top_rated"),
    UPCOMING("upcoming");

    private final String name;

    MovieSummaryType(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }

}
