package de.interaapps.localweather

import android.app.Activity
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback
import com.kwabenaberko.openweathermaplib.implementation.callbacks.ThreeHourForecastCallback
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather
import com.kwabenaberko.openweathermaplib.models.threehourforecast.ThreeHourForecast
import de.interaapps.localweather.utils.Lang
import de.interaapps.localweather.utils.LocationFailedEnum
import de.interaapps.localweather.utils.Units
import mumayank.com.airlocationlibrary.AirLocation
import java.util.ArrayList
import java.util.concurrent.TimeUnit
import kotlin.UnsupportedOperationException

class LocalWeather(private val activity: Activity, apiKey: String) {

    private var airLocation: AirLocation? = null
    private var openWeatherMapHelper: OpenWeatherMapHelper = OpenWeatherMapHelper(apiKey)

    private var currentLocation: Location? = null

    private var currentWeatherCallback: WeatherCallback? = null
    private var currentForecastWeatherCallback: ForecastWeatherCallback? = null

    /**
     * Specify if the users current location should be used
     */
    var useCurrentLocation: Boolean = true
        set(value) {
            if(!value) {
                updateCurrentLocation = false
            }
            field = value
        }

    /**
     * Specify if the users location should be updated automatically
     * Works only when [useCurrentLocation] is set to true
     * Checking [useCurrentLocation] is handled in setter
     */
    var updateCurrentLocation: Boolean = false
        set(value) {
            val data = if (useCurrentLocation) value else false
            if(!data) {
                airLocation?.stopLocationUpdates()
            }
            field = data
        }

    /**
     * Specify the update interval for the users location in milliseconds
     * Works only with [useCurrentLocation] and [updateCurrentLocation]
     */
    var updateLocationInterval: Long = TimeUnit.MINUTES.toMillis(5)

    @JvmField
    var lang: Lang = Lang.ENGLISH

    @JvmField
    var unit: Units = Units.METRIC

    @get:JvmSynthetic
    var weatherCallback: WeatherCallback? = currentWeatherCallback
        @Deprecated(message = WRITE_ONLY, level = DeprecationLevel.HIDDEN)
        get() = throw UnsupportedOperationException()
        set(value) {
            currentWeatherCallback = value
            field = currentWeatherCallback
        }

    @get:JvmSynthetic
    var forecastWeatherCallback: ForecastWeatherCallback? = currentForecastWeatherCallback
        @Deprecated(message = WRITE_ONLY, level = DeprecationLevel.HIDDEN)
        get() = throw UnsupportedOperationException()
        set(value) {
            currentForecastWeatherCallback = value
            field = currentForecastWeatherCallback
        }

    /**
     * Only used when [useCurrentLocation] is true
     */
    fun fetchCurrentLocation(currentLocationCallback: CurrentLocationCallback) {
        if(useCurrentLocation) {
            airLocation = AirLocation(activity, object: AirLocation.Callback {
                override fun aOnSuccess(locations: ArrayList<Location>) {
                    val newLocation = locations[locations.size-1]
                    if(currentLocation != newLocation) {
                        currentLocation = locations[locations.size-1]
                        currentLocation?.let { currentLocationCallback.onSuccess(it) }
                    }
                }

                override fun bOnFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {
                    // necessary to pass the exception to third-party applications
                    when(locationFailedEnum) {
                        AirLocation.LocationFailedEnum.DeviceInFlightMode -> {
                            currentLocationCallback.onFailure(LocationFailedEnum.DeviceInFlightMode)
                        }
                        AirLocation.LocationFailedEnum.LocationPermissionNotGranted -> {
                            currentLocationCallback.onFailure(LocationFailedEnum.LocationPermissionNotGranted)
                        }
                        AirLocation.LocationFailedEnum.LocationOptimizationPermissionNotGranted -> {
                            currentLocationCallback.onFailure(LocationFailedEnum.LocationOptimizationPermissionNotGranted)
                        }
                        AirLocation.LocationFailedEnum.HighPrecisionNaTryAgainPreferablyWithInternet -> {
                            currentLocationCallback.onFailure(LocationFailedEnum.HighPrecisionNaTryAgainPreferablyWithInternet)
                        }
                    }
                }
            }, !updateCurrentLocation, updateLocationInterval)
        }
    }

