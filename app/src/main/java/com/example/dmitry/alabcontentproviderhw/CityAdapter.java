package com.example.dmitry.alabcontentproviderhw;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dmitry.alabcontentproviderhw.model.City;

import java.util.List;

/**
 * Created by Dmitry on 28.05.2017.
 */

public class CityAdapter extends RecyclerView.Adapter {

    private List<City> cities;
    private OnCityClickListener listener;

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public void setListener(OnCityClickListener listener) {
        this.listener = listener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        City city = cities.get(position);
        ((CityViewHolder) holder).bind(city, listener);
    }

    @Override
    public int getItemCount() {
        if (cities != null) {
            return cities.size();
        } else {
            return 0;
        }
    }
    public void add(City city) {
        cities.add(city);
        notifyDataSetChanged();
    }

    public void remove(City city) {
        cities.remove(city);
        notifyDataSetChanged();
    }

}

class CityViewHolder extends RecyclerView.ViewHolder {

    private TextView tvCityName;

    private Button btnDelete;

    public CityViewHolder(View itemView) {
        super(itemView);
        tvCityName = (TextView) itemView.findViewById(R.id.tv_name);
        btnDelete = (Button) itemView.findViewById(R.id.btn_delete);
    }

    public void bind(@NonNull final City city, @Nullable final OnCityClickListener listener) {
        tvCityName.setText(city.getName());

        if (listener != null) {
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDelete(city);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(city);
                }
            });
        }
    }
}
