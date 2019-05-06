package com.example.restcountries.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.restcountries.interfaces.AdapterCallback;
import com.example.restcountries.model.realm.RealmCounty;
import com.example.restcountries.model.svg.SvgSoftwareLayerSetter;
import com.example.restcountries.view.fragments.CountryFragment;

import io.realm.RealmList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder> {
    RealmList<RealmCounty> countryList = new RealmList<>();
    AdapterCallback callback;

    Context context;
    public CountriesAdapter(Context context, AdapterCallback callback) {
        this.context = context;
        this.callback = callback;

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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("name",countryList.get(position).getName());
                bundle.putString("capital",countryList.get(position).getCapital());
                bundle.putString("currency",countryList.get(position).getCurrency().get(0).getName());
                bundle.putString("currency_symbol",countryList.get(position).getCurrency().get(0).getSymbol());
                bundle.putString("flag",countryList.get(position).getFlagLink());
                callback.changeFragment(bundle);

            }
        });
    }

    @Override
    public int getItemCount() {
            return countryList.size();

    }

    public void apendData(PictureDrawable pictureDrawable, RealmCounty country) {
        countryList.add(country);
        Logger.toLog("currency  "+country.getCurrency().get(0).getName());
        this.pictureDrawable = pictureDrawable;
        notifyDataSetChanged();
    }

    public void apendData(RealmCounty country) {
        countryList.add(country);
        Logger.toLog("currency  "+country.getCurrency().get(0).getName());

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
