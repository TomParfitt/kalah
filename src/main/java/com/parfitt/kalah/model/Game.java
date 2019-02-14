package com.parfitt.kalah.model;

import java.util.Map;
import lombok.Data;

@Data
public class Game {

    private long id;
    private Player playerTurn;
    private int lastPlacedPitId;
    private Map<Integer, Integer> board;
}
