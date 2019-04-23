package com.example.restcountries.presenter;

import com.arellomobile.mvp.MvpPresenter;
import com.example.restcountries.contract.MainActivityContract;
import com.example.restcountries.model.county.Country;
import com.example.restcountries.network.RetrofitInterface;
import com.example.restcountries.view.MainActivity;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    MainActivityContract.View view;
    MainActivityContract.Model model;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RetrofitInterface retrofitInterface;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;

    }



    @Override
    public void onCreate() {

    }

    private void loadAllData(){

        compositeDisposable.add(retrofitInterface
                .getAllCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<Country>>(){
                    @Override
                    public void onSuccess(List<Country> countries) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }
}
