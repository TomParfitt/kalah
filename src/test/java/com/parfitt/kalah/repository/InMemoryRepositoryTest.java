package com.parfitt.kalah.repository;

import com.parfitt.kalah.model.Game;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static com.parfitt.kalah.model.Player.NORTH;
import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryRepositoryTest {

    private GameRepository gameRepository;

    @Before
    public void setup() {
        gameRepository = new InMemoryRepository();
    }

    @Test
    public void create_withGame_willReturnGameWithId() {
        // Given
        Game game = new Game();

        // When
        Game createdGame = gameRepository.create(game);

        // Then
        assertThat(createdGame).extracting(Game::getId).isNotNull();
    }

    @Test
    public void read_withGameExistingForId_returnsGame() {
        // Given
        Game game = new Game();
        Game createdGame = gameRepository.create(game);

        // When
        Optional<Game> readGame = gameRepository.read(createdGame.getId());

        // Then
        assertThat(readGame).get().isEqualTo(createdGame);
    }

    @Test
    public void update_withNewGame_returnsUpdatedGameAndSavesToRepo() {
        // Given
        Game game = new Game();
        Game createdGame = gameRepository.create(game);
        Game updatedGame = new Game();
        updatedGame.setPlayerTurn(NORTH);
        updatedGame.setId(createdGame.getId());

        // When
        Game returnedUpdatedGame = gameRepository.update(updatedGame);

        // Then
        assertThat(returnedUpdatedGame).isEqualTo(updatedGame);
        Optional<Game> readGame = gameRepository.read(returnedUpdatedGame.getId());
        assertThat(readGame).get().isEqualTo(returnedUpdatedGame);

    }
}