package com.example.restcountries.contract;

import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;

import com.example.restcountries.model.county.Country;
import com.example.restcountries.model.realm.RealmCountry;
import com.example.restcountries.model.realm.RealmCurrency;

import io.realm.RealmList;

public interface MainListFragmentContract {
    interface View {

        void postDataToList(Country country);

    }

    interface Model {

        public String getName();

        public RealmList<RealmCurrency> getCurrency();

        public String getFlagLink();

        public String getCapital();

        public void setName(String name);

        public void setCurrency(RealmList<RealmCurrency> currency);

        public void setFlagLink(String flagLink);

        public void setCapital(String capital);
    }

    interface Presenter {
        void onCreate(Bundle bundle);

    }
}
