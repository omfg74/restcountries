package com.example.restcountries.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.restcountries.RestCountries;
import com.example.restcountries.contract.MainActivityContract;
import com.example.restcountries.model.DomanRepository.DataBaseWriter;
import com.example.restcountries.model.DomanRepository.DbModel;
import com.example.restcountries.model.DomanRepository.NetworkModel;
import com.example.restcountries.model.DomanRepository.PictureLoader;
import com.example.restcountries.model.country.Country;
import com.example.restcountries.model.realm.RealmCountry;
import com.example.restcountries.network.RetrofitInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

import static com.bumptech.glide.Glide.with;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    MainActivityContract.View view;
    MainActivityContract.Model.DataBaseWriterInterface writerInterface;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RequestBuilder<PictureDrawable> requestBuilder;
    private RetrofitInterface retrofitInterface;
    RealmCountry realmCkeck;
    PictureDrawable pictureDrawable;
    HashMap<String, PictureDrawable> pictureDrawableMap;
    private MainActivityContract.Model.LoadCountryInterface loadCountryInterface;
    private MainActivityContract.Model.PictoreLoaderInterface pictoreLoaderInterface;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
        this.writerInterface = new DataBaseWriter();
        this.pictoreLoaderInterface = new PictureLoader();
        this.pictureDrawableMap = new HashMap<>();
    }

    @Override
    public void onCreate() {
        checkINternetConnection();
        checkIfRealmIsEmpty();
    }

    void loadCountries() {
        ArrayList<Country> countries = new ArrayList<>();
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
                                view.changeFragment(countries, pictureDrawableMap);
                            }
                        }).subscribe(
                                new Consumer<Country>() {
                                    @Override
                                    public void accept(Country country) throws Exception {
                                        if (loadCountryInterface instanceof NetworkModel) {
                                          writerInterface.writeToDatabase(country);
                                        }
//                                       pictureDrawableMap.put(country.getFlag(),pictoreLoaderInterface.loadPicures(country));
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
    private void loadPictures(Country country){

//        try {
//            Uri uri = Uri.parse(country.getFlag());
//            requestBuilder = Glide.with(RestCountries.getContext())
//                    .as(PictureDrawable.class)
//                    .transition(withCrossFade())
//                    .listener(new RequestListener<PictureDrawable>() {
//                        @Override
//                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<PictureDrawable> target, boolean isFirstResource) {
//                            Log.d("Log","on fail "+e);
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(PictureDrawable resource, Object model, Target<PictureDrawable> target, DataSource dataSource, boolean isFirstResource) {
//                            Log.d("Log","pic "+pictureDrawable);
//                            return false;
//                        }
//                    });
//            requestBuilder
//                    .load(uri);
//            requestBuilder.diskCacheStrategy(DiskCacheStrategy.DATA);
//            requestBuilder
//                    .into(new CustomTarget<PictureDrawable>() {
//                        @Override
//                        public void onResourceReady(@NonNull PictureDrawable resource, @Nullable Transition<? super PictureDrawable> transition) {
//                            pictureDrawable = resource;
//                            Log.d("Log","picture "+pictureDrawable);
//                        }
//
//                        @Override
//                        public void onLoadCleared(@Nullable Drawable placeholder) {
//                            Log.d("Log", "onLoadCleared");
//                        }
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void exit() {
        System.exit(0);
    }
}
