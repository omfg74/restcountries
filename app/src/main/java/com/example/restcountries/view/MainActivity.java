package com.example.restcountries.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.restcountries.Constants;
import com.example.restcountries.R;
import com.example.restcountries.contract.MainActivityContract;
import com.example.restcountries.model.country.Country;
import com.example.restcountries.presenter.MainActivityPresenter;
import com.example.restcountries.view.fragments.MainListFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    MainActivityPresenter presenter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        presenter = new MainActivityPresenter(this);

        presenter.onCreate();
    }

    @Override
    public void makeTost(String s) {
        Snackbar.make(progressBar,s,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void changeFragment(ArrayList<Country> countries, HashMap<String, PictureDrawable> pictureDrawableMap) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.COUNTRIES_KEY, countries);
        bundle.putSerializable(Constants.COUNTRIES_FLAG, pictureDrawableMap);
        MainListFragment mainListFragment = new MainListFragment();
        mainListFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_place, mainListFragment)
                .commit();

    }

    @Override
    public void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("No Internet")
                .setMessage("Please check the internet connection and restart the app")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.exit();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void progressBarSetVisible() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void progressBarSetInvisible() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void progressBarSetProgress(int progress) {
        progressBar.setProgress(progress);
    }

    @Override
    public void progressBarSetSize(int size) {
        progressBar.setMax(size);
    }
}
