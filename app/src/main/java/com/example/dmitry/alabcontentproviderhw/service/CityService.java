package com.example.dmitry.alabcontentproviderhw.service;

import com.example.dmitry.alabcontentproviderhw.model.City;

import java.util.List;

/**
 * Created by Dmitry on 28.05.2017.
 */

public interface CityService {

    List<City> getAll();

    void add(City city);

    void remove(City city);
}
