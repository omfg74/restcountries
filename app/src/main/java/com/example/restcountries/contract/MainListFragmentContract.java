package com.example.restcountries.contract;

import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;

import com.example.restcountries.model.country.Country;
import com.example.restcountries.model.realm.RealmCurrency;

import io.realm.RealmList;

public interface MainListFragmentContract {
    interface View {
        void postDataToList(Country country, byte[] pictureDrawable);
        void postDataToList(Country country);
    }

    interface Model {
        String getName();
        RealmList<RealmCurrency> getCurrency();
        String getFlagLink();
        String getCapital();
        void setName(String name);
        void setCurrency(RealmList<RealmCurrency> currency);
        void setFlagLink(String flagLink);
        void setCapital(String capital);
    }

    interface Presenter {
        void onCreate(Bundle bundle);
        void onSaveInstanceState(Bundle outState);
    }
}
