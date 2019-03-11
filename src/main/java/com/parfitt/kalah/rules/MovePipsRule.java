package com.parfitt.kalah.rules;

import static com.parfitt.kalah.model.Constants.EMPTY_PIT;
import static com.parfitt.kalah.model.Constants.FIRST_PIT;
import static com.parfitt.kalah.model.Constants.TOTAL_PITS;
import static com.parfitt.kalah.model.Player.NORTH;
import static com.parfitt.kalah.model.Player.SOUTH;

import com.parfitt.kalah.model.Game;
import java.util.Map;

import com.parfitt.kalah.model.Player;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class MovePipsRule implements Rule {

    @Override
    public void apply(final Game game, int pitId) {
        Map<Integer, Integer> board = game.getBoard();

        Integer numOfPips = board.get(pitId);
        board.put(pitId, EMPTY_PIT);

        int nextPitId = pitId;
        for (int i = 0; i < numOfPips; i++) {
            nextPitId = getNextValidPitId(game, nextPitId);
            int nextPitValue = board.get(nextPitId) + 1;
            board.put(nextPitId, nextPitValue);
            game.setLastPlacedPitId(nextPitId);
        }
    }

    private int getNextValidPitId(final Game game, final int pitId) {
        int nextPit = incrementPitId(pitId);
        Player oppositePlayer = game.getPlayerTurn().getOppositePlayer();
        return nextPit == oppositePlayer.getHome() ? incrementPitId(nextPit) : nextPit;
    }

    private int incrementPitId(int pitId) {
        int nextPitId = pitId + 1;
        return nextPitId > TOTAL_PITS ? FIRST_PIT : nextPitId;
    }

}
