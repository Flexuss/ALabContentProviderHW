package com.example.dmitry.alabcontentproviderhw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dmitry.alabcontentproviderhw.database.CityContract;
import com.example.dmitry.alabcontentproviderhw.model.City;
import com.example.dmitry.alabcontentproviderhw.service.CityService;
import com.example.dmitry.alabcontentproviderhw.service.CityServiceImpl;
import com.example.dmitry.alabcontentproviderhw.service.WeatherService;
import com.example.dmitry.alabcontentproviderhw.service.WeatherServiceImpl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnCityClickListener {

    private CityService cityService;
    private WeatherService weatherService;

    private Button btnAdd;
    private EditText etCity;

    private RecyclerView recyclerView;
    private CityAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityService = new CityServiceImpl(this);
        weatherService = new WeatherServiceImpl(this);

        recyclerView = (RecyclerView) findViewById(R.id.rv_cities);
        adapter = new CityAdapter();
        adapter.setListener(this);
        adapter.setCities(cityService.getAll());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
        etCity = (EditText) findViewById(R.id.et_city_name);
    }

    @Override
    public void onClick(View v) {
        addCity(etCity.getText().toString());
    }

    private void addCity(String cityName) {
        City city = new City(cityName);
        cityService.add(city);
        adapter.setCities(cityService.getAll());
    }

    private void removeCity(City city) {
        weatherService.remove(city.getName());
        cityService.remove(city);
        adapter.remove(city);

    }

    @Override
    public void onDelete(City city) {
        removeCity(city);
    }

    @Override
    public void onClick(City city) {
        Intent intent = new Intent(this, CityActivity.class);
        CityContract.insertToIntent(intent, city);
        startActivity(intent);
    }
}
