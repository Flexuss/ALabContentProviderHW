package com.example.dmitry.alabcontentproviderhw.api;

import com.example.dmitry.alabcontentproviderhw.api.pojo.WeatherPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dmitry on 28.05.2017.
 */

public interface ApiService {
    @GET("data/2.5/weather?appid=d36db75030b51fc37abae741892146c6")
    Call<WeatherPojo> getWeather(@Query("q") String cityName);
}
