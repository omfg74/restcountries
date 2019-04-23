package com.example.restcountries.contract;

import com.arellomobile.mvp.MvpView;
import com.example.restcountries.model.realm.RealmCurrency;

import io.realm.RealmList;

public interface MainActivityContract extends MvpView {
    interface View{
        void showSplashFregment();

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
    }
}
