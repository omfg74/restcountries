package com.example.restcountries.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.restcountries.R;
import com.example.restcountries.contract.MainActivityContract;
import com.example.restcountries.presenter.MainActivityPresenter;
import com.example.restcountries.view.fragments.SplashFragment;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    MainActivityPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter=new MainActivityPresenter(this);
        presenter.onCreate();
    }

    @Override
    public void showSplashFregment() {
        SplashFragment splashFragment = new SplashFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_place,splashFragment)
                .commit();


    }
}
