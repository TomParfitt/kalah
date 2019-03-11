package com.parfitt.kalah.repository;

import com.parfitt.kalah.model.Game;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static com.parfitt.kalah.model.Player.NORTH;
import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryRepositoryTest {

    private InMemoryRepository inMemoryRepository;

    @Before
    public void setup() {
        inMemoryRepository = new InMemoryRepository();
    }

    @Test
    public void create_withGame_willReturnGameWithId() {
        // Given
        Game game = new Game();

        // When
        Game createdGame = inMemoryRepository.create(game);

        // Then
        assertThat(createdGame).extracting(Game::getId).isNotNull();
    }

    @Test
    public void read_withGameExistingForId_returnsGame() {
        // Given
        Game game = new Game();
        Game createdGame = inMemoryRepository.create(game);

        // When
        Optional<Game> readGame = inMemoryRepository.read(createdGame.getId());

        // Then
        assertThat(readGame).get().isEqualTo(createdGame);
    }

    @Test
    public void update_withNewGame_returnsUpdatedGameAndSavesToRepo() {
        // Given
        Game game = new Game();
        Game createdGame = inMemoryRepository.create(game);
        Game updatedGame = new Game();
        updatedGame.setPlayerTurn(NORTH);
        updatedGame.setId(createdGame.getId());

        // When
        Game returnedUpdatedGame = inMemoryRepository.update(updatedGame);

        // Then
        assertThat(returnedUpdatedGame).isEqualTo(updatedGame);
        Optional<Game> readGame = inMemoryRepository.read(returnedUpdatedGame.getId());
        assertThat(readGame).get().isEqualTo(returnedUpdatedGame);

    }
}