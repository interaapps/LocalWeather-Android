package de.interaapps.localweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import de.interaapps.localweather.utils.LocationFailedEnum;

public class MainActivity extends AppCompatActivity {

    private LocalWeather localWeather;
    private Weather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localWeather = new LocalWeather(this, "OpenWeatherApiKey") {
            @Override
            void onLocationFailure(@NotNull LocationFailedEnum locationFailedEnum) {
                Log.e("LOCATION-ERROR", locationFailedEnum.toString());
            }

            @Override
            void onWeatherFailure(@NotNull Throwable throwable) {
                Log.e("WEATHER-ERROR", throwable.getMessage());
            }

            @Override
            void onLocationSuccess() {
                localWeather.listenWeather();
            }

            @Override
            void onWeatherSuccess() {
                weather = localWeather.getWeather();
            }
        };

        localWeather.setLocation();
    }
}
