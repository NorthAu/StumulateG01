package com.example.pvz.game.model;

import java.util.List;

public record Level(int stage, String environment, List<Zombie> zombies) {
}
