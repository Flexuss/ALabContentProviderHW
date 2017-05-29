package com.example.dmitry.alabcontentproviderhw.database;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import com.example.dmitry.alabcontentproviderhw.model.City;

/**
 * Created by Dmitry on 28.05.2017.
 */

public class CityContract {

    public static final String TABLE_NAME = "cities";

    public static void createTable(@NonNull SQLiteDatabase db) {
        TableBuilder.create(TABLE_NAME)
                .intColumn(CityEntry._ID)
                .textColumn(CityEntry.COLUMN_NAME)
                .primaryKey(CityEntry.COLUMN_NAME)
                .execute(db);
    }
    public static Uri getUri() {
        return MyContentProvider.getBaseUri().buildUpon().appendPath(TABLE_NAME).build();
    }

    public static City cityAtCursor(Cursor cursor) {
        int nameIndex = cursor.getColumnIndex(CityEntry.COLUMN_NAME);
        String cityName = cursor.getString(nameIndex);
        return new City(cityName);
    }

    public static ContentValues toContentValues(City city) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CityEntry.COLUMN_NAME, city.getName());
        return contentValues;
    }

    public static void insertToBundle(Bundle bundle, City city) {
        bundle.putString(CityEntry.COLUMN_NAME, city.getName());
    }

    public static City fromBundle(Bundle bundle) {
        String cityName = bundle.getString(CityEntry.COLUMN_NAME);
        if (cityName != null) {
            return new City(cityName);
        } else {
            return null;
        }
    }

    public static void insertToIntent(Intent intent, City city) {
        intent.putExtra(CityEntry.COLUMN_NAME, city.getName());
    }

    public static City fromIntent(@NonNull Intent intent) {
        String cityName = intent.getStringExtra(CityEntry.COLUMN_NAME);
        if (cityName != null) {
            return new City(cityName);
        } else {
            return null;
        }

    }


    public static class CityEntry implements BaseColumns {

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_NAME = "name";
    }
}
