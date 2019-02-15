package com.parfitt.kalah.rules;

import static com.parfitt.kalah.model.Constants.EMPTY_PIT;
import static com.parfitt.kalah.model.Constants.TOTAL_PITS;

import com.parfitt.kalah.model.Game;
import java.util.Map;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(3)
@Component
public class EmptyWithOppositeRule implements Rule {

    @Override
    public void apply(Game game, int pitId) {
        int lastPlacedPitId = game.getLastPlacedPitId();

        if (isInPlayerPits(game) && isLastPlacedPitEmpty(game)) {

            Map<Integer, Integer> board = game.getBoard();

            int oppositePitId = getOppositePitId(game);
            Integer numInOppositePit = board.get(oppositePitId);

            board.put(lastPlacedPitId, EMPTY_PIT);
            board.put(oppositePitId, EMPTY_PIT);

            int playerHome = game.getPlayerTurn().getHome();
            board.put(playerHome, numInOppositePit + 1);
        }
    }

    private boolean isInPlayerPits(Game game) {
        int lastPlacedPitId = game.getLastPlacedPitId();
        return game.getPlayerTurn().getPits().contains(lastPlacedPitId);
    }

    private boolean isLastPlacedPitEmpty(Game game) {
        Map<Integer, Integer> board = game.getBoard();
        int lastPlacedPitId = game.getLastPlacedPitId();
        return board.get(lastPlacedPitId) == 1;
    }

    private int getOppositePitId(Game game) {
        int halfOfBoard = TOTAL_PITS / 2;
        return game.getLastPlacedPitId() > halfOfBoard ?
                game.getLastPlacedPitId() - halfOfBoard :
                game.getLastPlacedPitId() + halfOfBoard;
    }
}
