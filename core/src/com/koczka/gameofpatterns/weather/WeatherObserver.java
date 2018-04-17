package com.koczka.gameofpatterns.weather;

import com.koczka.gameofpatterns.weather.WeatherSubject;

public abstract class WeatherObserver {
    protected WeatherSubject subject;
    public abstract void update();
}