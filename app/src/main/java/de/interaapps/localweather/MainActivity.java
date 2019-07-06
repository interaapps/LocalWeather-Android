package de.interaapps.localweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import de.interaapps.localweather.utils.LocationFailedEnum;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalWeather localWeather = new LocalWeather(this, "OpenWeatherApiKey") {
            @Override
            void onLocationFailure(@NotNull LocationFailedEnum locationFailedEnum) {
                Log.e("LOCATION-ERROR", locationFailedEnum.toString());
            }

            @Override
            void onWeatherFailure(@NotNull Throwable throwable) {
                Log.e("WEATHER-ERROR", throwable.getMessage());
            }
        };
        localWeather.setLocation();
        localWeather.listenWeather();
    }
}
