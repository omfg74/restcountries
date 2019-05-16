package com.example.restcountries.presenter;

import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.RequestBuilder;
import com.example.restcountries.Constants;
import com.example.restcountries.contract.MainListFragmentContract;
import com.example.restcountries.model.country.Country;
import com.example.restcountries.model.realm.RealmCountry;


import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainListFragmentPresenter implements MainListFragmentContract.Presenter {
    MainListFragmentContract.View view;
    MainListFragmentContract.Model model;
    RequestBuilder<PictureDrawable> requestBuilder;

    public MainListFragmentPresenter(MainListFragmentContract.View view) {
        this.view = view;
        this.model = new RealmCountry();

    }

    @Override
    public void onCreate(Bundle bundle) {
        ArrayList<Country> countries = new ArrayList<>();
        HashMap<String, PictureDrawable>pictureDrawableHashMap;
        pictureDrawableHashMap = (HashMap<String, PictureDrawable>) bundle.getSerializable(Constants.COUNTRIES_FLAG);
        countries = (ArrayList<Country>) bundle.getSerializable(Constants.COUNTRIES_KEY);
        loadPictures(countries, pictureDrawableHashMap);
    }

    private void loadPictures(ArrayList<Country> countries,  HashMap<String, PictureDrawable>pictureDrawableHashMap) {
//        PictureDrawable pictureDrawable;
        Observable.fromIterable(countries)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Country>() {
                    @Override
                    public void accept(Country country) throws Exception {
                        PictureDrawable pictureDrawable =  pictureDrawableHashMap.get(country.getFlag());
                        if (pictureDrawable==null){
                        Log.d("Log","ALLLERT NULL");
                        }
                        view.postDataToList(country, pictureDrawable);
                    }
                });
    }

}
