package com.parfitt.kalah.rules;

import com.parfitt.kalah.model.Game;

public interface Rule {

    void apply(Game game, int pitId);

}
