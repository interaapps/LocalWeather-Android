package de.interaapps.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.os.ConfigurationCompat;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.interaapps.localweather.utils.Lang;
import de.interaapps.localweather.utils.LocationFailedEnum;
import de.interaapps.example.utils.OpenWeatherIcons;
import de.interaapps.localweather.utils.Units;
import de.interaapps.localweather.LocalWeather;
import de.interaapps.localweather.Weather;

public class MainActivity extends AppCompatActivity {

    private LocalWeather localWeather;
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
        localWeather = new LocalWeather(this, "OpenWeatherApiKey");
    }

    private void initializeLogic() {
        localWeather.setUseCurrentLocation(true);
        localWeather.setUpdateCurrentLocation(true);
        localWeather.lang = Lang.ENGLISH;
        localWeather.unit = Units.METRIC;

        localWeather.setWeatherCallback(new LocalWeather.WeatherCallback() {
            @Override
            public void onSuccess(Weather weather) {
                updateWeatherDetails(weather);
            }

            @Override
            public void onFailure(Throwable exception) {
                Log.e("Weather fetching Error", exception.getMessage());
            }
        });

        localWeather.fetchCurrentLocation(new LocalWeather.CurrentLocationCallback() {
            @Override
            public void onSuccess(Location location) {
                Log.e("Location Success", location.toString());
                localWeather.fetchCurrentWeatherByLocation(location);
            }

            @Override
            public void onFailure(LocationFailedEnum locationFailedEnum) {
                Log.e("Location fetching Error", locationFailedEnum.toString());
            }
        });
    }

    private void updateWeatherDetails(Weather weather) {
            new OpenWeatherIcons(getApplicationContext(), weather.getIcons()[0], weatherImage);
            String unit = (localWeather.unit == Units.METRIC) ? "°C" : "°F" ;
            String speed = (localWeather.unit == Units.METRIC) ? "km/h" : "mi/h" ;
            weatherTemp.setText(((int) weather.getTemperature()) + unit);
            weatherInfo.setText("Description: " + weather.getDescriptions()[0]);
            weatherClouds.setText("Clouds: " + ((int) weather.getClouds()));
            weatherMaxTemp.setText("Max. Temp.: " + ((int) weather.getMaxTemperature()) + unit);
            weatherMinTemp.setText("Min. Temp.: " + ((int) weather.getMinTemperature()) + unit);
            weatherTempKf.setText("Temp Kf.: " + ((int) weather.getTemperatureKf()));
            locationCountry.setText("Country: " + weather.getCountry());

            //real location based: localWeather.getLatitude();
            locationLat.setText("Latitude: " + weather.getLatitude());
            //real location based: localWeather.getLongitude();
            locationLon.setText("Longitude: " + weather.getLongitude());

            locationGroundLevel.setText("Ground Level: " + weather.getGroundLevel());
            locationSeaLevel.setText("Sea Level: " + weather.getSeaLevel());

            weatherBase.setText("Base: " + weather.getBase());
            weatherWindSpeed.setText("Wind Speed: " + weather.getWindSpeed() + speed);
            weatherWindDeg.setText("Wind Angle: " + ((int) weather.getWindAngle()) + "°");
            weatherSunrise.setText("Sunrise: " + weather.getSunrise());
            weatherSunset.setText("Sunset: " + weather.getSunset());

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM", ConfigurationCompat.getLocales(getResources().getConfiguration()).get(0));
        dayInfo.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        localWeather.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        localWeather.onRequestPermissionResult(requestCode, permissions, grantResults);
    }
}
