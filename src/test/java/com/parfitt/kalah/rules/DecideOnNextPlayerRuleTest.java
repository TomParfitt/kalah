package com.parfitt.kalah.rules;

import com.parfitt.kalah.model.Game;
import org.junit.Before;
import org.junit.Test;

import static com.parfitt.kalah.model.Player.NORTH;
import static com.parfitt.kalah.model.Player.SOUTH;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class DecideOnNextPlayerRuleTest {

    private DecideOnNextPlayerRule rule;

    @Before
    public void setUp() {
        rule = new DecideOnNextPlayerRule();
    }

    @Test
    public void apply_withLastPitInHome_doesNotChangePlayerTurn() {
        // Given
        Game game = mock(Game.class);
        given(game.getLastPlacedPitId()).willReturn(NORTH.getHome());
        given(game.getPlayerTurn()).willReturn(NORTH);

        // When
        rule.apply(game, 0);

        // Then
        then(game).should().getLastPlacedPitId();
        then(game).should().getPlayerTurn();
        then(game).shouldHaveNoMoreInteractions();
    }

    @Test
    public void apply_withLastPitNotInHome_changesPlayerTurn() {
        // Given
        Game game = mock(Game.class);
        given(game.getLastPlacedPitId()).willReturn(3);
        given(game.getPlayerTurn()).willReturn(NORTH);

        // When
        rule.apply(game, 0);

        // Then
        then(game).should().getLastPlacedPitId();
        then(game).should(times(2)).getPlayerTurn();
        then(game).should().setPlayerTurn(SOUTH);
        then(game).shouldHaveNoMoreInteractions();
    }
}