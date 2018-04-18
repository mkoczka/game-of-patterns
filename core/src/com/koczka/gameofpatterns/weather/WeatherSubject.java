package com.koczka.gameofpatterns.weather;

import java.util.ArrayList;
import java.util.List;

public class WeatherSubject {

    private List<WeatherObserver> observers = new ArrayList<WeatherObserver>();
    private Weather state;

    public Weather getState() {
        return state;
    }

    public void setState(Weather state) {
        this.state = state;
        notifyAllObservers();
    }

    public void attach(WeatherObserver observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (WeatherObserver observer : observers) {
            observer.updateWeather(this.state);
        }
    }
}