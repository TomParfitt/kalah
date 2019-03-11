package com.parfitt.kalah.model.responses;

import com.parfitt.kalah.model.Game;
import java.util.Map;

public class MakeMoveResponseAdapter extends AbstractResponse {

    public MakeMoveResponseAdapter(Game game) {
        super(game);
    }

    public long getId() {
        return game.getId();
    }

    public String getUrl() {
        return buildLink(game.getId());
    }

    public Map<Integer, Integer> getStatus() {
        return game.getBoard();
    }
}
