package com.example.restcountries.model.DomanRepository;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
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
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.restcountries.RestCountries;
import com.example.restcountries.contract.MainActivityContract;
import com.example.restcountries.interfaces.DrawableCallback;
import com.example.restcountries.model.country.Country;
import com.example.restcountries.model.svg.SvgSoftwareLayerSetter;


import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PictureLoader implements MainActivityContract.Model.PictoreLoaderInterface {
    PictureDrawable pictureDrawable;
    private RequestBuilder<PictureDrawable> requestBuilder;
    private DrawableCallback callback;

    public PictureLoader(DrawableCallback callback) {
        this.callback = callback;
    }

    @Override
    public PictureDrawable loadPictures(Country country, boolean useGlide) {


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
                            if (resource == null) {
                            }

                            return false;
                        }
                    });
            requestBuilder.diskCacheStrategy(DiskCacheStrategy.DATA);
            requestBuilder
                    .load(uri)
                    .override(100, 100)
                    .into(new CustomTarget<PictureDrawable>() {
                        @Override
                        public void onResourceReady(@NonNull PictureDrawable resource, @Nullable Transition<? super PictureDrawable> transition) {
                         callback.callback();
                        }


                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
