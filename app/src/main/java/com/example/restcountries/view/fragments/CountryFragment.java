package com.example.restcountries.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.restcountries.R;
import com.example.restcountries.contract.CountryFragmentContract;
import com.example.restcountries.presenter.CountryFargmentPresenter;

public class CountryFragment extends DialogFragment implements CountryFragmentContract.View {
    private ImageView flagImageView;
    private TextView nameTextView, capitalTextView, currencyTextView;
    private CountryFragmentContract.Presenter presenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.country_fragment, container);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new CountryFargmentPresenter(this);
        flagImageView = view.findViewById(R.id.flagImageView);
        nameTextView = view.findViewById(R.id.country_name_text_view);
        capitalTextView = view.findViewById(R.id.capital_textView);
        currencyTextView = view.findViewById(R.id.currency_textView);
        presenter.onCreate(getArguments());
        presenter.loadFlag(flagImageView,getArguments());

    }

    @Override
    public void setName(String name) {
        nameTextView.setText(name);
    }

    @Override
    public void setCapital(String capital) {
        capitalTextView.setText(capital);
    }

    @Override
    public void setCurency(String curancy, String symbol) {
        currencyTextView.setText(curancy+" "+symbol);
    }

    @Override
    public void setFlag(ImageView imageView) {

    }
}
