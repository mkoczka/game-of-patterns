package com.koczka.gameofpatterns.character;

import com.koczka.gameofpatterns.Entity;
import com.koczka.gameofpatterns.weather.WeatherObserver;

public abstract class Character extends Entity implements WeatherObserver {

    protected int playerVelocity;

}
