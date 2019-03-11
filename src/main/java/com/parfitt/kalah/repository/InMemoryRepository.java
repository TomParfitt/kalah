package com.parfitt.kalah.repository;

import com.parfitt.kalah.model.Game;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryRepository implements GameRepository {

    private final ConcurrentMap<Long, Game> repo;
    private final AtomicLong counter = new AtomicLong();

    public InMemoryRepository() {
        this.repo = new ConcurrentHashMap<>();
    }

    @Override
    public Game create(Game game) {
        long gameId = counter.incrementAndGet();
        game.setId(gameId);

        repo.put(gameId, game);

        return game;
    }

    @Override
    public Optional<Game> read(Long gameId) {
        return Optional.of(repo.get(gameId));
    }

    @Override
    public Game update(Game game) {
        repo.replace(game.getId(), game);
        return game;
    }

}
