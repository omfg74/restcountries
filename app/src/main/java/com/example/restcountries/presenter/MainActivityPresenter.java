package com.example.restcountries.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.restcountries.Logger;
import com.example.restcountries.RestCountries;
import com.example.restcountries.contract.MainActivityContract;
import com.example.restcountries.model.FlagCountry;
import com.example.restcountries.model.county.Country;
import com.example.restcountries.model.realm.RealmCounty;
import com.example.restcountries.model.realm.RealmCurrency;
import com.example.restcountries.model.svg.SvgSoftwareLayerSetter;
import com.example.restcountries.network.RetrofitClient;
import com.example.restcountries.network.RetrofitInterface;

import java.nio.file.attribute.AclEntryType;
import java.util.ArrayList;
import java.util.List;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmList;

import static com.bumptech.glide.Glide.with;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    MainActivityContract.View view;
    MainActivityContract.Model realmCounrty;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RequestBuilder<PictureDrawable> requestBuilder;
    private RetrofitInterface retrofitInterface;
    private FlagCountry flagCountry;
    private List<FlagCountry> flagCountries = new ArrayList<>();

    int i = 0;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
        this.realmCounrty = new RealmCounty();
        this.flagCountry = new FlagCountry();

    }


    @Override
    public void onCreate() {

        retrofitInterface = RetrofitClient.getInstance().create(RetrofitInterface.class);
        checkINternetConnection();
        checkIfRealmIsEmpty();

    }

    void loadFromNetwork() {
        compositeDisposable.add(
                loadAllData()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Logger.toLog("on error");
                                view.makeTost("Error loading data");
                            }
                        })
                        .doOnComplete(new Action() {
                            @Override
                            public void run() throws Exception {
                                view.changeFragment();
                            }
                        })
                        .subscribe(
                                new Consumer<Country>() {
                                    @Override
                                    public void accept(Country country) throws Exception {
                                        writeToRealm(country);
                                    }

                                }));


    }

    private Observable<Country> loadAllData() {
        return retrofitInterface
                .getAllCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.toLog("Error ");
                        Logger.toLog(" " + throwable.getMessage());
                    }
                })
                .flatMap(new Function<List<Country>, ObservableSource<Country>>() {
                    @Override
                    public ObservableSource<Country> apply(List<Country> countries) throws Exception {
                        Logger.toLog("get all data");
                        Logger.toLog("country " + countries.size());
                        return Observable.fromIterable(countries).subscribeOn(Schedulers.io());
                    }
                });
    }

    private void writeToRealm(Country country) {
        //write data from internet to realm
        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
        try {
            realm.beginTransaction();

            Logger.toLog("realm start" + country.getName());
            realmCounrty = realm.createObject(RealmCounty.class);
            RealmList<RealmCurrency> realmCurrencyList = new RealmList<>();
            realmCounrty.setName(country.getName());
            realmCounrty.setCapital(country.getCapital());
            for (int j = 0; j < country.getCurrencies().size(); j++) {
                RealmCurrency realmCurrency = realm.createObject(RealmCurrency.class);
                realmCurrency.setName(country.getCurrencies().get(j).getName());
                realmCurrency.setCode(country.getCurrencies().get(j).getCode());
                realmCurrency.setSymbol(country.getCurrencies().get(j).getSymbol());
                realmCurrencyList.add(realmCurrency);
            }
            realmCounrty.setCurrency(realmCurrencyList);
            realmCounrty.setFlagLink(country.getFlag());
            realm.commitTransaction();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {


            realm.close();
        }
    }

    private void checkIfRealmIsEmpty() {
        Realm realm = Realm.getDefaultInstance();
        RealmCounty realmCounty = realm.where(RealmCounty.class).findFirst();
        if (realmCounty != null) {
            view.changeFragment();
        } else {
            if (checkINternetConnection()) {
                loadFromNetwork();
            } else {
                allertNoInternet();
            }
        }

    }

    private void allertNoInternet() {
        //make dialog to tur internet on or to exit
       view.showAlertDialog();
    }

    private boolean checkINternetConnection() {
//check if internet is on
        ConnectivityManager connectivityManager= (ConnectivityManager) RestCountries.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void exit() {
        System.exit(0);
    }
}
