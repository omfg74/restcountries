package com.example.restcountries.Utils;

import android.graphics.drawable.PictureDrawable;

import com.bumptech.glide.RequestBuilder;
import com.example.restcountries.Logger;
import com.example.restcountries.interfaces.CountryLoadderInterface;
import com.example.restcountries.model.county.Country;
import com.example.restcountries.network.RetrofitInterface;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class NetworkLoader implements CountryLoadderInterface {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RequestBuilder<PictureDrawable> requestBuilder;
    private RetrofitInterface retrofitInterface;
    @Override
    public void load() {

        compositeDisposable.add(
        loadAllData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
//                        view.changeFragment();
                    }
                })
                .subscribe(
                        new Consumer<Country>() {
                            @Override
                            public void accept(Country country) throws Exception {
//                                writeToRealm(country);
//                        Uri uri = Uri.parse(country.getFlag());
//                        requestBuilder = Glide.with((Activity)view)
//                                .as(PictureDrawable.class)
////                .placeholder(R.drawable.image_loading)
////                .error(R.drawable.image_error)
//                                .transition(withCrossFade())
//                                .listener(new SvgSoftwareLayerSetter());
//
//                        requestBuilder.load(uri).into(new Target<PictureDrawable>() {
//                            @Override
//                            public void onLoadStarted(@Nullable Drawable placeholder) {
//
//                                Logger.toLog("onLoadStarted");
//                            }
//
//                            @Override
//                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
//                                Logger.toLog("onLoadFailed");
//                            }
//
//                            @Override
//                            public void onResourceReady(@NonNull PictureDrawable resource, @Nullable Transition<? super PictureDrawable> transition) {
//                                Logger.toLog(""+resource);
//                            }
//
//                            @Override
//                            public void onLoadCleared(@Nullable Drawable placeholder) {
//                                Logger.toLog("onLoadCleared");
//                            }
//
//                            @Override
//                            public void getSize(@NonNull SizeReadyCallback cb) {
//                                Logger.toLog("size "+ cb);
//                            }
//
//                            @Override
//                            public void removeCallback(@NonNull SizeReadyCallback cb) {
//
//                            }
//
//                            @Override
//                            public void setRequest(@Nullable Request request) {
//
//                            }
//
//                            @Nullable
//                            @Override
//                            public Request getRequest() {
//                                return null;
//                            }
//
//                            @Override
//                            public void onStart() {
//
//                            }
//
//                            @Override
//                            public void onStop() {
//
//                            }
//
//                            @Override
//                            public void onDestroy() {
//
//                            }
//                        });
//

                            }

//                        }));

    }));
    }


    private Observable<Country> loadAllData(){
        return retrofitInterface
                .getAllCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.toLog("Error ");
                        Logger.toLog(" "+throwable.getMessage());
                    }
                })
                .flatMap(new Function<List<Country>, ObservableSource<Country>>() {
                    @Override
                    public ObservableSource<Country> apply(List<Country> countries) throws Exception {
                        Logger.toLog("get all data");
                        Logger.toLog("country "+countries.size());
                        return Observable.fromIterable(countries).subscribeOn(Schedulers.io());
                    }
                });
    }
}
