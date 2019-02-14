package com.parfitt.kalah.rules;

import static com.parfitt.kalah.model.Constants.FIRST_PIT;
import static com.parfitt.kalah.model.Constants.TOTAL_PITS;
import static com.parfitt.kalah.model.Player.NORTH;
import static com.parfitt.kalah.model.Player.SOUTH;

import com.parfitt.kalah.model.Game;
import java.util.Map;
import org.springframework.core.annotation.Order;

@Order(2)
public class MovePipsRule implements Rule {

    @Override
    public void apply(final Game game, int pitId) {
        Map<Integer, Integer> board = game.getBoard();

        Integer numOfPips = board.get(pitId);

        for (int i = 0; i < numOfPips; i++) {
            int nextPitId = getNextValidPitId(pitId);
            int nextPitValue = board.get(nextPitId) + 1;
            board.put(nextPitId, nextPitValue);
            game.setLastPlacedPitId(nextPitId);
        }
    }

    private int getNextValidPitId(final int pitId) {
        int nextPit = incrementPitId(pitId);
        return nextPit == NORTH.getHome() || nextPit == SOUTH.getHome() ? incrementPitId(nextPit) : nextPit;
    }

    private int incrementPitId(int pitId) {
        int nextPitId = pitId + 1;
        return nextPitId > TOTAL_PITS ? FIRST_PIT : nextPitId;
    }

}
