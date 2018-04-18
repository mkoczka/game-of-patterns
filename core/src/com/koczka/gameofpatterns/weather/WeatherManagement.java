package com.koczka.gameofpatterns.weather;

import com.koczka.gameofpatterns.GameState;

import java.util.Timer;
import java.util.TimerTask;

public class WeatherManagement {

    WeatherSubject weatherSubject;

    public WeatherManagement() {
        this.weatherSubject = new WeatherSubject();

        this.randomWeatherChange();
    }

    private void randomWeatherChange() {
        changeWeather();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                changeWeather();
            }
        }, 5000, 5000);
    }

    private void changeWeather() {
        Weather weather = Weather.SUN;
        int randomNum = (int) (Math.random() * 2) + 1;
        if (randomNum == 1) weather = Weather.SNOW;
        if (randomNum == 2) weather = Weather.RAIN;
        GameState.getInstance().setWeather(weather);
        this.weatherSubject.setState(weather);
    }

}
