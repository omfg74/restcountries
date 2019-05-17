package com.example.restcountries.presenter;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import com.bumptech.glide.RequestBuilder;
import com.example.restcountries.RestCountries;
import com.example.restcountries.contract.MainActivityContract;
import com.example.restcountries.interfaces.DrawableCallback;
import com.example.restcountries.model.DomanRepository.DataBaseWriter;
import com.example.restcountries.model.DomanRepository.DbModel;
import com.example.restcountries.model.DomanRepository.NetworkModel;
import com.example.restcountries.model.DomanRepository.PictureLoader;
import com.example.restcountries.model.country.Country;
import com.example.restcountries.model.realm.RealmCountry;
import com.example.restcountries.network.RetrofitInterface;

import java.util.ArrayList;
import java.util.HashMap;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

import static com.bumptech.glide.Glide.with;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivityPresenter implements MainActivityContract.Presenter, DrawableCallback {
//master
    MainActivityContract.View view;
    MainActivityContract.Model.DataBaseWriterInterface writerInterface;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RequestBuilder<PictureDrawable> requestBuilder;
    private RetrofitInterface retrofitInterface;
    RealmCountry realmCkeck;
    PictureDrawable pictureDrawable;
    HashMap<String,  byte[] > biteArrayMap;
    ArrayList< byte[]> drawables;
    ArrayList<Country> countries = new ArrayList<>();
    private MainActivityContract.Model.LoadCountryInterface loadCountryInterface;
    private MainActivityContract.Model.PictoreLoaderInterface pictureLoaderInterface;
    boolean useGlide = true;//change to enable or disable Glide usage in picture loading
    int i= 0;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
        this.writerInterface = new DataBaseWriter();
        this.pictureLoaderInterface = new PictureLoader(this);
        this.biteArrayMap = new HashMap<>();
        this.drawables = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        checkINternetConnection();
        checkIfRealmIsEmpty();
    }

    void loadCountries() {
        view.progressBarSetVisible();
        if (loadCountryInterface instanceof DbModel){
            view.progressBarSetSize(250);
        }
        compositeDisposable.add(
                loadCountryInterface.loadCountry()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                view.makeTost("Error loading data");
                            }
                        })
                        .doOnComplete(new Action() {
                            @Override
                            public void run() throws Exception {
                                if (loadCountryInterface instanceof NetworkModel) {
                                    view.progressBarSetSize(countries.size());
                                }else if(loadCountryInterface instanceof DbModel) {
                                    view.progressBarSetInvisible();
                                    view.changeFragment(countries);
                                }
                            }
                        }).subscribe(
                        new Consumer<Country>() {
                            @Override
                            public void accept(Country country) throws Exception {
                                if (loadCountryInterface instanceof NetworkModel) {
                                    writerInterface.writeToDatabase(country);
                                    pictureLoaderInterface.loadPictures(country, useGlide);
                               }else if(loadCountryInterface instanceof DbModel) {
                                    view.progressBarSetProgress(countries.size());
                            }
                                countries.add(country);
                            }

                        }));
    }

    private void checkIfRealmIsEmpty() {
        Realm realm = Realm.getDefaultInstance();
        realmCkeck = realm.where(RealmCountry.class).findFirst();
        if (realmCkeck != null) {
            loadCountryInterface = new DbModel();
        } else {
            if (checkINternetConnection()) {
                loadCountryInterface = new NetworkModel();
            } else {
                allertNoInternet();
                return;
            }
        }
        loadCountries();
    }

    private void allertNoInternet() {
        //make dialog to tur internet on or to exit
        view.showAlertDialog();
    }

    private boolean checkINternetConnection() {
        //check if internet is on
        ConnectivityManager connectivityManager = (ConnectivityManager) RestCountries.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void exit() {
        System.exit(0);
    }

    @Override
    public void callback(Country country,  byte[] bitmapdata) {
        if(loadCountryInterface instanceof NetworkModel){
            if (!useGlide){
        drawables.add(bitmapdata);
        view.progressBarSetProgress(drawables.size());
        biteArrayMap.put(country.getFlag(), bitmapdata);
        if (drawables.size() == countries.size()) {
            view.changeFragment(countries, biteArrayMap);
            view.progressBarSetInvisible();
        }
    }
        }
    }

    @Override
    public void callback() {
        i++;
        view.progressBarSetProgress(i);
        if (countries.size()==i) {
            view.changeFragment(countries);
            view.progressBarSetInvisible();
        }
    }
}

