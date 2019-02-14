package com.parfitt.kalah.repository;

import com.parfitt.kalah.model.Game;
import java.util.Optional;
import java.util.Random;
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

    public Optional<Game> get(String gameId) {
        return Optional.of(repo.get(gameId));
    }

    public Game update(Game game) {
        // TODO what if id is null ?
        repo.replace(game.getId(), game);
        return game;
    }

    public Game create(Game game) {
        Random rand = new Random();

        long gameId = counter.incrementAndGet();
        repo.putIfAbsent(gameId, game);

        game.setId(gameId);

        return game;
    }

}
