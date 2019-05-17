package com.example.restcountries.view.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Picture;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.restcountries.R;
import com.example.restcountries.RestCountries;
import com.example.restcountries.interfaces.AdapterCallback;
import com.example.restcountries.model.country.Country;
import com.example.restcountries.model.svg.SvgSoftwareLayerSetter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder> {
    ArrayList<Country> countryList = new ArrayList<>();
    AdapterCallback callback;
    ArrayList<byte[]> byteArray = new ArrayList<>();
    boolean useGlide = false;

    Context context;

    public CountriesAdapter(Context context, AdapterCallback callback) {
        this.context = context;
        this.callback = callback;

    }

    @NonNull
    @Override
    public CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.main_list_m, parent, false);
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        return new CountriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesViewHolder holder, int position) {
        //При загузке изображений можно было бы использовать Glide повторно, что не нарушило бы принципа автономности.
        // Однако полный Preload бы не получился, тк картинки бы отрисовывались по мере прокрутки.Но для продакшн решения я бы потупил именно так
        // тк пользователю не принципиальна динамика загрузки картиок из кэша, но вероятно это сэкономило бы время процесса первичной загрузки из бд.

        if(useGlide) {
            RequestBuilder<PictureDrawable> requestBuilder;
            try {
                Uri uri = Uri.parse(countryList.get(position).getFlag());
                requestBuilder = Glide.with(RestCountries.getContext())
                        .as(PictureDrawable.class)
                        .transition(withCrossFade())
                        .listener(new SvgSoftwareLayerSetter());
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.DATA);
                requestBuilder
                        .load(uri)
                        .override(100, 100)
                        .into(holder.imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(byteArray.get(position), 0, byteArray.get(position).length));
        }
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

    public void apendData(Country country, byte[] bytes) {
        countryList.add(country);
        byteArray.add(bytes);
        notifyDataSetChanged();
    }
    //данный метод будет испоьзоваться при отображении картинок с помощю Glide
    public void apendData(Country country) {
        useGlide=true;
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
        }
    }
}
