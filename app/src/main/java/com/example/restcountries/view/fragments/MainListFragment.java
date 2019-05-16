package com.example.restcountries.view.fragments;

import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restcountries.R;
import com.example.restcountries.RestCountries;
import com.example.restcountries.contract.MainListFragmentContract;
import com.example.restcountries.interfaces.AdapterCallback;
import com.example.restcountries.model.country.Country;
import com.example.restcountries.presenter.MainListFragmentPresenter;
import com.example.restcountries.view.adapters.CountriesAdapter;

public class MainListFragment extends Fragment implements MainListFragmentContract.View, AdapterCallback {
    RecyclerView recyclerView;
    View view;
    CountriesAdapter countriesAdapter;
    MainListFragmentContract.Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainListFragmentPresenter(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_list_fragment, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(RestCountries.getContext(), 3));
        presenter = new MainListFragmentPresenter(this);
        countriesAdapter = new CountriesAdapter(getActivity(), this);
        recyclerView.setAdapter(countriesAdapter);
        presenter.onCreate(getArguments());

    }

    @Override
    public void postDataToList(Country country, PictureDrawable pictureDrawable) {
        Log.d("Log","pic "+pictureDrawable);
        countriesAdapter.apendData(country, pictureDrawable);
    }

    @Override
    public void changeFragment(Bundle bundle) {
        CountryFragment countryFragment = new CountryFragment();
        countryFragment.setArguments(bundle);
        countryFragment.show(getActivity().getSupportFragmentManager(), "dialog_fragment");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putSerializable();
        presenter.onSaveInstanceState(outState);
    }
}
