package com.example.restcountries.contract;

import android.graphics.drawable.PictureDrawable;

import com.arellomobile.mvp.MvpView;
import com.example.restcountries.model.county.Country;
import com.example.restcountries.model.realm.RealmCurrency;

import io.realm.RealmList;

public interface MainActivityContract extends MvpView {
    interface View{
        void showSplashFregment();
        void makeTost(String s);
        void changeFragment();

    }
    interface Presenter{
        void onCreate();

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

        public interface ICountry {
            public Country getCountry();
            public PictureDrawable getPictureDrawable();
            public void setPictureDrawable(PictureDrawable pictureDrawable);
            public void setCountry(Country country);


        }
    }
}
