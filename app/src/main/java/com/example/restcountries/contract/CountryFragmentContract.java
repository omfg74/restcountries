package com.example.restcountries.contract;

import android.os.Bundle;
import android.widget.ImageView;

public interface CountryFragmentContract {
    interface View {
        void setName(String name);

        void setCapital(String capital);

        void setCurency(String curancy, String symbol);

        void setFlag(ImageView imageView);
    }

    interface Presenter {
        void onCreate(Bundle bundle);

        void loadFlag(ImageView imageView, Bundle bundle);

    }

    interface Model {

    }
}
