package com.parfitt.kalah.model.responses;

import com.parfitt.kalah.model.Game;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class AbstractResponse {

    protected final Game game;

    AbstractResponse(Game game) {
        this.game = game;
    }

    protected String buildLink(long gameId) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
                .replacePath("games/" + gameId)
                .build()
                .toString();
    }

}
