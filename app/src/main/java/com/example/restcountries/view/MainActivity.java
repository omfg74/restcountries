package com.example.restcountries.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.restcountries.R;
import com.example.restcountries.contract.MainActivityContract;
import com.example.restcountries.presenter.MainActivityPresenter;
import com.example.restcountries.view.fragments.MainListFragment;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    MainActivityPresenter presenter;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        showSplashFregment();//for test
        presenter=new MainActivityPresenter(this);
        presenter.onCreate();
//        ActivityCompat.requestPermissions(MainActivity.this
//                ,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                1);

    }

    @Override
    public void showSplashFregment() {
//        SplashFragment splashFragment = new SplashFragment();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.main_fragment_place,splashFragment)
//                .commit();


    }

    @Override
    public void makeTost(String s) {

    }

    @Override
    public void changeFragment() {
        MainListFragment mainListFragment = new MainListFragment();
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

                // A null listener allows the button to dismiss the dialog and take no further action.
//                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
