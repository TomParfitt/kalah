package com.parfitt.kalah.controller;

import com.parfitt.kalah.model.Game;
import com.parfitt.kalah.model.responses.CreateGameResponseAdapter;
import com.parfitt.kalah.model.responses.MakeMoveResponseAdapter;
import com.parfitt.kalah.service.KalahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class KalahController {

    @Autowired
    private final KalahService kalahService;

    public KalahController(KalahService kalahService) {
        this.kalahService = kalahService;
    }

    @PostMapping
    public ResponseEntity<CreateGameResponseAdapter> createGame() {
        Game game = kalahService.create();
        CreateGameResponseAdapter createGameResponse = new CreateGameResponseAdapter(game);
        return new ResponseEntity(createGameResponse, HttpStatus.CREATED);
    }

    @PutMapping("{gameId}/pits/{pitId}")
    public ResponseEntity<MakeMoveResponseAdapter> makeMove(@PathVariable Long gameId, @PathVariable int pitId) {
        Game game = kalahService.makeMove(gameId, pitId);
        MakeMoveResponseAdapter makeMoveResponseAdapter = new MakeMoveResponseAdapter(game);
        return new ResponseEntity(makeMoveResponseAdapter, HttpStatus.OK);
    }
}
