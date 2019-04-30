package com.example.restcountries.view.adapters;

import android.graphics.drawable.PictureDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restcountries.R;
import com.example.restcountries.model.realm.RealmCounty;

import io.realm.RealmList;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>{
    RealmList<RealmCounty> countryList;

    PictureDrawable pictureDrawable;
    @NonNull
    @Override
    public CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.main_list_m, parent,false);
        return new CountriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesViewHolder holder, int position) {
        holder.imageView.setImageDrawable(pictureDrawable);
        holder.textView.setText(countryList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public void apendData(PictureDrawable pictureDrawable, RealmCounty country) {
        countryList.add(country);
        this.pictureDrawable = pictureDrawable;
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
