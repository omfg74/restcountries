package com.example.restcountries.contract;

import com.arellomobile.mvp.MvpView;

public interface MainActivityContract extends MvpView {
    interface View{
        void showSplashFregment();

    }
    interface Presenter{
        void onCreate();

    }
    interface Model{

    }
}
