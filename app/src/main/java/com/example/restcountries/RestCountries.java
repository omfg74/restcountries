package com.example.restcountries;

import android.app.Application;
import android.content.Context;

import java.lang.reflect.TypeVariable;
import java.time.temporal.ValueRange;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RestCountries extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        RestCountries.context = getApplicationContext();
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(0)
                .migration(new RestCountriesMigration())
                .build();
    }

    public static Context getContext() {
        return RestCountries.context;
    }
}
