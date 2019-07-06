package de.interaapps.localweather;

import android.app.Activity;
import android.location.Location;

import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.ThreeHourForecastCallback;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;
import com.kwabenaberko.openweathermaplib.models.threehourforecast.ThreeHourForecast;

import org.jetbrains.annotations.NotNull;

import de.interaapps.localweather.utils.Lang;
import de.interaapps.localweather.utils.LocationFailedEnum;
import de.interaapps.localweather.utils.Units;
import mumayank.com.airlocationlibrary.AirLocation;

public abstract class LocalWeather {

    private Activity activity;
    private AirLocation airLocation;
    private Location location;
    private int timeout = 5000;
    private OpenWeatherMapHelper openWeatherMapHelper;
    private Units unit = Units.METRIC;
    private Lang language = Lang.ENGLISH;
    private CurrentWeather currentWeather;
    private ThreeHourForecast threeHourForecast;

    public LocalWeather(Activity activity, String apiKey) {
        this.activity = activity;
        openWeatherMapHelper = new OpenWeatherMapHelper(apiKey);
    }

    public LocalWeather(Activity activity, String apiKey, Units unit) {
        this.activity = activity;
        this.unit = unit;
        openWeatherMapHelper = new OpenWeatherMapHelper(apiKey);
    }

    public LocalWeather(Activity activity, String apiKey, Units unit, Lang language) {
        this.activity = activity;
        this.unit = unit;
        this.language = language;
        openWeatherMapHelper = new OpenWeatherMapHelper(apiKey);
    }

    private void setSpecification() {
        openWeatherMapHelper.setUnits(unit.getTitle());
        openWeatherMapHelper.setLang(language.getTag());
    }

    public void setLanguage(Lang language) {
        this.language = language;
    }

    public Lang getLanguage() {
        return language;
    }

    public void setUnit(Units unit) {
        this.unit = unit;
    }

    public Units getUnit() {
        return unit;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setLocation() {
        airLocation = new AirLocation(activity, false, true, new AirLocation.Callbacks() {
            @Override
            public void onSuccess(@NotNull Location location) {
                LocalWeather.this.location = location;
                onLocationSuccess();
            }

            @Override
            public void onFailed(@NotNull AirLocation.LocationFailedEnum locationFailedEnum) {
                switch (locationFailedEnum) {
                    case DeviceInFlightMode:
                        onLocationFailure(LocationFailedEnum.DeviceInFlightMode);
                        break;
                    case LocationPermissionNotGranted:
                        onLocationFailure(LocationFailedEnum.LocationPermissionNotGranted);
                        break;

                    case LocationOptimizationPermissionNotGranted:
                        onLocationFailure(LocationFailedEnum.LocationOptimizationPermissionNotGranted);
                        break;

                    case HighPrecisionNA_TryAgainPreferablyWithInternet:
                        onLocationFailure(LocationFailedEnum.HighPrecisionNA_TryAgainPreferablyWithInternet);
                        break;
                }
            }
        });
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLatitude(double latitude) {
        location.setLatitude(latitude);
    }

    public double getLatitude() {
        return location.getLatitude();
    }

    public void setLongitude(double longitude) {
        location.setLongitude(longitude);
    }

    public double getLongitude() {
        return  location.getLongitude();
    }

    public void setAltitude(double altitude) {
        location.setAltitude(altitude);
    }

    public double getAltitude() {
        return location.getAltitude();
    }

    public void listenWeather() {
        setSpecification();

        openWeatherMapHelper.getCurrentWeatherByGeoCoordinates(location.getLatitude(), location.getLongitude(), new CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                LocalWeather.this.currentWeather = currentWeather;
                onWeatherSuccess();
            }

            @Override
            public void onFailure(Throwable throwable) {
                onWeatherFailure(throwable);
            }
        });
    }

    public void forecastWeather() {
        setSpecification();

        openWeatherMapHelper.getThreeHourForecastByGeoCoordinates(location.getLatitude(), location.getLongitude(), new ThreeHourForecastCallback() {
            @Override
            public void onSuccess(ThreeHourForecast threeHourForecast) {
                LocalWeather.this.threeHourForecast = threeHourForecast;
                onWeatherSuccess();
            }

            @Override
            public void onFailure(Throwable throwable) {
                onWeatherFailure(throwable);
            }
        });
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public Weather getWeather() {
        return new Weather(currentWeather);
    }

    public void setForecastWeather(ThreeHourForecast threeHourForecast) {
        this.threeHourForecast = threeHourForecast;
    }

    public ForecastWeather getForecastWeather() {
        return new ForecastWeather(threeHourForecast);
    }

    abstract void onLocationFailure(LocationFailedEnum locationFailedEnum);
    abstract void onWeatherFailure(Throwable throwable);
    abstract void onLocationSuccess();
    abstract void onWeatherSuccess();
}
