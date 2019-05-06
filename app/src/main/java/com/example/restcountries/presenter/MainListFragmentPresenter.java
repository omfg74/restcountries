package com.example.restcountries.presenter;

import android.graphics.drawable.PictureDrawable;

import com.bumptech.glide.RequestBuilder;
import com.example.restcountries.contract.MainListFragmentContract;
import com.example.restcountries.model.realm.RealmCounty;
import com.example.restcountries.model.realm.RealmCurrency;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
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
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmCounty>realmCountries = realm.where(RealmCounty.class).findAll();
        RealmList<RealmCurrency>currencies  = realmCountries.get(0).getCurrency();
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
