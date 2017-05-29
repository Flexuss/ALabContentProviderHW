package com.example.dmitry.alabcontentproviderhw;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmitry.alabcontentproviderhw.database.CityContract;
import com.example.dmitry.alabcontentproviderhw.model.City;
import com.example.dmitry.alabcontentproviderhw.model.Weather;
import com.example.dmitry.alabcontentproviderhw.receiver.MainIntentService;
import com.example.dmitry.alabcontentproviderhw.receiver.MainReceiver;
import com.example.dmitry.alabcontentproviderhw.receiver.MainReceiverListenet;
import com.example.dmitry.alabcontentproviderhw.service.WeatherService;
import com.example.dmitry.alabcontentproviderhw.service.WeatherServiceImpl;

import java.text.DateFormat;

/**
 * Created by Dmitry on 28.05.2017.
 */

public class CityActivity extends AppCompatActivity implements View.OnClickListener, MainReceiverListenet.MainReceiverListener {
    private static final String IS_LOADING = "loading";

    private TextView tvAbout;
    private Button btnUpdate;
    private ProgressBar progressBar;

    private WeatherService weatherService;
    private MainReceiver receiver;

    private City city;

    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        weatherService = new WeatherServiceImpl(this);

        tvAbout = (TextView) findViewById(R.id.tv_about);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.pb_on_update);

        receiver = new MainReceiver(this);
        IntentFilter intentFilter = new IntentFilter(MainIntentService.ACTION_UPDATED);
        registerReceiver(receiver,intentFilter);

        if (savedInstanceState != null){
            city = CityContract.fromBundle(savedInstanceState);
            setLoading(savedInstanceState.getBoolean(IS_LOADING));
        } else {
            city = CityContract.fromIntent(getIntent());
        }

        Weather weather = weatherService.get(city);

        if (weather!= null){
            tvAbout.setText(weatherToString(weather));
            System.out.println("WEATHER != NULL!");
        } else {
            System.out.println("WEATHER = NULL!");
            executeService();
        }
    }

    private void executeService(){
        MainIntentService.start(this, city);
        setLoading(true);
        System.out.println("SERVICE EXECUTED!");
    }



    @Override
    public void onClick(View v) {
        executeService();
    }

    private void setLoading(boolean loading) {
        this.isLoading = loading;
        if (loading) {
            tvAbout.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {

            tvAbout.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDataReceive(String status) {
        System.out.println("DATA RECEIVED!");
        switch (status){
            case MainReceiver.STATUS_OK:
                tvAbout.setText(weatherToString(weatherService.get(city)));
                break;
            case MainReceiver.STATUS_FAIL:
                Toast.makeText(this,"request failure",Toast.LENGTH_LONG).show();
                break;
        }
        setLoading(false);
    }

    private String weatherToString(Weather weather) {
        String result = "City: " + weather.getCityName() + "\nTemperature: ";
        if (weather.getTemp() > 0) {
            result += "+";
        } else {
            if (weather.getTemp() < 0) {
                result += "-";
            }
        }
        result += weather.getTemp() + "\nCountryCode: " + weather.getCountryCode() +
                "\nLast update: " + DateFormat.getInstance().format(weather.getDate());

        return result;

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        CityContract.insertToBundle(outState,city);
        outState.putBoolean(IS_LOADING,isLoading);
        super.onSaveInstanceState(outState);
    }
}
