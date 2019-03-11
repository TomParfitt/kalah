package com.parfitt.kalah.service;

import com.parfitt.kalah.model.Game;
import com.parfitt.kalah.model.exceptions.GameNotFoundException;
import com.parfitt.kalah.repository.GameRepository;
import com.parfitt.kalah.rules.Rule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class KalahServiceTest {

    @Captor
    private ArgumentCaptor<Game> gameCaptor1;

    @Captor
    private ArgumentCaptor<Game> gameCaptor2;

    @Captor
    private ArgumentCaptor<Game> gameCaptor3;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private KalahService kalahService;

    private List rules;

    @Mock
    private Rule rule1;

    @Mock
    private Rule rule2;

    @Before
    public void setUp() {
        rules = asList(rule1, rule2);
        kalahService = new KalahService(gameRepository, rules);
    }

    @Test
    public void create_DelegatesCreationOfGameToRepo() {
        // When
        kalahService.create();

        // Then
        then(gameRepository).should().create(gameCaptor1.capture());
        then(gameRepository).shouldHaveNoMoreInteractions();
        then(rule1).shouldHaveZeroInteractions();
        then(rule2).shouldHaveZeroInteractions();


        assertThat(gameCaptor1.getValue()).isNotNull();
    }

    @Test
    public void makeMove_withNotFoundGameId_returnsException() {
        // Given
        given(gameRepository.read(anyLong())).willReturn(Optional.empty());
        long gameId = 1L;

        // When
        assertThatExceptionOfType(GameNotFoundException.class)
                .isThrownBy(() -> kalahService.makeMove(gameId, 1))
                .withNoCause();

        // Then
        then(gameRepository).should().read(gameId);
        then(gameRepository).shouldHaveNoMoreInteractions();
        then(rule1).shouldHaveZeroInteractions();
        then(rule2).shouldHaveZeroInteractions();
    }

    @Test
    public void makeMove_withFoundGameId_runsRulesAndUpdatesGameInRepo() {
        // Given
        long gameId = 1L;
        int pitId = 1;
        Game game = mock(Game.class);
        given(gameRepository.read(anyLong())).willReturn(Optional.of(game));
        given(gameRepository.update(any(Game.class))).willReturn(game);

        // When
        kalahService.makeMove(gameId, pitId);

        // Then
        then(gameRepository).should().read(gameId);
        then(rule1).should().apply(gameCaptor1.capture(), eq(pitId));
        then(rule2).should().apply(gameCaptor2.capture(), eq(pitId));
        then(gameRepository).should().update(gameCaptor3.capture());

        assertThat(gameCaptor1.getValue()).isEqualTo(game);
        assertThat(gameCaptor2.getValue()).isEqualTo(game);
        assertThat(gameCaptor3.getValue()).isEqualTo(game);
    }
}