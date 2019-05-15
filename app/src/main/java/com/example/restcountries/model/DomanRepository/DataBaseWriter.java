package com.example.restcountries.model.DomanRepository;

import com.example.restcountries.contract.MainActivityContract;
import com.example.restcountries.model.county.Country;
import com.example.restcountries.model.realm.RealmCountry;
import com.example.restcountries.model.realm.RealmCurrency;

import io.realm.Realm;
import io.realm.RealmList;

public class DataBaseWriter implements MainActivityContract.Model.DataBaseWriterInterface {

    RealmCountry realmCounrty = new RealmCountry();
    @Override
    public void writeToDatabase(Country country) {
        //write data from internet to realm
        Realm realm = Realm.getDefaultInstance();
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
}
