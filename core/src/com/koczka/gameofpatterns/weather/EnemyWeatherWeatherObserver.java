package com.koczka.gameofpatterns.weather;

import com.koczka.gameofpatterns.character.Enemy;

public class EnemyWeatherWeatherObserver extends WeatherObserver {

    private Enemy enemy;

    EnemyWeatherWeatherObserver(WeatherSubject subject, Enemy enemy) {
        this.enemy = enemy;
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        this.enemy.setVelocityByWeather(this.subject.getState());
    }
}
