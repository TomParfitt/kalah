package com.parfitt.kalah.rules;

import com.parfitt.kalah.model.Game;
import com.parfitt.kalah.model.exceptions.IncorrectPitIdSelectedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
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
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class ValidateValidMoveRuleTest {

    private ValidateValidMoveRule rule;

    private Map<Integer, Integer> board;

    @Before
    public void setUp() {
        rule = new ValidateValidMoveRule();
        board = IntStream
                .rangeClosed(FIRST_PIT, TOTAL_PITS)
                .boxed()
                .collect(Collectors.toMap(
                        Function.identity(),
                        i -> i == NORTH.getHome() || i == SOUTH.getHome() ? EMPTY_PIT : INIT_PIT_SIZE)
                );
    }

    @Test
    public void apply_withValidPitId_doesNotThrowException() {
        // Given
        Game game = mock(Game.class);
        given(game.getPlayerTurn()).willReturn(NORTH);
        given(game.getBoard()).willReturn(board);

        // When
        rule.apply(game, 2);

        // Then
        then(game).should().getPlayerTurn();
        then(game).should().getBoard();
        then(game).shouldHaveNoMoreInteractions();
    }

    @Test
    public void apply_withInvalidPitIdTooLow_returnsException() {
        // Given
        Game game = mock(Game.class);

        // When
        assertThatExceptionOfType(IncorrectPitIdSelectedException.class)
                .isThrownBy(() -> rule.apply(game, 0))
                .withNoCause()
                .withMessage("Invalid pitId, must be between 1 and 14");

        // Then
        then(game).shouldHaveNoMoreInteractions();
    }

    @Test
    public void apply_withInvalidPitIdTooHigh_returnsException() {
        // Given
        Game game = mock(Game.class);

        // When
        assertThatExceptionOfType(IncorrectPitIdSelectedException.class)
                .isThrownBy(() -> rule.apply(game, 15))
                .withNoCause()
                .withMessage("Invalid pitId, must be between 1 and 14");

        // Then
        then(game).shouldHaveNoMoreInteractions();
    }

    @Test
    public void apply_withNotPlayerPit_returnsException() {
        // Given
        Game game = mock(Game.class);
        given(game.getPlayerTurn()).willReturn(NORTH);

        // When
        assertThatExceptionOfType(IncorrectPitIdSelectedException.class)
                .isThrownBy(() -> rule.apply(game, 8))
                .withNoCause()
                .withMessage("Incorrect player pitId");

        // Then
        then(game).should().getPlayerTurn();
        then(game).shouldHaveNoMoreInteractions();
    }

    @Test
    public void apply_withNoPipsInPit_returnsException() {
        // Given
        Game game = mock(Game.class);
        given(game.getPlayerTurn()).willReturn(NORTH);
        int pitId = 3;
        board.put(pitId, EMPTY_PIT);
        given(game.getBoard()).willReturn(board);

        // When
        assertThatExceptionOfType(IncorrectPitIdSelectedException.class)
                .isThrownBy(() -> rule.apply(game, pitId))
                .withNoCause()
                .withMessage("No pips in pit");

        // Then
        then(game).should().getPlayerTurn();
        then(game).should().getBoard();
        then(game).shouldHaveNoMoreInteractions();
    }
}