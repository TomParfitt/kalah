package com.parfitt.kalah.service;

import com.parfitt.kalah.model.Game;
import com.parfitt.kalah.model.exceptions.GameNotFoundException;
import com.parfitt.kalah.repository.InMemoryRepository;
import com.parfitt.kalah.rules.Rule;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KalahService {

    private final InMemoryRepository inMemoryRepository;
    private final List<Rule> rules;

    @Autowired
    public KalahService(InMemoryRepository inMemoryRepository, List<Rule> rules) {
        this.inMemoryRepository = inMemoryRepository;
        this.rules = rules;
    }

    public Game makeMove(String gameId, int pitId) {
        Game game = inMemoryRepository.get(gameId).orElseThrow(GameNotFoundException::new);

        rules.forEach(r -> r.apply(game, pitId));

        return update(game);
    }

    public Game create() {
        return inMemoryRepository.create(new Game());
    }

    private Game update(Game game) {
        return inMemoryRepository.update(game);
    }

}
