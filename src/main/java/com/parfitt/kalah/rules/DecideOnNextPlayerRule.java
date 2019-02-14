package com.parfitt.kalah.rules;

import com.parfitt.kalah.model.Game;
import com.parfitt.kalah.model.Player;
import org.springframework.core.annotation.Order;

@Order(4)
public class DecideOnNextPlayerRule implements Rule {

    @Override
    public void apply(Game game, int pitId) {

        if (isNotPlacedInHome(game)) {
            Player oppositePlayer = game.getPlayerTurn().getOppositePlayer();
            game.setPlayerTurn(oppositePlayer);
        }
    }

    private boolean isNotPlacedInHome(Game game) {
        int lastPlacedPitId = game.getLastPlacedPitId();
        return game.getPlayerTurn().getHome() != lastPlacedPitId;
    }
}
