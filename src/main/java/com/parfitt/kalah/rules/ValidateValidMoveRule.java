package com.parfitt.kalah.rules;

import static com.parfitt.kalah.model.Constants.EMPTY_PIT;
import static com.parfitt.kalah.model.Constants.FIRST_PIT;
import static com.parfitt.kalah.model.Constants.TOTAL_PITS;

import com.parfitt.kalah.model.Game;
import com.parfitt.kalah.model.exceptions.IncorrectPitIdSelectedException;
import org.springframework.core.annotation.Order;

@Order(1)
public class ValidateValidMoveRule implements Rule {

    @Override
    public void apply(Game game, int pitId) {
        if (isValidPitId(pitId)) {
            throw new IncorrectPitIdSelectedException("Invalid pitId, must be between " + FIRST_PIT + " and " + TOTAL_PITS);
        }

        if (isPlayerPit(game, pitId)) {
            throw new IncorrectPitIdSelectedException("Incorrect player pitId");
        }

        if (isNoPipsInPit(game, pitId)) {
            throw new IncorrectPitIdSelectedException("No pips in pit");
        }
    }

    private boolean isValidPitId(int pitId) {
        return FIRST_PIT <= pitId && pitId <= TOTAL_PITS;
    }

    private boolean isPlayerPit(Game game, int pitId) {
        return game.getPlayerTurn().getPits().contains(pitId);
    }

    private boolean isNoPipsInPit(Game game, int pitId) {
        return game.getBoard().get(pitId) == EMPTY_PIT;
    }

}
