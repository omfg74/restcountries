package com.example.restcountries.model.DomanRepository;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.restcountries.RestCountries;
import com.example.restcountries.contract.MainActivityContract;
import com.example.restcountries.model.country.Country;
import com.example.restcountries.model.svg.SvgSoftwareLayerSetter;


import io.reactivex.Observable;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PictureLoader implements MainActivityContract.Model.PictoreLoaderInterface {
    PictureDrawable pictureDrawable;
    private RequestBuilder<PictureDrawable> requestBuilder;
    @Override
    public PictureDrawable loadPicures(Country country) {
        try {
            Uri uri = Uri.parse(country.getFlag());
            requestBuilder = Glide.with(RestCountries.getContext())
                    .as(PictureDrawable.class)
                    .transition(withCrossFade())
                    .listener(new RequestListener<PictureDrawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<PictureDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(PictureDrawable resource, Object model, Target<PictureDrawable> target, DataSource dataSource, boolean isFirstResource) {
                            if(resource==null){
                                Log.d("Log","RESOURSE NULL");
                            };
                            pictureDrawable=resource;
                            return false;
                        }
                    });
            requestBuilder.diskCacheStrategy(DiskCacheStrategy.DATA);
            requestBuilder
                    .load(uri)
                    .into(new Target<PictureDrawable>() {
                        @Override
                        public void onLoadStarted(@Nullable Drawable placeholder) {

                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {

                        }

                        @Override
                        public void onResourceReady(@NonNull PictureDrawable resource, @Nullable Transition<? super PictureDrawable> transition) {

                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }

                        @Override
                        public void getSize(@NonNull SizeReadyCallback cb) {

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
Log.d("Log","ON STOP");
                        }

                        @Override
                        public void onDestroy() {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(pictureDrawable==null){
            Log.d("Log","PICTURE NULL");
        };
        return pictureDrawable;
    }


}
