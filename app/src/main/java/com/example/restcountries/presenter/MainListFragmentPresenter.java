package com.example.restcountries.presenter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ahmadrosid.svgloader.SvgSoftwareLayerSetter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.restcountries.Logger;
import com.example.restcountries.RestCountries;
import com.example.restcountries.contract.MainListFragmentContract;
import com.example.restcountries.model.realm.RealmCounty;
import com.example.restcountries.model.realm.RealmCurrency;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainListFragmentPresenter implements MainListFragmentContract.Presenter {
    MainListFragmentContract.View view;
    MainListFragmentContract.Model model;
    RequestBuilder<PictureDrawable> requestBuilder;

    public MainListFragmentPresenter(MainListFragmentContract.View view) {
        this.view =  view;
        this.model = new RealmCounty();

    }

    @Override
    public void onCreate() {


        loadDatafromrealm();
    }

    private void loadDatafromrealm(){
//        List<RealmCounty> realmCountyList = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmCounty>realmCountries = realm.where(RealmCounty.class).findAll();
        RealmList<RealmCurrency>currencies  = realmCountries.get(0).getCurrency();
        Logger.toLog("Realm currency "+currencies.get(0).getName());
//        Logger.toLog("Realm data size "+realmCounties.size());
        loadPictures(realmCountries);



    }
    private void loadPictures(RealmResults<RealmCounty> realmResults){
        PictureDrawable pictureDrawable;
        Observable.fromIterable(realmResults)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RealmCounty>() {
                    @Override
                    public void accept(RealmCounty realmCounty) throws Exception {
                        view.postDataToList(realmCounty);
                    }
                });
        }

}
