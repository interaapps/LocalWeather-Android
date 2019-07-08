package de.interaapps.localweather;

import android.app.Activity;
import android.location.Location;

import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.ThreeHourForecastCallback;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;
import com.kwabenaberko.openweathermaplib.models.threehourforecast.ThreeHourForecast;

import de.interaapps.localweather.utils.Lang;
import de.interaapps.localweather.utils.LocationFailedEnum;
import de.interaapps.localweather.utils.Units;

public class LocalWeather {

    private Activity activity;
    private Location location;
    private OpenWeatherMapHelper openWeatherMapHelper;
    private Units unit = Units.METRIC;
    private Lang language = Lang.ENGLISH;
    private CurrentWeather currentWeather;
    private ThreeHourForecast threeHourForecast;
    private CurrentLocation currentLocation;
    private LocalWeather.Callbacks callbacks;
    private boolean shouldWeRequestPermission = false;
    private boolean shouldWeRequestOptimization = false;

    public LocalWeather(Activity activity, String apiKey, LocalWeather.Callbacks callbacks) {
        this.activity = activity;
        this.callbacks = callbacks;
        openWeatherMapHelper = new OpenWeatherMapHelper(apiKey);
    }

    public LocalWeather(Activity activity,
                        String apiKey,
                        boolean shouldWeRequestPermission,
                        boolean shouldWeRequestOptimization,
                        LocalWeather.Callbacks callbacks) {
        this.activity = activity;
        this.callbacks = callbacks;
        this.shouldWeRequestPermission = shouldWeRequestPermission;
        this.shouldWeRequestOptimization = shouldWeRequestOptimization;
        openWeatherMapHelper = new OpenWeatherMapHelper(apiKey);
    }

    public LocalWeather(Activity activity, String apiKey, Units unit, LocalWeather.Callbacks callbacks) {
        this.activity = activity;
        this.unit = unit;
        this.callbacks = callbacks;
        openWeatherMapHelper = new OpenWeatherMapHelper(apiKey);
    }

    public LocalWeather(Activity activity,
                        String apiKey,
                        Units unit,
                        boolean shouldWeRequestPermission,
                        boolean shouldWeRequestOptimization,
                        LocalWeather.Callbacks callbacks) {
        this.activity = activity;
        this.unit = unit;
        this.callbacks = callbacks;
        this.shouldWeRequestPermission = shouldWeRequestPermission;
        this.shouldWeRequestOptimization = shouldWeRequestOptimization;
        openWeatherMapHelper = new OpenWeatherMapHelper(apiKey);
    }

    public LocalWeather(Activity activity, String apiKey, Units unit, Lang language, LocalWeather.Callbacks callbacks) {
        this.activity = activity;
        this.unit = unit;
        this.language = language;
        this.callbacks = callbacks;
        openWeatherMapHelper = new OpenWeatherMapHelper(apiKey);
    }

    public LocalWeather(Activity activity,
                        String apiKey,
                        Units unit,
                        Lang language,
                        boolean shouldWeRequestPermission,
                        boolean shouldWeRequestOptimization,
                        LocalWeather.Callbacks callbacks) {
        this.activity = activity;
        this.unit = unit;
        this.language = language;
        this.callbacks = callbacks;
        this.shouldWeRequestPermission = shouldWeRequestPermission;
        this.shouldWeRequestOptimization = shouldWeRequestOptimization;
        openWeatherMapHelper = new OpenWeatherMapHelper(apiKey);
    }

    private void setSpecification() {
        openWeatherMapHelper.setUnits(unit.getTitle());
        openWeatherMapHelper.setLang(language.getTag());
    }

    public void setLanguage(Lang language) {
        this.language = language;
        openWeatherMapHelper.setLang(language.getTag());
    }

    public Lang getLanguage() {
        return language;
    }

    public void setUnit(Units unit) {
        this.unit = unit;
        openWeatherMapHelper.setUnits(unit.getTitle());
    }

    public Units getUnit() {
        return unit;
    }

    public void setRequestPermission(boolean shouldWeRequestPermission) {
        this.shouldWeRequestPermission = shouldWeRequestPermission;
    }

    public boolean getRequestPermission() {
        return shouldWeRequestPermission;
    }

    public void setRequestOptimization(boolean shouldWeRequestOptimization){
        this.shouldWeRequestOptimization = shouldWeRequestOptimization;
    }

    public boolean getRequestOptimization(){
        return shouldWeRequestOptimization;
    }

    public void fetchLocation() {
        currentLocation = new CurrentLocation(activity, shouldWeRequestPermission, shouldWeRequestOptimization, new CurrentLocation.Callbacks() {
            @Override
            public void onSuccess(Location location) {
                LocalWeather.this.location = location;
                callbacks.onLocationSuccess();
            }

            @Override
            public void onFailed(LocationFailedEnum locationFailedEnum) {
                callbacks.onLocationFailure(locationFailedEnum);
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

    public CurrentLocation getLocationAccess() {
        return currentLocation;
    }

    public void fetchWeather() {
        setSpecification();

        openWeatherMapHelper.getCurrentWeatherByGeoCoordinates(location.getLatitude(), location.getLongitude(), new CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                LocalWeather.this.currentWeather = currentWeather;
                callbacks.onWeatherSuccess();
            }

            @Override
            public void onFailure(Throwable throwable) {
                callbacks.onWeatherFailure(throwable);
            }
        });
    }

    public void forecastWeather() {
        setSpecification();

        openWeatherMapHelper.getThreeHourForecastByGeoCoordinates(location.getLatitude(), location.getLongitude(), new ThreeHourForecastCallback() {
            @Override
            public void onSuccess(ThreeHourForecast threeHourForecast) {
                LocalWeather.this.threeHourForecast = threeHourForecast;
                callbacks.onWeatherSuccess();
            }

            @Override
            public void onFailure(Throwable throwable) {
                callbacks.onWeatherFailure(throwable);
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

    interface Callbacks {
        void onLocationFailure(LocationFailedEnum locationFailedEnum);

        void onWeatherFailure(Throwable throwable);

        void onLocationSuccess();

        void onWeatherSuccess();
    }
}
