package com.parfitt.kalah.repository;

import com.parfitt.kalah.model.Game;

import java.util.Optional;

public interface GameRepository {
    Game create(Game game);

    Optional<Game> read(Long gameId);

    Game update(Game game);
}
