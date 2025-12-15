package com.example.pvz.game.api;

import com.example.pvz.game.model.GameSession;
import com.example.pvz.game.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/mainline/start")
    public GameSession startMainline() {
        return gameService.startMainlineCampaign();
    }

    @PostMapping("/mainline/{sessionId}/progress")
    public GameSession progressMainline(@PathVariable String sessionId) {
        return gameService.progressMainline(sessionId);
    }

    @PostMapping("/minigame/start")
    public GameSession startMiniGame(@RequestBody Map<String, String> payload) {
        String name = payload.getOrDefault("name", "Fog Runner");
        return gameService.startMiniGame(name);
    }

    @PostMapping("/endless/start")
    public GameSession startEndless() {
        return gameService.startEndless();
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<GameSession> getSession(@PathVariable String sessionId) {
        return gameService.getSession(sessionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/plants")
    public Object listPlants() {
        return gameService.getPlants();
    }

    @GetMapping("/levels")
    public Object listLevels() {
        return gameService.getMainlineLevels();
    }

    @GetMapping("/minigames")
    public Object listMiniGames() {
        return gameService.getMiniGames();
    }
}
