package com.parfitt.kalah.rules;

import com.parfitt.kalah.model.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import static com.parfitt.kalah.model.Constants.EMPTY_PIT;
import static com.parfitt.kalah.model.Constants.INIT_PIT_SIZE;
import static com.parfitt.kalah.model.Player.NORTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PopulateBoardRuleTest {

    @Captor
    private ArgumentCaptor<Map<Integer, Integer>> boardCaptor;

    private PopulateBoardRule rule;

    @Before
    public void setUp() {
        rule = new PopulateBoardRule();
    }

    @Test
    public void apply_withBoard_doesNothing() {
        // Given
        Game game = mock(Game.class);

        // When
        rule.apply(game, 0);

        // Then
        then(game).should().getBoard();
        then(game).shouldHaveNoMoreInteractions();
    }

    @Test
    public void apply_withNoBoard_populatesGame() {
        // Given
        Game game = mock(Game.class);
        given(game.getBoard()).willReturn(null);

        // When
        rule.apply(game, 0);

        // Then
        verify(game).setBoard(boardCaptor.capture());
        verify(game).setPlayerTurn(NORTH);
        Map<Integer, Integer> board = boardCaptor.getValue();
        assertThat(board).isNotNull();
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
}