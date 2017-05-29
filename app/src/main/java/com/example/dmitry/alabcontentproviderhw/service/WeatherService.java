package com.example.dmitry.alabcontentproviderhw.service;

import com.example.dmitry.alabcontentproviderhw.model.City;
import com.example.dmitry.alabcontentproviderhw.model.Weather;

/**
 * Created by Dmitry on 28.05.2017.
 */

public interface WeatherService{

    Weather get(City city);

    void add(Weather weather);

    void remove(String cityName);
}
