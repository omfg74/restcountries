package com.example.restcountries.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.example.restcountries.R;
import com.example.restcountries.interfaces.AdapterCallback;
import com.example.restcountries.model.country.Country;
import com.example.restcountries.model.svg.SvgSoftwareLayerSetter;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder> {
    ArrayList<Country> countryList = new ArrayList<>();
    AdapterCallback callback;
    PictureDrawable pictureDrawable;

    Context context;

    public CountriesAdapter(Context context, AdapterCallback callback) {
        this.context = context;
        this.callback = callback;

    }

    RequestBuilder<PictureDrawable> requestBuilder;
//    PictureDrawable pictureDrawable;

    @NonNull
    @Override
    public CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.main_list_m, parent, false);
        return new CountriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesViewHolder holder, int position) {
//        try {
//            Uri uri = Uri.parse(countryList.get(position).getFlag());
//            requestBuilder = Glide.with((Activity) context)
//                    .as(PictureDrawable.class)
//                    .transition(withCrossFade())
//                    .listener(new SvgSoftwareLayerSetter());
//            requestBuilder
//                    .load(uri)
//                    .into(holder.imageView);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        holder.imageView.setImageDrawable(pictureDrawable);
        holder.textView.setText(countryList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("name", countryList.get(position).getName());
                bundle.putString("capital", countryList.get(position).getCapital());
                bundle.putString("currency", countryList.get(position).getCurrencies().get(0).getName());
                bundle.putString("currency_symbol", countryList.get(position).getCurrencies().get(0).getSymbol());
                bundle.putString("flag", countryList.get(position).getFlag());
                callback.changeFragment(bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public void apendData(Country country, PictureDrawable pictureDrawable) {
        countryList.add(country);
        this.pictureDrawable = pictureDrawable;
        notifyDataSetChanged();
    }

    public class CountriesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public CountriesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recyClerViewImageView);
            textView = itemView.findViewById(R.id.recyclerViewTextView);
        }
    }
}
