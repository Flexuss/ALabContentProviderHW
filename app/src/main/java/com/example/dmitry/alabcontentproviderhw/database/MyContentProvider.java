package com.example.dmitry.alabcontentproviderhw.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Dmitry on 28.05.2017.
 */

public class MyContentProvider extends ContentProvider {
    private DbOpenHelper dbHelper;

    public static final String BASE_AUTHORITY = "com.example.dmitry.alabcontentproviderhw";

    public static Uri baseUri;

    private final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final int URI_KEY_CITY = 2;

    public static final int URI_KEY_WEATHER = 4;


    public static Uri getBaseUri() {
        return baseUri;
    }

    @Override
    public boolean onCreate() {
        if (getContext() != null) {
            dbHelper = new DbOpenHelper(getContext());
            baseUri = Uri.parse("content://" + BASE_AUTHORITY);
            matcher.addURI(BASE_AUTHORITY, CityContract.TABLE_NAME, URI_KEY_CITY);
            matcher.addURI(BASE_AUTHORITY, WeatherContract.TABLE_NAME, URI_KEY_WEATHER);
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String tableName = getType(uri);
        Cursor cursor = dbHelper.getReadableDatabase()
                .query(tableName, projection, selection, selectionArgs, null, null, null);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int matchCode = matcher.match(uri);
        switch (matchCode) {
            case URI_KEY_CITY:
                return CityContract.TABLE_NAME;
            case URI_KEY_WEATHER:
                return WeatherContract.TABLE_NAME;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName = getType(uri);
        long id = dbHelper.getWritableDatabase().insert(tableName, null, values);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getType(uri);
        return dbHelper.getWritableDatabase().delete(tableName, selection, selectionArgs);

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getType(uri);
        return dbHelper.getWritableDatabase().update(tableName, values, selection, selectionArgs);
    }
}
