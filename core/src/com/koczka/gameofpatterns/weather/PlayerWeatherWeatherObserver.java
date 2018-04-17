package com.koczka.gameofpatterns.weather;

import com.koczka.gameofpatterns.character.Player;

public class PlayerWeatherWeatherObserver extends WeatherObserver {

    private Player player;

    PlayerWeatherWeatherObserver(WeatherSubject subject, Player player) {
        this.player = player;
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        this.player.setVelocityByWeather(this.subject.getState());
    }
}
