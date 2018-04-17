package com.koczka.gameofpatterns;

public class GameState {

    private static GameState instance;

    private int score = 0;

    private GameState() {
    }

    public static GameState getInstance() {
        if (GameState.instance == null) {
            GameState.instance = new GameState();
        }
        return GameState.instance;
    }

    protected void addScore() {
        this.score += 1;
    }

    protected int getScore() {
        return this.score;
    }

}
