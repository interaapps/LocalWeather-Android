package de.interaapps.localweather.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.NotNull;

import de.interaapps.localweather.R;

public class OpenWeatherIcons {

    private Context context;
    private String weatherIcon;

    public OpenWeatherIcons(Context context, String weatherIcon, @NotNull AppCompatImageView imageView)
    {
        this.context = context;
        this.weatherIcon = weatherIcon;

        imageView.setImageDrawable(getImage());
    }

    private Drawable getImage() {

        switch (weatherIcon)
        {
            case "01d":
                return ContextCompat.getDrawable(context, R.drawable.weather01d);

            case "02d":
                return ContextCompat.getDrawable(context, R.drawable.weather02d);

            case "03n":
            case "03d":
                return ContextCompat.getDrawable(context, R.drawable.weather03d);

            case "04n":
            case "04d":
                return ContextCompat.getDrawable(context, R.drawable.weather04d);

            case "09n":
            case "09d":
                return ContextCompat.getDrawable(context, R.drawable.weather09d);

            case "10d":
                return ContextCompat.getDrawable(context, R.drawable.weather10d);

            case "11n":
            case "11d":
                return ContextCompat.getDrawable(context, R.drawable.weather11d);

            case "13n":
            case "13d":
                return ContextCompat.getDrawable(context, R.drawable.weather13d);

            case "50n":
            case "50d":
                return ContextCompat.getDrawable(context, R.drawable.weather50d);

            default:
                return new ColorDrawable(Color.TRANSPARENT) {
                };
        }
    }
}
