package com.example.restcountries.presenter;

import android.app.Activity;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.example.restcountries.RestCountries;
import com.example.restcountries.contract.CountryFragmentContract;
import com.example.restcountries.model.svg.SvgSoftwareLayerSetter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class CountryFargmentPresenter implements CountryFragmentContract.Presenter {

    CountryFragmentContract.View view;
    public CountryFargmentPresenter(CountryFragmentContract.View view) {
        this.view = view;
    }

    @Override
    public void onCreate(Bundle bundle) {

        view.setName(bundle.getString("name"));
        view.setCapital(bundle.getString("capital"));
        view.setCurency(bundle.getString("curency"),bundle.getString("symbol"));
    }

    @Override
    public void loadFlag(ImageView imageView, Bundle bundle) {
        RequestBuilder<PictureDrawable>requestBuilder;
        Uri uri = Uri.parse(bundle.getString("flag"));
        requestBuilder = Glide.with(RestCountries.getContext())
                .as(PictureDrawable.class)
                .transition(withCrossFade())
                .listener(new SvgSoftwareLayerSetter());
        requestBuilder
                .load(uri)
                .into(imageView);
    }

}
