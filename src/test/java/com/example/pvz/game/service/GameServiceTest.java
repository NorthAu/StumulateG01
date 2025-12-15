package com.example.pvz.game.service;

import com.example.pvz.game.model.GameMode;
import com.example.pvz.game.model.GameSession;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameServiceTest {

    private final GameService gameService = new GameService();

    @Test
    void hasAtLeastFifteenPlants() {
        assertThat(gameService.getPlants()).hasSizeGreaterThanOrEqualTo(15);
    }

    @Test
    void mainlineHasTenLevels() {
        assertThat(gameService.getMainlineLevels()).hasSizeGreaterThanOrEqualTo(10);
    }

    @Test
    void canProgressMainline() {
        GameSession session = gameService.startMainlineCampaign();
        assertThat(session.getMode()).isEqualTo(GameMode.MAINLINE);
        gameService.progressMainline(session.getId());
        assertThat(gameService.getSession(session.getId()).orElseThrow().getCurrentLevel()).isEqualTo(2);
    }
}
