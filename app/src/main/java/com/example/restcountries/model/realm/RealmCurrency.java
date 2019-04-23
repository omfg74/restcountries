package com.example.restcountries.model.realm;

import io.realm.Realm;
import io.realm.RealmObject;

public class RealmCurrency extends RealmObject {

    private String code;
    private String name;
    private String symbol;

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
}
