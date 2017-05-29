package com.example.dmitry.alabcontentproviderhw.model;

import java.util.Date;

/**
 * Created by Dmitry on 28.05.2017.
 */

public class Weather {

    private Date date;

    private String cityName;

    private String countryCode;

    private Double temp;

    public Weather() {
    }

    public Weather(Date date, String cityName, String countryCode, Double temp) {

        this.date = date;
        this.cityName = cityName;
        this.countryCode = countryCode;
        this.temp = temp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }
}
