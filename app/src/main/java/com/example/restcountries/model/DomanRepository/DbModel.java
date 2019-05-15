package com.example.restcountries.model.DomanRepository;

import com.example.restcountries.contract.MainActivityContract;
import com.example.restcountries.model.county.Country;
import com.example.restcountries.model.county.Currency;
import com.example.restcountries.model.realm.RealmCountry;
import com.example.restcountries.model.realm.RealmCurrency;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class DbModel implements MainActivityContract.Model.LoadCountryInterface {
    @Override
    public Observable<Country> loadCountry() {
//      return convert()
//              .subscribeOn(Schedulers.io())
//              .observeOn(AndroidSchedulers.mainThread())
//              .flatMap(new Function<List<Country>, ObservableSource<Country>>() {
//                  @Override
//                  public ObservableSource<Country> apply(List<Country> countries) throws Exception {
//                      return Observable.fromIterable(countries).subscribeOn(Schedulers.io());
//                  }
//              });
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmCountry> realmCountries = realm.where(RealmCountry.class).findAll();
        RealmList<RealmCurrency> realmCurrencies  = realmCountries.get(0).getCurrency();
//        loadPictures(realmCountries);
        List<Country> countries = new ArrayList<>();
        for (int i = 0; i <realmCountries.size() ; i++) {
            Country country = new Country();
            country.setName(realmCountries.get(i).getName());
            country.setCapital(realmCountries.get(i).getCapital());
//            country.setCurrencies(realm);
            country.setFlag(realmCountries.get(i).getFlagLink());
            List<Currency> currencies = new ArrayList<>();
            for (int j = 0; j < realmCountries.get(i).getCurrency().size(); j++) {

                Currency currency = new Currency();
                currency.setCode(realmCountries.get(i).getCurrency().get(j).getCode());
                currency.setName(realmCountries.get(i).getCurrency().get(j).getName());
                currency.setSymbol(realmCountries.get(i).getCurrency().get(j).getSymbol());
                currencies.add(currency);
            }
            country.setCurrencies(currencies);
            countries.add(country);

        }
        return Observable.fromIterable(countries);
}
private Observable<List<Country>>convert(){
//    Realm realm = Realm.getDefaultInstance();
//    RealmResults<RealmCountry> realmCountries = realm.where(RealmCountry.class).findAll();
//    RealmList<RealmCurrency> currencies  = realmCountries.get(0).getCurrency();
////        loadPictures(realmCountries);
//    List<Country> countries = new ArrayList<>();
//    for (int i = 0; i <realmCountries.size() ; i++) {
//        Country country = new Country();
//        country.setName(realmCountries.get(i).getName());
//        country.setCapital(realmCountries.get(i).getCapital());
////            country.setCurrencies(realm);
//        country.setFlag(realmCountries.get(i).getFlagLink());
//        countries.add(country);
//
//    }
//        return Observable.fromIterable(countries);
//}
    return null;
}
}
