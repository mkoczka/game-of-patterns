package com.koczka.gameofpatterns.weather;

import com.koczka.gameofpatterns.character.Player;

public class WeatherManagement {

    WeatherSubject weatherSubject;

    WeatherManagement(Player player) {
        this.weatherSubject = new WeatherSubject();
        new PlayerWeatherWeatherObserver(this.weatherSubject, player);

        this.randomWeatherChange();
    }

    private void randomWeatherChange() {

    }

    private void changeWeather(Weather weather) {
        this.weatherSubject.setState(weather);
    }

}
