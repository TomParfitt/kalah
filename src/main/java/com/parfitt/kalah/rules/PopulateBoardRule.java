package com.parfitt.kalah.rules;

import static com.parfitt.kalah.model.Constants.EMPTY_PIT;
import static com.parfitt.kalah.model.Constants.FIRST_PIT;
import static com.parfitt.kalah.model.Constants.INIT_PIT_SIZE;
import static com.parfitt.kalah.model.Constants.TOTAL_PITS;
import static com.parfitt.kalah.model.Player.NORTH;
import static com.parfitt.kalah.model.Player.SOUTH;

import com.parfitt.kalah.model.Game;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(0)
@Component
public class PopulateBoardRule implements Rule {

    @Override
    public void apply(Game game, int pitId) {
        if (game.getBoard() == null) {
            populateBoard(game);
            game.setPlayerTurn(NORTH);
        }
    }

    private void populateBoard(Game game) {
        Map<Integer, Integer> board = IntStream
                .rangeClosed(FIRST_PIT, TOTAL_PITS)
                .boxed()
                .collect(Collectors.toMap(
                        Function.identity(),
                        i -> i == NORTH.getHome() || i == SOUTH.getHome() ? EMPTY_PIT : INIT_PIT_SIZE)
                );
        game.setBoard(board);
    }
}
