package de.interaapps.localweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.os.ConfigurationCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import de.interaapps.localweather.utils.LocationFailedEnum;
import de.interaapps.localweather.utils.OpenWeatherIcons;
import de.interaapps.localweather.utils.Units;

public class MainActivity extends AppCompatActivity {

    private LocalWeather localWeather;
    private Weather weather;
    private boolean weatherReady = false;
    private AppCompatImageView weatherImage;
    private AppCompatTextView dayInfo;
    private AppCompatTextView weatherTemp;
    private AppCompatTextView weatherInfo;
    private AppCompatTextView weatherClouds;
    private AppCompatTextView weatherMaxTemp;
    private AppCompatTextView weatherMinTemp;
    private AppCompatTextView weatherTempKf;
    private AppCompatTextView locationCountry;
    private AppCompatTextView locationLat;
    private AppCompatTextView locationLon;
    private AppCompatTextView locationGroundLevel;
    private AppCompatTextView locationSeaLevel;
    private AppCompatTextView weatherBase;
    private AppCompatTextView weatherWindSpeed;
    private AppCompatTextView weatherWindDeg;
    private AppCompatTextView weatherSunrise;
    private AppCompatTextView weatherSunset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        initializeLogic();
    }

    private void initialize() {
        weatherImage = findViewById(R.id.weather_image);
        dayInfo = findViewById(R.id.day_info);
        weatherTemp = findViewById(R.id.weather_temp);
        weatherInfo = findViewById(R.id.weather_info);
        weatherClouds = findViewById(R.id.weather_clouds);
        weatherMaxTemp = findViewById(R.id.weather_max_temp);
        weatherMinTemp = findViewById(R.id.weather_min_temp);
        weatherTempKf = findViewById(R.id.weather_temp_kf);
        locationCountry = findViewById(R.id.location_country);
        locationLat = findViewById(R.id.location_lat);
        locationLon = findViewById(R.id.location_lon);
        locationGroundLevel = findViewById(R.id.location_ground_level);
        locationSeaLevel = findViewById(R.id.location_sea_level);
        weatherBase = findViewById(R.id.weather_base);
        weatherWindSpeed = findViewById(R.id.weather_wind_speed);
        weatherWindDeg = findViewById(R.id.weather_wind_deg);
        weatherSunrise = findViewById(R.id.weather_sunrise);
        weatherSunset = findViewById(R.id.weather_sunset);
    }

    private void initializeLogic() {
        Timer timer = new Timer ();
        TimerTask tenMinTask = new TimerTask () {
            @Override
            public void run () {
                updateLocalWeather();
            }
        };
        timer.schedule(tenMinTask, 0, 1000*60*10); //1000*60 = 1min *10 = 10min
    }

    private void updateLocalWeather() {
        localWeather = new LocalWeather(this,
                "OpenWeatherApiKey",
                true,
                true,
                new LocalWeather.Callbacks() {
                    @Override
                    public void onLocationFailure(LocationFailedEnum locationFailedEnum) {
                        Log.e("LOCATION-ERROR", locationFailedEnum.toString());
                    }

                    @Override
                    public void onWeatherFailure(Throwable throwable) {
                        Log.e("WEATHER-ERROR", throwable.getMessage());
                    }

                    @Override
                    public void onLocationSuccess() {
                        localWeather.fetchWeather();
                    }

                    @Override
                    public void onWeatherSuccess() {
                        weather = localWeather.getWeather();
                        weatherReady = true;
                        updateWeatherDetails();
                    }
                });

        localWeather.fetchLocation();
    }

    private void updateWeatherDetails() {
        //extra check but should always be true
        if (weatherReady) {
            new OpenWeatherIcons(getApplicationContext(), weather.getIcons()[0], weatherImage);
            String unit = (localWeather.getUnit() == Units.METRIC) ? "°C" : "°F" ;
            String speed = (localWeather.getUnit() == Units.METRIC) ? "km/h" : "mi/h" ;
            weatherTemp.setText(((int) weather.getTemp()) + unit);
            weatherInfo.setText("Description: " + weather.getDescriptions()[0]);
            weatherClouds.setText("Clouds: " + ((int) weather.getClouds()));
            weatherMaxTemp.setText("Max. Temp.: " + ((int) weather.getMaxTemp()) + unit);
            weatherMinTemp.setText("Min. Temp.: " + ((int) weather.getMinTemp()) + unit);
            weatherTempKf.setText("Temp Kf.: " + ((int) weather.getTempKf()));
            locationCountry.setText("Country: " + weather.getCountry());

            //real location based: localWeather.getLatitude();
            locationLat.setText("Latitude: " + weather.getLat());
            //real location based: localWeather.getLongitude();
            locationLon.setText("Longitude: " + weather.getLon());

            locationGroundLevel.setText("Ground Level: " + weather.getGrndLevel());
            locationSeaLevel.setText("Sea Level: " + weather.getSeaLevel());

            weatherBase.setText("Base: " + weather.getBase());
            weatherWindSpeed.setText("Wind Speed: " + weather.getWindSpeed() + speed);
            weatherWindDeg.setText("Wind Angle: " + ((int) weather.getWindDeg()) + "°");
            weatherSunrise.setText("Sunrise: " + weather.getSunrise());
            weatherSunset.setText("Sunset: " + weather.getSunset());
        }

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM", ConfigurationCompat.getLocales(getResources().getConfiguration()).get(0));
        dayInfo.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        localWeather.getLocationAccess().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        localWeather.getLocationAccess().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
