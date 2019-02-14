package com.parfitt.kalah.rules;

import static com.parfitt.kalah.model.Player.NORTH;
import static com.parfitt.kalah.model.Player.SOUTH;

import com.parfitt.kalah.model.Game;
import com.parfitt.kalah.model.Player;
import java.util.Map;
import org.springframework.core.annotation.Order;

@Order(5)
public class EndGameRule implements Rule {

    @Override
    public void apply(Game game, int pitId) {

        int sumOfNorthPits = NORTH.getPits().stream().mapToInt(i -> game.getBoard().get(i)).sum();
        int sumOfSouthPits = SOUTH.getPits().stream().mapToInt(i -> game.getBoard().get(i)).sum();

        if (sumOfNorthPits == 0 || sumOfSouthPits == 0) {
            movePitsIntoHome(game, NORTH, sumOfNorthPits);
            movePitsIntoHome(game, SOUTH, sumOfSouthPits);
        }
    }

    private void movePitsIntoHome(Game game, Player player, int sumOfPipsInPits) {
        if (sumOfPipsInPits != 0) {
            Map<Integer, Integer> board = game.getBoard();

            player.getPits().forEach(pId -> board.put(pId, 0));

            Integer pipsInHome = board.get(player.getHome());
            board.put(player.getHome(), pipsInHome + sumOfPipsInPits);
        }
    }
}