    private fun setSpecification(specificLocation: Location? = currentLocation): Location? {
        openWeatherMapHelper.setLang(lang.tag)
        openWeatherMapHelper.setUnits(unit.title)
        return specificLocation ?: currentLocation
    }

    /**
     * If you want to use users current location set [useCurrentLocation], [updateCurrentLocation] and [updateLocationInterval]
     * @param[specificLocation] used when specified
     */
    @JvmOverloads
    fun fetchCurrentWeatherByLocation(specificLocation: Location? = currentLocation) {
        setSpecification(specificLocation)?.let {
            openWeatherMapHelper.getCurrentWeatherByGeoCoordinates(it.latitude, it.longitude, object: CurrentWeatherCallback {
                override fun onSuccess(currentWeather: CurrentWeather?) {
                    currentWeather?.let { weather -> currentWeatherCallback?.onSuccess(Weather(weather)) }
                }

                override fun onFailure(throwable: Throwable?) {
                    currentWeatherCallback?.onFailure(throwable)
                }
            })
        }
    }

    fun fetchCurrentWeatherByLocation(latitude: Double = currentLocation?.latitude ?: 0.0, longitude: Double = currentLocation?.longitude ?: 0.0) {
        setSpecification(Location(LocationManager.GPS_PROVIDER).apply {
            setLatitude(latitude)
            setLongitude(longitude)
        })?.let {
            openWeatherMapHelper.getCurrentWeatherByGeoCoordinates(it.latitude, it.longitude, object: CurrentWeatherCallback {
                override fun onSuccess(currentWeather: CurrentWeather?) {
                    currentWeather?.let { weather -> currentWeatherCallback?.onSuccess(Weather(weather)) }
                }

                override fun onFailure(throwable: Throwable?) {
                    currentWeatherCallback?.onFailure(throwable)
                }
            })
        }
    }

    fun fetchCurrentWeatherByCityId(id: String) {
        setSpecification()
        openWeatherMapHelper.getCurrentWeatherByCityID(id, object: CurrentWeatherCallback {
            override fun onSuccess(currentWeather: CurrentWeather?) {
                currentWeather?.let { weather -> currentWeatherCallback?.onSuccess(Weather(weather)) }
            }

            override fun onFailure(throwable: Throwable?) {
                currentWeatherCallback?.onFailure(throwable)
            }
        })
    }

    fun fetchCurrentWeatherByCityName(name: String) {
        setSpecification()
        openWeatherMapHelper.getCurrentWeatherByCityName(name, object: CurrentWeatherCallback {
            override fun onSuccess(currentWeather: CurrentWeather?) {
                currentWeather?.let { weather -> currentWeatherCallback?.onSuccess(Weather(weather)) }
            }

            override fun onFailure(throwable: Throwable?) {
                currentWeatherCallback?.onFailure(throwable)
            }
        })
    }

    fun fetchCurrentWeatherByZipCode(zipCode: String) {
        setSpecification()
        openWeatherMapHelper.getCurrentWeatherByZipCode(zipCode, object: CurrentWeatherCallback {
            override fun onSuccess(currentWeather: CurrentWeather?) {
                currentWeather?.let { weather -> currentWeatherCallback?.onSuccess(Weather(weather)) }
            }

            override fun onFailure(throwable: Throwable?) {
                currentWeatherCallback?.onFailure(throwable)
            }
        })
    }

    /**
     * If you want to use users current location set [useCurrentLocation], [updateCurrentLocation] and [updateLocationInterval]
     * @param[specificLocation] used when specified
     */
    @JvmOverloads
    fun fetchForecastWeatherByLocation(specificLocation: Location? = currentLocation) {
        setSpecification(specificLocation)?.let {
            openWeatherMapHelper.getThreeHourForecastByGeoCoordinates(it.latitude, it.longitude, object: ThreeHourForecastCallback{
                override fun onSuccess(threeHourForecast: ThreeHourForecast?) {
                    threeHourForecast?.let { weather -> currentForecastWeatherCallback?.onSuccess(ForecastWeather(weather)) }
                }

                override fun onFailure(throwable: Throwable?) {
                    currentForecastWeatherCallback?.onFailure(throwable)
                }
            })
        }
    }

