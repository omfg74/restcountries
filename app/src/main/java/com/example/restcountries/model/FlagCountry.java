package com.example.restcountries.model;

import android.graphics.drawable.PictureDrawable;

import com.example.restcountries.contract.MainActivityContract;
import com.example.restcountries.model.county.Country;

public class FlagCountry implements MainActivityContract.Model.ICountry {

    private Country country;
    private PictureDrawable pictureDrawable;

    @Override
    public Country getCountry() {
        return country;
    }

    @Override
    public PictureDrawable getPictureDrawable() {
        return pictureDrawable;
    }

    @Override
    public void setPictureDrawable(PictureDrawable pictureDrawable) {
        this.pictureDrawable = pictureDrawable;
    }

    @Override
    public void setCountry(Country country) {
        this.country = country;
    }
}
