package com.ssafy.domain;

import lombok.Getter;

@Getter
public class Position {
    private long lastSeconds;

    private final String positionName;

    public Position(String positionName, long lastSeconds) {
        this.positionName = positionName;
        this.lastSeconds = lastSeconds;
    }

    public void updateLastSecond(long time) {
        this.lastSeconds = time;
    }
}
