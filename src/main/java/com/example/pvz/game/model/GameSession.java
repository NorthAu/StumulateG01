package com.example.pvz.game.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameSession {
    private final String id = UUID.randomUUID().toString();
    private final GameMode mode;
    private final Instant createdAt = Instant.now();
    private int currentLevel;
    private boolean completed;
    private final List<String> notes = new ArrayList<>();

    public GameSession(GameMode mode, int startingLevel) {
        this.mode = mode;
        this.currentLevel = startingLevel;
    }

    public String getId() {
        return id;
    }

    public GameMode getMode() {
        return mode;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void advanceLevel() {
        this.currentLevel++;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        this.completed = true;
    }

    public List<String> getNotes() {
        return notes;
    }
}
