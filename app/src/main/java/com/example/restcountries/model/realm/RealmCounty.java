package com.example.restcountries.model.realm;

import com.example.restcountries.contract.MainActivityContract;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

public class RealmCounty extends RealmObject implements MainActivityContract.Model {
    private String name;
    private RealmList<RealmCurrency> currency;
    private String flagLink;
    private String capital;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public RealmList<RealmCurrency> getCurrency() {
        return currency;
    }

    @Override
    public String getFlagLink() {
        return flagLink;
    }

    @Override
    public String getCapital() {
        return capital;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setCurrency(RealmList<RealmCurrency> currency) {
        this.currency = currency;
    }


    @Override
    public void setFlagLink(String flagLink) {

        this.flagLink = flagLink;
    }

    @Override
    public void setCapital(String capital) {
        this.capital = capital;
    }
}
