package com.parfitt.kalah.model;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Getter;

@Getter
public enum Player {

    NORTH(7),
    SOUTH(14);

    private final int home;
    private final Set<Integer> pits;

    Player(int home) {
        this.home = home;
        this.pits = IntStream.rangeClosed(home - 6, home - 1).boxed().collect(Collectors.toSet());
    }

    public Player getOppositePlayer() {
        return this == NORTH ? SOUTH : NORTH;
    }
}