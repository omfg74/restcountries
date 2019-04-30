package com.example.restcountries.presenter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ahmadrosid.svgloader.SvgSoftwareLayerSetter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.restcountries.Logger;
import com.example.restcountries.contract.MainListFragmentContract;
import com.example.restcountries.model.realm.RealmCounty;


import io.realm.Realm;
import io.realm.RealmResults;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainListFragmentPresenter implements MainListFragmentContract.Presenter {
    MainListFragmentContract.View view;
    MainListFragmentContract.Model model;

    public MainListFragmentPresenter(MainListFragmentContract.View view) {
        this.view =  view;
        this.model = new RealmCounty();

    }

    @Override
    public void onCreate() {


    }

    private void loadDatafromrealm(){
//        List<RealmCounty> realmCountyList = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmCounty>realmCounties = realm.where(RealmCounty.class).findAll();


    }
    private void LoadPictures(RealmResults<RealmCounty> realmResults){
        RequestBuilder<PictureDrawable> requestBuilder;
        for (int i = 0; i <realmResults.size() ; i++) {
            RealmCounty country = realmResults.get(i);
            Uri uri = Uri.parse(realmResults.get(i).getFlagLink());
            requestBuilder = Glide.with((Activity)view)
                    .as(PictureDrawable.class)
//                .placeholder(R.drawable.image_loading)
//                .error(R.drawable.image_error)
                    .transition(withCrossFade())
                    .listener(new SvgSoftwareLayerSetter());

            requestBuilder.load(uri).onlyRetrieveFromCache(true).into(new Target<PictureDrawable>() {
                @Override
                public void onLoadStarted(@Nullable Drawable placeholder) {

                    Logger.toLog("onLoadStarted");
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    Logger.toLog("onLoadFailed");
                }

                @Override
                public void onResourceReady(@NonNull PictureDrawable resource, @Nullable Transition<? super PictureDrawable> transition) {
                    Logger.toLog(""+resource);
                    view.postPicture(resource, country);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {
                    Logger.toLog("onLoadCleared");
                }

                @Override
                public void getSize(@NonNull SizeReadyCallback cb) {
                    Logger.toLog("size "+ cb);
                }

                @Override
                public void removeCallback(@NonNull SizeReadyCallback cb) {

                }

                @Override
                public void setRequest(@Nullable Request request) {

                }

                @Nullable
                @Override
                public Request getRequest() {
                    return null;
                }

                @Override
                public void onStart() {

                }

                @Override
                public void onStop() {

                }

                @Override
                public void onDestroy() {

                }
            });
        }
    }
}
