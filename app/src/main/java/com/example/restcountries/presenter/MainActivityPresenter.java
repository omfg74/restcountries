package com.example.restcountries.presenter;

import android.graphics.Bitmap;

import com.example.restcountries.Logger;
import com.example.restcountries.contract.MainActivityContract;
import com.example.restcountries.model.county.Country;
import com.example.restcountries.model.realm.RealmCounty;
import com.example.restcountries.model.realm.RealmCurrency;
import com.example.restcountries.network.RetrofitClient;
import com.example.restcountries.network.RetrofitInterface;

import java.util.List;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmList;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    MainActivityContract.View view;
    MainActivityContract.Model realmCounrty;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
//    private ConnectableObservable <List<Bitmap>> flagsObservable = getFlags(new String()) ;



    private RetrofitInterface retrofitInterface;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
        this.realmCounrty = new RealmCounty();

    }



    @Override
    public void onCreate() {
        retrofitInterface = RetrofitClient.getInstance().create(RetrofitInterface.class);
        loadAllData();
    }

    private void loadAllData(){
try {
    compositeDisposable.add(retrofitInterface
            .getAllCountries()
            .subscribeOn(Schedulers.io())
//            .flattenAsObservable((Function<List<Country>, Iterable<?>>) countries -> countries)
            .flatMap(new Function<List<Country>, ObservableSource<?>>() {
                @Override
                public ObservableSource<?> apply(List<Country> countries) throws Exception {
                    return null;
                }
            })
            .subscribe());
}catch (Exception e){e.printStackTrace();}
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<List<Country>>() {
//                    @Override
//                    public void onSuccess(List<Country> countries) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                }));


    }



//    private Observable<Bitmap> getFlags(Country country) {
//        return retrofitInterface.getFlag(country.getFlag())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe();
//        return  retrofitInterface.getFlag(flag)
//                .toObservable()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());

//    }
    private void loadFlags(Country country){

//        //load flag for each counrty and save to gallery

        String filename = generateImageName(country.getName());

        compositeDisposable.add(retrofitInterface
                .getFlag(country.getFlag())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe());
    }


    private void writeToRealm(final List<Country>countries){
        //write data from internet to realm
        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
        try {
            realm.executeTransactionAsync((realm1) -> {
                for (int i = 0; i < countries.size(); i++) {
                    realmCounrty = realm1.createObject(RealmCounty.class);
                    RealmList<RealmCurrency> realmCurrencyList = new RealmList<>();
                    realmCounrty.setName(countries.get(i).getName());
                    realmCounrty.setCapital(countries.get(i).getCapital());
                    for (int j = 0; j < countries.get(i).getCurrencies().size(); j++) {
                        RealmCurrency realmCurrency = realm.createObject(RealmCurrency.class);
                        realmCurrency.setName(countries.get(i).getCurrencies().get(j).getName());
                        realmCurrency.setCode(countries.get(i).getCurrencies().get(j).getCode());
                        realmCurrency.setSymbol(countries.get(i).getCurrencies().get(j).getSymbol());
                        realmCurrencyList.add(realmCurrency);
                    }
                    realmCounrty.setCurrency(realmCurrencyList);
                    realmCounrty.setFlagLink(countries.get(i).getFlag());
                }
            });
        }catch (Exception e){
         e.printStackTrace();
        }finally {


            realm.close();
        }
    }

    private void checkIfRealmIsEmpty(){
        Realm realm = Realm.getDefaultInstance();
        RealmCounty realmCounty = realm.where(RealmCounty.class).findFirst();
        if (realm!=null){
            loadDataFromRealm();
        }else {
            if(checkINternetConnection()){
                loadAllData();
            }else {
                allertNoInternet();
            }
        }

    }

    private void loadDataFromRealm() {
        //load data from realm and pass to list fargmant
    }

    private void allertNoInternet() {
        //make dialog to tur internet on or to exit
    }

    private boolean checkINternetConnection() {
//check if internet is on
        return false;
    }


    private String generateImageName(String countryName){
        String[]str  = countryName.split(" ");
        return str[0];
    }

}
