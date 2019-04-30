package com.example.restcountries.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.restcountries.Logger;
import com.example.restcountries.R;
import com.example.restcountries.RestCountries;
import com.example.restcountries.model.realm.RealmCounty;
import com.example.restcountries.model.svg.SvgSoftwareLayerSetter;

import io.realm.RealmList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>{
    RealmList<RealmCounty> countryList = new RealmList<>();

    Context context;
    public CountriesAdapter(Context context) {
        this.context = context;

    }

    RequestBuilder<PictureDrawable>requestBuilder;
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
//        holder.imageView.setImageDrawable(pictureDrawable);
        try {
            Uri uri = Uri.parse(countryList.get(position).getFlagLink());
            requestBuilder = Glide.with((Activity) context)
                    .as(PictureDrawable.class)
                    .transition(withCrossFade())
                    .listener(new SvgSoftwareLayerSetter());
            requestBuilder
                    .load(uri)
                    .into(holder.imageView);
//                .into(holder.imageView);
        }catch (Exception e){e.printStackTrace();}

        holder.textView.setText(countryList.get(position).getName());
    }

    @Override
    public int getItemCount() {
            return countryList.size();

    }

    public void apendData(PictureDrawable pictureDrawable, RealmCounty country) {
        countryList.add(country);
        this.pictureDrawable = pictureDrawable;
        notifyDataSetChanged();
    }

    public void apendData(RealmCounty country) {
        countryList.add(country);


        notifyDataSetChanged();
    }

    public class CountriesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public CountriesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recyClerViewImageView);
            textView = itemView.findViewById(R.id.recyclerViewTextView);
//            try {
//                Uri uri = Uri.parse("https://restcountries.eu/data/afg.svg");
//                requestBuilder = Glide.with((Activity) context)
//                        .as(PictureDrawable.class)
//                        .transition(withCrossFade())
//                        .listener(new SvgSoftwareLayerSetter());
//                requestBuilder
//                        .load(uri)
//                        .into(imageView);
//
////                .into(holder.imageView);
//            }catch (Exception e){e.printStackTrace();}

        }
    }
}
