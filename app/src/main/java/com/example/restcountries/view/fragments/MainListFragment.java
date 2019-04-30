package com.example.restcountries.view.fragments;

import android.app.Activity;
import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restcountries.R;
import com.example.restcountries.RestCountries;
import com.example.restcountries.contract.MainListFragmentContract;
import com.example.restcountries.model.county.Country;
import com.example.restcountries.model.realm.RealmCounty;
import com.example.restcountries.presenter.MainListFragmentPresenter;
import com.example.restcountries.view.adapters.CountriesAdapter;

public class MainListFragment extends Fragment implements MainListFragmentContract.View {
    RecyclerView recyclerView;
    View view;
    CountriesAdapter countriesAdapter;
    MainListFragmentContract.Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.main_list_fragment,container,false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(RestCountries.getContext()));
        presenter = new MainListFragmentPresenter(this);
        recyclerView.setAdapter(countriesAdapter);
    }

    @Override
    public void postDataToList(Country country) {

    }

    @Override
    public void postPicture(PictureDrawable pictureDrawable, RealmCounty country) {
        countriesAdapter.apendData(pictureDrawable,country);
    }
}
