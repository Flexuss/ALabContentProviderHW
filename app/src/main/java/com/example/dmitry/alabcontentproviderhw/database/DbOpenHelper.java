package com.example.dmitry.alabcontentproviderhw.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Dmitry on 28.05.2017.
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    Context context;

    public DbOpenHelper(Context context) {
        super(context, "weather", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Toast.makeText(context, "onCreate", Toast.LENGTH_SHORT).show();
        CityContract.createTable(sqLiteDatabase);
        WeatherContract.createTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
