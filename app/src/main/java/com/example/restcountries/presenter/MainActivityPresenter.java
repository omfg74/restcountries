package com.example.restcountries.presenter;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bumptech.glide.RequestBuilder;
import com.example.restcountries.RestCountries;
import com.example.restcountries.contract.MainActivityContract;
import com.example.restcountries.model.DomanRepository.DbModel;
import com.example.restcountries.model.FlagCountry;
import com.example.restcountries.model.DomanRepository.NetworkModel;
import com.example.restcountries.model.county.Country;
import com.example.restcountries.model.realm.RealmCountry;
import com.example.restcountries.model.realm.RealmCurrency;
import com.example.restcountries.network.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
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
    RealmCountry realmCkeck;
    private MainActivityContract.Model.LoadCountryInterface loadCountryInterface;
    private List<FlagCountry> flagCountries = new ArrayList<>();

    int i = 0;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
        this.realmCounrty = new RealmCountry();
        this.flagCountry = new FlagCountry();

    }


    @Override
    public void onCreate() {

//        retrofitInterface = RetrofitClient.getInstance().create(RetrofitInterface.class);
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
                                view.changeFragment(countries);
                            }
                        })
                        .subscribe(
                                new Consumer<Country>() {
                                    @Override
                                    public void accept(Country country) throws Exception {
//                                        Realm realm = Realm.getDefaultInstance();
//                                        realmCkeck = realm.where(RealmCountry.class).findFirst();

                                        if (loadCountryInterface instanceof NetworkModel) {
                                            writeToRealm(country);
                                        }
                                        countries.add(country);
                                    }

                                }));


    }

    private void writeToRealm(Country country) {
        //write data from internet to realm
        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
        try {
            realm.beginTransaction();
            realmCounrty = realm.createObject(RealmCountry.class);
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
        realmCkeck = realm.where(RealmCountry.class).findFirst();
        if (realmCkeck != null) {
//            view.changeFragment();
            loadCountryInterface = new DbModel();
        } else {

            if (checkINternetConnection()) {
                loadCountryInterface = new NetworkModel();
//                loadCountries();
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
}
