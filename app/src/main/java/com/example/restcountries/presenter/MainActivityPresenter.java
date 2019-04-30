package com.example.restcountries.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ahmadrosid.svgloader.SvgSoftwareLayerSetter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.restcountries.Logger;
import com.example.restcountries.contract.MainActivityContract;
import com.example.restcountries.model.county.Country;
import com.example.restcountries.model.realm.RealmCounty;
import com.example.restcountries.model.realm.RealmCurrency;
import com.example.restcountries.network.RetrofitClient;
import com.example.restcountries.network.RetrofitInterface;

import java.util.List;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmList;

import static com.bumptech.glide.Glide.with;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    MainActivityContract.View view;
    MainActivityContract.Model realmCounrty;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RequestBuilder<PictureDrawable> requestBuilder;
    private RetrofitInterface retrofitInterface;
    int i = 0;
    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
        this.realmCounrty = new RealmCounty();

    }



    @Override
    public void onCreate() {
        retrofitInterface = RetrofitClient.getInstance().create(RetrofitInterface.class);
        compositeDisposable.add(
        loadAllData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.toLog("on error");
                        view.makeTost("Error loading data");
                    }
                })
                .subscribe(
                        new Consumer<Country>() {
                    @Override
                    public void accept(Country country) throws Exception {
                        Uri uri = Uri.parse(country.getFlag());
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

                                Logger.toLog("onStart"+""+i++);
                            }

                            @Override
                            public void onStop() {

                            }

                            @Override
                            public void onDestroy() {

                            }
                        });
                        view.changeFragment();

                    }

                }));

    }

//    private Observable<>getBitmaps(Country country) {
//        try {
//            return retrofitInterface
//                    .getFlag(country.getFlag())
//                    .map(new Function<Bitmap, Bitmap>() {
//                        @Override
//                        public Bitmap apply(Bitmap bitmap) throws Exception {
//
//                            return bitmap;
//                        }
//                    }).subscribeOn(Schedulers.io());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        return Observable.create(new ObservableOnSubscribe<Void>() {
//            @Override
//            public void subscribe(ObservableEmitter<Void> emitter) throws Exception {
//              FutureTarget<Bitmap> bitmapFutureTarget = Glide
//                        .with((Activity) view)
//                        .asBitmap()
//                        .load(country.getFlag())
//                       .submit(2000,2000);
//                Bitmap b= bitmapFutureTarget.get();
//
//                emitter.onNext();
//                emitter.onComplete();
//            }
//        });
//    }
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

    private void writeToRealm(final List<Country>countries){
        //write data from internet to realm
        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
        try {
            realm.executeTransactionAsync((realm1) -> {
                for (int i = 0; i < countries.size(); i++) {
                    realmCounrty = realm1.createObject(RealmCounty.class);
                    RealmList<RealmCurrency> realmCurrencyList = new RealmList<>();
                    realmCounrty.setName(countries.get(i).getName());
                    realmCounrty.setCapital(countries.get(i).getCapital());
                    for (int j = 0; j < countries.get(i).getCurrencies().size(); j++) {
                        RealmCurrency realmCurrency = realm.createObject(RealmCurrency.class);
                        realmCurrency.setName(countries.get(i).getCurrencies().get(j).getName());
                        realmCurrency.setCode(countries.get(i).getCurrencies().get(j).getCode());
                        realmCurrency.setSymbol(countries.get(i).getCurrencies().get(j).getSymbol());
                        realmCurrencyList.add(realmCurrency);
                    }
                    realmCounrty.setCurrency(realmCurrencyList);
                    realmCounrty.setFlagLink(countries.get(i).getFlag());
                }
            });
        }catch (Exception e){
         e.printStackTrace();
        }finally {


            realm.close();
        }
    }

    private void checkIfRealmIsEmpty(){
        Realm realm = Realm.getDefaultInstance();
        RealmCounty realmCounty = realm.where(RealmCounty.class).findFirst();
        if (realm!=null){
            loadDataFromRealm();
        }else {
            if(checkINternetConnection()){
                loadAllData();
            }else {
                allertNoInternet();
            }
        }

    }

    private void loadDataFromRealm() {
        //load data from realm and pass to list fargmant
    }

    private void allertNoInternet() {
        //make dialog to tur internet on or to exit
    }

    private boolean checkINternetConnection() {
//check if internet is on
        return false;
    }


    private String generateImageName(String countryName){
        String[]str  = countryName.split(" ");
        return str[0];
    }

}
