package com.example.restcountries.contract;

import android.graphics.drawable.PictureDrawable;

import com.arellomobile.mvp.MvpView;
import com.example.restcountries.interfaces.DrawableCallback;
import com.example.restcountries.model.country.Country;
import com.example.restcountries.model.realm.RealmCurrency;
import com.example.restcountries.presenter.MainActivityPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.realm.RealmList;

public interface MainActivityContract extends MvpView {
    interface View {
        void makeTost(String s);

        void changeFragment(ArrayList<Country> countries, HashMap<String,  byte[] >   bitmapdata);
        void changeFragment(ArrayList<Country>countries);

        void showAlertDialog();

        void progressBarSetVisible();

        void progressBarSetInvisible();

        void progressBarSetProgress(int progress);

        void progressBarSetSize(int size);
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

        interface DataBaseWriterInterface {
            void writeToDatabase(Country country);
        }

        interface PictoreLoaderInterface {
            PictureDrawable loadPictures(Country country, boolean useGlide);
        }
    }
}
