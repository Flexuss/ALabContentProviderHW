package com.example.dmitry.alabcontentproviderhw.service;

import android.content.Context;
import android.database.Cursor;

import com.example.dmitry.alabcontentproviderhw.database.CityContract;
import com.example.dmitry.alabcontentproviderhw.model.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry on 28.05.2017.
 */

public class CityServiceImpl implements CityService {
    private Context context;

    public CityServiceImpl(Context context) {
        this.context = context;
    }

    @Override
    public List<City> getAll() {
        List<City> cityList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(CityContract.getUri(), null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                cityList.add(CityContract.cityAtCursor(cursor));
            }
            cursor.close();
        }
        return cityList;
    }

    @Override
    public void add(City city) {
        context.getContentResolver().insert(CityContract.getUri(),
                CityContract.toContentValues(city));
    }

    @Override
    public void remove(City city) {
        context.getContentResolver().delete(CityContract.getUri(),
                CityContract.CityEntry.COLUMN_NAME + "=?",
                new String[]{city.getName()});
    }
}
