package com.parfitt.kalah.rules;

import com.parfitt.kalah.model.Game;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.parfitt.kalah.model.Constants.EMPTY_PIT;
import static com.parfitt.kalah.model.Constants.FIRST_PIT;
import static com.parfitt.kalah.model.Constants.INIT_PIT_SIZE;
import static com.parfitt.kalah.model.Constants.TOTAL_PITS;
import static com.parfitt.kalah.model.Player.NORTH;
import static com.parfitt.kalah.model.Player.SOUTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MovePipsRuleTest {

    private MovePipsRule rule;

    private Map<Integer, Integer> board;

    @Before
    public void setUp() {
        rule = new MovePipsRule();
        board = IntStream
                .rangeClosed(FIRST_PIT, TOTAL_PITS)
                .boxed()
                .collect(Collectors.toMap(
                        Function.identity(),
                        i -> i == NORTH.getHome() || i == SOUTH.getHome() ? EMPTY_PIT : INIT_PIT_SIZE)
                );
    }

    @Test
    public void apply_withEnoughPipsToReachOwnHome_addsToHome() {
        // Given
        Game game = mock(Game.class);

        given(game.getBoard()).willReturn(board);
        given(game.getPlayerTurn()).willReturn(NORTH);

        // When
        rule.apply(game, 6);


        // Then
        assertThat(board).containsExactly(
                entry(1, INIT_PIT_SIZE),
                entry(2, INIT_PIT_SIZE),
                entry(3, INIT_PIT_SIZE),
                entry(4, INIT_PIT_SIZE),
                entry(5, INIT_PIT_SIZE),
                entry(6, EMPTY_PIT),
                entry(7, 1),
                entry(8, 5),
                entry(9, 5),
                entry(10, 5),
                entry(11, INIT_PIT_SIZE),
                entry(12, INIT_PIT_SIZE),
                entry(13, INIT_PIT_SIZE),
                entry(14, EMPTY_PIT)
        );

    }

    @Test
    public void apply_withEnoughPipsToReachPitPlacedIn_addsToOwnHomeOnly() {
        // Given
        Game game = mock(Game.class);
        board.put(1, 13);
        given(game.getBoard()).willReturn(board);
        given(game.getPlayerTurn()).willReturn(NORTH);

        // When
        rule.apply(game, 1);


        // Then
        assertThat(board).containsExactly(
                entry(1, 1),
                entry(2, 5),
                entry(3, 5),
                entry(4, 5),
                entry(5, 5),
                entry(6, 5),
                entry(7, 1),
                entry(8, 5),
                entry(9, 5),
                entry(10, 5),
                entry(11, 5),
                entry(12, 5),
                entry(13, 5),
                entry(14, EMPTY_PIT)
        );

    }
}