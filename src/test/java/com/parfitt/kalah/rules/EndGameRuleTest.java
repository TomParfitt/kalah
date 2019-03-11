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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class EndGameRuleTest {

    private EndGameRule rule;

    @Before
    public void setUp() {
        rule = new EndGameRule();
    }

    @Test
    public void apply_withPipsInBothSides_doesNothing() {
        // Given
        Game game = mock(Game.class);
        Map<Integer, Integer> board = IntStream
                .rangeClosed(FIRST_PIT, TOTAL_PITS)
                .boxed()
                .collect(Collectors.toMap(
                        Function.identity(),
                        i -> i == NORTH.getHome() || i == SOUTH.getHome() ? EMPTY_PIT : INIT_PIT_SIZE)
                );
        given(game.getBoard()).willReturn(board);

        // When
        rule.apply(game, 0);

        // Then
        assertThat(board).containsExactly(
                entry(1, INIT_PIT_SIZE),
                entry(2, INIT_PIT_SIZE),
                entry(3, INIT_PIT_SIZE),
                entry(4, INIT_PIT_SIZE),
                entry(5, INIT_PIT_SIZE),
                entry(6, INIT_PIT_SIZE),
                entry(7, EMPTY_PIT),
                entry(8, INIT_PIT_SIZE),
                entry(9, INIT_PIT_SIZE),
                entry(10, INIT_PIT_SIZE),
                entry(11, INIT_PIT_SIZE),
                entry(12, INIT_PIT_SIZE),
                entry(13, INIT_PIT_SIZE),
                entry(14, EMPTY_PIT)
        );
    }

    @Test
    public void apply_withPipsInOnlyNorthSide_addsToNorthHome() {
        // Given
        Game game = mock(Game.class);
        Map<Integer, Integer> board = IntStream
                .rangeClosed(FIRST_PIT, TOTAL_PITS)
                .boxed()
                .collect(Collectors.toMap(
                        Function.identity(),
                        i -> i == NORTH.getHome() || i == SOUTH.getHome() || SOUTH.getPits().contains(i)
                                ? EMPTY_PIT : INIT_PIT_SIZE)
                );
        given(game.getBoard()).willReturn(board);

        // When
        rule.apply(game, 0);

        // Then
        assertThat(board).containsExactly(
                entry(1, EMPTY_PIT),
                entry(2, EMPTY_PIT),
                entry(3, EMPTY_PIT),
                entry(4, EMPTY_PIT),
                entry(5, EMPTY_PIT),
                entry(6, EMPTY_PIT),
                entry(7, 24),
                entry(8, EMPTY_PIT),
                entry(9, EMPTY_PIT),
                entry(10, EMPTY_PIT),
                entry(11, EMPTY_PIT),
                entry(12, EMPTY_PIT),
                entry(13, EMPTY_PIT),
                entry(14, EMPTY_PIT)
        );
    }

    @Test
    public void apply_withPipsInOnlySouthSide_addsToSouthHome() {
    // Given
        Game game = mock(Game.class);
        Map<Integer, Integer> board = IntStream
                .rangeClosed(FIRST_PIT, TOTAL_PITS)
                .boxed()
                .collect(Collectors.toMap(
                        Function.identity(),
                        i -> i == NORTH.getHome() || i == SOUTH.getHome() || NORTH.getPits().contains(i)
                                ? EMPTY_PIT : INIT_PIT_SIZE)
                );
        given(game.getBoard()).willReturn(board);

        // When
        rule.apply(game, 0);

        // Then
        assertThat(board).containsExactly(
                entry(1, EMPTY_PIT),
                entry(2, EMPTY_PIT),
                entry(3, EMPTY_PIT),
                entry(4, EMPTY_PIT),
                entry(5, EMPTY_PIT),
                entry(6, EMPTY_PIT),
                entry(7, EMPTY_PIT),
                entry(8, EMPTY_PIT),
                entry(9, EMPTY_PIT),
                entry(10, EMPTY_PIT),
                entry(11, EMPTY_PIT),
                entry(12, EMPTY_PIT),
                entry(13, EMPTY_PIT),
                entry(14, 24)
        );
    }
}