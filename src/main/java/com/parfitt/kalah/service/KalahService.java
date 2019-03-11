package com.parfitt.kalah.service;

import com.parfitt.kalah.model.Game;
import com.parfitt.kalah.model.exceptions.GameNotFoundException;
import com.parfitt.kalah.repository.GameRepository;
import com.parfitt.kalah.rules.Rule;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KalahService {

    private final GameRepository gameRepository;
    private final List<Rule> rules;

    @Autowired
    public KalahService(GameRepository gameRepository, List<Rule> rules) {
        this.gameRepository = gameRepository;
        this.rules = rules;
    }

    public Game create() {
        return gameRepository.create(new Game());
    }

    public Game makeMove(Long gameId, int pitId) {
        Game game = gameRepository.read(gameId).orElseThrow(GameNotFoundException::new);

        rules.forEach(r -> r.apply(game, pitId));

        return gameRepository.update(game);
    }

}
