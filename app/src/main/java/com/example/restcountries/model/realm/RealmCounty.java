package com.example.restcountries.model.realm;

import io.realm.Realm;
import io.realm.RealmObject;

public class RealmCounty extends RealmObject {
    private String name;
    private String currency;
    private String flagLink;
    private String capital;

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public String getFlagLink() {
        return flagLink;
    }

    public String getCapital() {
        return capital;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setFlagLink(String flagLink) {
        this.flagLink = flagLink;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }
}
