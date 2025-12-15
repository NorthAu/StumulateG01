package com.example.pvz.game.service;

import com.example.pvz.game.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class GameService {

    private static final int MAINLINE_MAX = 10;
    private final Map<String, GameSession> sessions = new ConcurrentHashMap<>();
    private final List<Level> mainlineLevels;
    private final List<String> miniGames;
    private final List<Plant> plants;

    public GameService() {
        this.plants = buildPlantCatalog();
        this.mainlineLevels = buildMainlineLevels();
        this.miniGames = List.of(
                "Fog Runner",
                "Zen Garden Rush",
                "Triple Lane Bowling",
                "Rooftop Survival",
                "Wall-nut Target Practice"
        );
    }

    public GameSession startMainlineCampaign() {
        GameSession session = new GameSession(GameMode.MAINLINE, 1);
        session.getNotes().add("Complete all " + MAINLINE_MAX + " stages to win.");
        sessions.put(session.getId(), session);
        return session;
    }

    public GameSession startMiniGame(String miniGameName) {
        if (!miniGames.contains(miniGameName)) {
            throw new IllegalArgumentException("Unknown mini-game: " + miniGameName);
        }
        GameSession session = new GameSession(GameMode.MINI_GAME, 1);
        session.getNotes().add("Playing mini game: " + miniGameName);
        sessions.put(session.getId(), session);
        return session;
    }

    public GameSession startEndless() {
        GameSession session = new GameSession(GameMode.ENDLESS, 1);
        session.getNotes().add("Endless mode never ends, but difficulty scales.");
        sessions.put(session.getId(), session);
        return session;
    }

    public Optional<GameSession> getSession(String sessionId) {
        return Optional.ofNullable(sessions.get(sessionId));
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public List<String> getMiniGames() {
        return miniGames;
    }

    public List<Level> getMainlineLevels() {
        return mainlineLevels;
    }

    public GameSession progressMainline(String sessionId) {
        GameSession session = sessions.get(sessionId);
        if (session == null) {
            throw new NoSuchElementException("Session not found");
        }
        if (session.getMode() != GameMode.MAINLINE) {
            throw new IllegalStateException("Session is not mainline");
        }
        if (session.isCompleted()) {
            return session;
        }
        if (session.getCurrentLevel() >= MAINLINE_MAX) {
            session.markCompleted();
            session.getNotes().add("Mainline complete! Heroes of the lawn.");
            return session;
        }
        session.advanceLevel();
        session.getNotes().add("Advanced to level " + session.getCurrentLevel());
        return session;
    }

    private List<Plant> buildPlantCatalog() {
        Map<PlantType, Plant> plantMap = new EnumMap<>(PlantType.class);
        plantMap.put(PlantType.SUNFLOWER, new Plant(PlantType.SUNFLOWER, 50, 0, 5));
        plantMap.put(PlantType.PEASHOOTER, new Plant(PlantType.PEASHOOTER, 100, 20, 5));
        plantMap.put(PlantType.REPEATER, new Plant(PlantType.REPEATER, 150, 30, 6));
        plantMap.put(PlantType.SNOW_PEA, new Plant(PlantType.SNOW_PEA, 175, 25, 7));
        plantMap.put(PlantType.WALL_NUT, new Plant(PlantType.WALL_NUT, 50, 0, 15));
        plantMap.put(PlantType.TALL_NUT, new Plant(PlantType.TALL_NUT, 125, 0, 20));
        plantMap.put(PlantType.CHERRY_BOMB, new Plant(PlantType.CHERRY_BOMB, 150, 90, 25));
        plantMap.put(PlantType.CHOMPER, new Plant(PlantType.CHOMPER, 150, 90, 15));
        plantMap.put(PlantType.SQUASH, new Plant(PlantType.SQUASH, 50, 60, 15));
        plantMap.put(PlantType.THREE_PEATER, new Plant(PlantType.THREE_PEATER, 225, 25, 8));
        plantMap.put(PlantType.SPLIT_PEA, new Plant(PlantType.SPLIT_PEA, 125, 20, 7));
        plantMap.put(PlantType.STARFRUIT, new Plant(PlantType.STARFRUIT, 125, 25, 7));
        plantMap.put(PlantType.CACTUS, new Plant(PlantType.CACTUS, 125, 20, 8));
        plantMap.put(PlantType.JALAPENO, new Plant(PlantType.JALAPENO, 125, 90, 25));
        plantMap.put(PlantType.PUMPKIN, new Plant(PlantType.PUMPKIN, 125, 0, 20));
        plantMap.put(PlantType.COB_CANNON, new Plant(PlantType.COB_CANNON, 500, 120, 30));
        plantMap.put(PlantType.MAGNET_SHROOM, new Plant(PlantType.MAGNET_SHROOM, 100, 0, 12));
        return plantMap.values().stream().sorted(Comparator.comparing(p -> p.type().name())).collect(Collectors.toList());
    }

    private List<Level> buildMainlineLevels() {
        List<Level> levels = new ArrayList<>();
        for (int i = 1; i <= MAINLINE_MAX; i++) {
            levels.add(new Level(i, i <= 5 ? "Day Garden" : "Night Roof", buildZombieWave(i)));
        }
        return levels;
    }

    private List<Zombie> buildZombieWave(int difficulty) {
        List<Zombie> zombies = new ArrayList<>();
        zombies.add(new Zombie("Walker", 50 + difficulty * 5, 1, 10));
        zombies.add(new Zombie("Conehead", 80 + difficulty * 7, 1, 15));
        if (difficulty > 3) {
            zombies.add(new Zombie("Buckethead", 120 + difficulty * 8, 1, 20));
        }
        if (difficulty > 6) {
            zombies.add(new Zombie("Pole Vaulter", 100 + difficulty * 10, 2, 25));
        }
        if (difficulty > 8) {
            zombies.add(new Zombie("Gargantuar", 300 + difficulty * 20, 1, 50));
        }
        return zombies;
    }
}
