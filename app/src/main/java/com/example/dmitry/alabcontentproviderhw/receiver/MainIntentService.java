package com.example.dmitry.alabcontentproviderhw.receiver;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.dmitry.alabcontentproviderhw.api.ApiProvider;
import com.example.dmitry.alabcontentproviderhw.api.pojo.WeatherPojo;
import com.example.dmitry.alabcontentproviderhw.database.CityContract;
import com.example.dmitry.alabcontentproviderhw.model.City;
import com.example.dmitry.alabcontentproviderhw.model.Weather;
import com.example.dmitry.alabcontentproviderhw.service.CityService;
import com.example.dmitry.alabcontentproviderhw.service.CityServiceImpl;
import com.example.dmitry.alabcontentproviderhw.service.WeatherService;
import com.example.dmitry.alabcontentproviderhw.service.WeatherServiceImpl;

import java.io.IOException;
import java.util.Date;

import retrofit2.Response;

/**
 * Created by Dmitry on 28.05.2017.
 */

public class MainIntentService extends IntentService {
    public MainIntentService() {
        super(MainIntentService.class.getName());
    }
    public static final String ACTION_UPDATED = "action.update";

    public static void start(Context context, City city) {

        Intent intent = new Intent(context, MainIntentService.class);
        CityContract.insertToIntent(intent, city);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        WeatherService weatherService = new WeatherServiceImpl(getApplicationContext());
        CityService cityService = new CityServiceImpl(getApplicationContext());

        String status = MainReceiver.STATUS_FAIL;
        City city =CityContract.fromIntent(intent);
        ApiProvider apiProvider = ApiProvider.getInstance();

        try {
            Response<WeatherPojo> response = apiProvider.getWeather(city);
            if (response.isSuccessful()){
                WeatherPojo weatherPojo = response.body();
                Weather weather = new Weather(new Date(System.currentTimeMillis()),
                        city.getName(),
                        weatherPojo.getSys().getCountry(),
                        weatherPojo.getMain().getTemp() - 273);

                weatherService.remove(city.getName());
                weatherService.add(weather);
                status = MainReceiver.STATUS_OK;
            }else {
                status = MainReceiver.STATUS_FAIL;
            }
        } catch (IOException e) {
            e.printStackTrace();
            status = MainReceiver.STATUS_FAIL;
        }

        Intent brIntent = new Intent(ACTION_UPDATED);
        brIntent.putExtra(MainReceiver.STATUS_KEY,status);
        sendBroadcast(brIntent);
    }
}
