package com.parfitt.kalah.model.responses;

import com.parfitt.kalah.model.Game;

public class CreateGameResponseAdapter extends AbstractResponse  {

    public CreateGameResponseAdapter(Game game) {
        super(game);
    }

    public long getId() {
        return game.getId();
    }

    public String getUri() {
        return buildLink(game.getId());
    }
}
