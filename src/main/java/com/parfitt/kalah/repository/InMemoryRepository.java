package com.parfitt.kalah.repository;

import com.parfitt.kalah.model.Game;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryRepository {

    private final ConcurrentMap<Long, Game> repo;
    private final AtomicLong counter = new AtomicLong();

    public InMemoryRepository() {
        this.repo = new ConcurrentHashMap<>();
    }

    public Game create(Game game) {
        long gameId = counter.incrementAndGet();
        game.setId(gameId);

        repo.put(gameId, game);

        return game;
    }

    public Optional<Game> read(String gameId) {
        return Optional.of(repo.get(gameId));
    }

    public Game update(Game game) {
        repo.replace(game.getId(), game);
        return game;
    }

}