    fun fetchForecastWeatherByLocation(latitude: Double = currentLocation?.latitude ?: 0.0, longitude: Double = currentLocation?.longitude ?: 0.0) {
        setSpecification(Location(LocationManager.GPS_PROVIDER).apply {
            setLatitude(latitude)
            setLongitude(longitude)
        })?.let {
            openWeatherMapHelper.getThreeHourForecastByGeoCoordinates(it.latitude, it.longitude, object: ThreeHourForecastCallback{
                override fun onSuccess(threeHourForecast: ThreeHourForecast?) {
                    threeHourForecast?.let { weather -> currentForecastWeatherCallback?.onSuccess(ForecastWeather(weather)) }
                }

                override fun onFailure(throwable: Throwable?) {
                    currentForecastWeatherCallback?.onFailure(throwable)
                }
            })
        }
    }

    fun fetchForecastWeatherByCityId(id: String) {
        setSpecification()
        openWeatherMapHelper.getThreeHourForecastByCityID(id, object: ThreeHourForecastCallback{
            override fun onSuccess(threeHourForecast: ThreeHourForecast?) {
                threeHourForecast?.let { weather -> currentForecastWeatherCallback?.onSuccess(ForecastWeather(weather)) }
            }

            override fun onFailure(throwable: Throwable?) {
                currentForecastWeatherCallback?.onFailure(throwable)
            }
        })
    }

    fun fetchForecastWeatherByCityName(name: String) {
        setSpecification()
        openWeatherMapHelper.getThreeHourForecastByCityName(name, object: ThreeHourForecastCallback{
            override fun onSuccess(threeHourForecast: ThreeHourForecast?) {
                threeHourForecast?.let { weather -> currentForecastWeatherCallback?.onSuccess(ForecastWeather(weather)) }
            }

            override fun onFailure(throwable: Throwable?) {
                currentForecastWeatherCallback?.onFailure(throwable)
            }
        })
    }

    fun fetchForecastWeatherByZipCode(zipCode: String) {
        setSpecification()
        openWeatherMapHelper.getThreeHourForecastByZipCode(zipCode, object: ThreeHourForecastCallback{
            override fun onSuccess(threeHourForecast: ThreeHourForecast?) {
                threeHourForecast?.let { weather -> currentForecastWeatherCallback?.onSuccess(ForecastWeather(weather)) }
            }

            override fun onFailure(throwable: Throwable?) {
                currentForecastWeatherCallback?.onFailure(throwable)
            }
        })
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        airLocation?.onActivityResult(requestCode, resultCode, data)
    }

    fun onRequestPermissionResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        airLocation?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    interface CurrentLocationCallback {
        fun onSuccess(location: Location)
        fun onFailure(locationFailedEnum: LocationFailedEnum)
    }

    interface WeatherCallback {
        fun onSuccess(weather: Weather)
        fun onFailure(exception: Throwable?)
    }

    interface ForecastWeatherCallback {
        fun onSuccess(forecastWeather: ForecastWeather)
        fun onFailure(exception: Throwable?)
    }

    /**
     * unable to handle @see[CurrentWeatherCallback] and @see[ThreeHourForecastCallback] in LocalWeather class at once
     */
    private class CurrentWeatherHandler(private var weatherCallback: WeatherCallback? = null): CurrentWeatherCallback {
        override fun onSuccess(currentWeather: CurrentWeather?) {
            currentWeather?.let { weatherCallback?.onSuccess(Weather(it)) }
        }

        override fun onFailure(throwable: Throwable?) {
            weatherCallback?.onFailure(throwable)
        }
    }

    /**
     * unable to handle @see[CurrentWeatherCallback] and @see[ThreeHourForecastCallback] in LocalWeather class at once
     */
    private class ForecastWeatherHandler(private var forecastWeatherCallback: ForecastWeatherCallback? = null): ThreeHourForecastCallback {
        override fun onSuccess(threeHourForecast: ThreeHourForecast?) {
            threeHourForecast?.let { forecastWeatherCallback?.onSuccess(ForecastWeather(it)) }
        }

        override fun onFailure(throwable: Throwable?) {
            forecastWeatherCallback?.onFailure(throwable)
        }
    }

    companion object {
        private const val WRITE_ONLY = "Write only property"
    }

}