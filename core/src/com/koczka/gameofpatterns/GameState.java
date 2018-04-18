package com.koczka.gameofpatterns;

import com.koczka.gameofpatterns.weather.Weather;

public class GameState {

    private static GameState instance;

    private int score = 0;

    private Weather weather;

    private GameState() {
    }

    public static GameState getInstance() {
        if (GameState.instance == null) {
            GameState.instance = new GameState();
        }
        return GameState.instance;
    }

    public void addScore() {
        this.score += 1;
    }

    public int getScore() {
        return this.score;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Weather getWeather() {
        return weather;
    }
}
