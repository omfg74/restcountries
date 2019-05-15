package com.example.restcountries.contract;

import android.graphics.drawable.PictureDrawable;

import com.arellomobile.mvp.MvpView;
import com.example.restcountries.model.county.Country;
import com.example.restcountries.model.realm.RealmCurrency;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.RealmList;

public interface MainActivityContract extends MvpView {
    interface View {
        void showSplashFregment();

        void makeTost(String s);

        void changeFragment(ArrayList<Country> countries);

        void showAlertDialog();
    }

    interface Presenter {
        void onCreate();

        void exit();

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

        interface LoadCountryInterface {
            Observable<Country> loadCountry();
        }
        interface DataBaseWriterInterface{
            void writeToDatabase(Country country);
        }
    }
}
