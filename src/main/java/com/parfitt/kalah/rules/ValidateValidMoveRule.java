package com.parfitt.kalah.rules;

import static com.parfitt.kalah.model.Constants.EMPTY_PIT;
import static com.parfitt.kalah.model.Constants.FIRST_PIT;
import static com.parfitt.kalah.model.Constants.TOTAL_PITS;

import com.parfitt.kalah.model.Game;
import com.parfitt.kalah.model.exceptions.IncorrectPitIdSelectedException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class ValidateValidMoveRule implements Rule {

    @Override
    public void apply(Game game, int pitId) {
        if (isInvalidPitId(pitId)) {
            throw new IncorrectPitIdSelectedException("Invalid pitId, must be between " + FIRST_PIT + " and " + TOTAL_PITS);
        }

        if (isNotPlayerPit(game, pitId)) {
            throw new IncorrectPitIdSelectedException("Incorrect player pitId");
        }

        if (isNoPipsInPit(game, pitId)) {
            throw new IncorrectPitIdSelectedException("No pips in pit");
        }
    }

    private boolean isInvalidPitId(int pitId) {
        return pitId < FIRST_PIT || TOTAL_PITS < pitId;
    }

    private boolean isNotPlayerPit(Game game, int pitId) {
        return !game.getPlayerTurn().getPits().contains(pitId);
    }

    private boolean isNoPipsInPit(Game game, int pitId) {
        return game.getBoard().get(pitId) == EMPTY_PIT;
    }

}
