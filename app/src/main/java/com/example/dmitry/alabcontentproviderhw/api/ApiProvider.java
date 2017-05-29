package com.example.dmitry.alabcontentproviderhw.api;

import com.example.dmitry.alabcontentproviderhw.api.pojo.WeatherPojo;
import com.example.dmitry.alabcontentproviderhw.model.City;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dmitry on 28.05.2017.
 */

public class ApiProvider {
    String BASE_URL = "http://api.openweathermap.org";

    private static ApiProvider openWeatherApiProvider;
    private ApiService openWeatherApiService;

    private ApiProvider(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        openWeatherApiService = retrofit.create(ApiService.class);
    }

    public Response<WeatherPojo> getWeather(City city) throws IOException {
        Call<WeatherPojo> call = openWeatherApiService.getWeather(city.getName());
        return call.execute();
    }

    public static ApiProvider getInstance(){
        if (openWeatherApiProvider == null){
            openWeatherApiProvider = new ApiProvider();
        }
        return openWeatherApiProvider;
    }
}
