package com.example.dmitry.alabcontentproviderhw.service;

import android.content.Context;
import android.database.Cursor;

import com.example.dmitry.alabcontentproviderhw.database.WeatherContract;
import com.example.dmitry.alabcontentproviderhw.model.City;
import com.example.dmitry.alabcontentproviderhw.model.Weather;

/**
 * Created by Dmitry on 28.05.2017.
 */

public class WeatherServiceImpl implements WeatherService {

    private Context context;

    public WeatherServiceImpl(Context context) {
        this.context = context;
    }

    @Override
    public Weather get(City city) {
        Weather weather = null;
        Cursor cursor = context.getContentResolver().query(WeatherContract.getUri(),
                null,
                WeatherContract.WeatherEntry.COLUMN_CITY_NAME + "=?",
                new String[]{city.getName()}, null);
        while (cursor.moveToNext()) {
            weather = WeatherContract.fromCursor(cursor);
        }
        cursor.close();

        return weather;
    }

    @Override
    public void add(Weather weather) {
        context.getContentResolver().insert(WeatherContract.getUri(),
                WeatherContract.toContentValues(weather));
    }

    @Override
    public void remove(String cityName) {
        context.getContentResolver().delete(WeatherContract.getUri(),
                WeatherContract.WeatherEntry.COLUMN_CITY_NAME + "=?", new String[]{cityName});
    }
}
