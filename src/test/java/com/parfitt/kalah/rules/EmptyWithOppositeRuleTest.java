package com.parfitt.kalah.rules;

import com.parfitt.kalah.model.Game;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

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
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EmptyWithOppositeRuleTest {

    private EmptyWithOppositeRule rule;

    @Before
    public void setUp() {
        rule = new EmptyWithOppositeRule();
    }

    @Test
    public void apply_withPitIdNotInPlayerPits_doesNothing() {
        // Given
        Game game = mock(Game.class);
        given(game.getLastPlacedPitId()).willReturn(8);
        given(game.getPlayerTurn()).willReturn(NORTH);

        // When
        rule.apply(game, 0);

        // Then
        then(game).should().getLastPlacedPitId();
        then(game).should().getPlayerTurn();
        then(game).shouldHaveNoMoreInteractions();
    }

    @Test
    public void apply_withPitIdInPlayerPitsButLastPlacedPitNotEmpty_doesNothing() {
        // Given
        Game game = mock(Game.class);
        int lastPlacedPitId = 3;
        given(game.getLastPlacedPitId()).willReturn(lastPlacedPitId);
        given(game.getPlayerTurn()).willReturn(NORTH);
        Map<Integer, Integer> board = mock(Map.class);
        given(board.get(lastPlacedPitId)).willReturn(2);
        given(game.getBoard()).willReturn(board);

        // When
        rule.apply(game, 0);

        // Then
        InOrder inOrder = inOrder(game, board);
        then(game).should(inOrder).getLastPlacedPitId();
        then(game).should(inOrder).getPlayerTurn();
        then(game).should(inOrder).getBoard();
        then(game).should(inOrder).getLastPlacedPitId();
        then(board).should(inOrder).get(lastPlacedPitId);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void apply_withPitIdInPlayerPitsAndLastPlacedPitEmpty_MovesToHome() {
        // Given
        Game game = mock(Game.class);
        int lastPlacedPitId = 3;
        given(game.getLastPlacedPitId()).willReturn(lastPlacedPitId);
        given(game.getPlayerTurn()).willReturn(NORTH);
        Map<Integer, Integer> board = mock(Map.class);
        given(board.get(lastPlacedPitId)).willReturn(1);
        given(board.get(11)).willReturn(4);
        given(game.getBoard()).willReturn(board);
        given(board.put(anyInt(), anyInt())).willReturn(null);

        // When
        rule.apply(game, 0);

        // Then
        verify(board).put(lastPlacedPitId, 0);
        verify(board).put(11, 0);
        verify(board).put(NORTH.getHome(), 5);
    }
}