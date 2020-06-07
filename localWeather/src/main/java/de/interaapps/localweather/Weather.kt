package de.interaapps.localweather

import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather

class Weather(private val currentWeather: CurrentWeather) {

    val descriptions: Array<String>
        get() {
            val desc = mutableListOf<String>()
            for(weather in currentWeather.weather) {
                desc.add(weather.description)
            }
            return desc.toTypedArray()
        }

    val icons: Array<String>
        get() {
            val ico = mutableListOf<String>()
            for(weather in currentWeather.weather) {
                ico.add(weather.icon)
            }
            return ico.toTypedArray()
        }

    val mainInfo: Array<String>
        get() {
            val main = mutableListOf<String>()
            for (weather in currentWeather.weather) {
                main.add(weather.main)
            }
            return main.toTypedArray()
        }

    val ids: Array<Long>
        get() {
            val id = mutableListOf<Long>()
            for (weather in currentWeather.weather) {
                id.add(weather.id)
            }
            return id.toTypedArray()
        }

    val temperature: Double = currentWeather.main?.temp ?: DEFAULT_NO_DOUBLE_VALUE

    val maxTemperature: Double = currentWeather.main?.tempMax ?: DEFAULT_NO_DOUBLE_VALUE

    val minTemperature: Double = currentWeather.main?.tempMin ?: DEFAULT_NO_DOUBLE_VALUE

    val temperatureKf: Double = currentWeather.main?.tempKf ?: DEFAULT_NO_DOUBLE_VALUE

    val groundLevel: Double = currentWeather.main?.grndLevel ?: DEFAULT_NO_DOUBLE_VALUE

    val humidity: Double = currentWeather.main?.humidity ?: DEFAULT_NO_DOUBLE_VALUE

    val pressure: Double = currentWeather.main?.pressure ?: DEFAULT_NO_DOUBLE_VALUE

    val seaLevel: Double = currentWeather.main?.seaLevel ?: DEFAULT_NO_DOUBLE_VALUE

    val windSpeed: Double = currentWeather.wind?.speed ?: DEFAULT_NO_DOUBLE_VALUE

    val windAngle: Double = currentWeather.wind?.deg ?: DEFAULT_NO_DOUBLE_VALUE

    val base: String = currentWeather.base.toString()

    val clouds: Double = currentWeather.clouds?.all ?: DEFAULT_NO_DOUBLE_VALUE

    val country: String = currentWeather.sys?.country.toString()

    val message: Double = currentWeather.sys?.message ?: DEFAULT_NO_DOUBLE_VALUE

    val pod: String = currentWeather.sys?.pod.toString()

    val sunrise: Long = currentWeather.sys?.sunrise ?: DEFAULT_NO_LONG_VALUE

    val sunset: Long = currentWeather.sys?.sunset ?: DEFAULT_NO_LONG_VALUE

    val latitude: Double = currentWeather.coord?.lat ?: DEFAULT_NO_DOUBLE_VALUE

    val longitude: Double = currentWeather.coord?.lon ?: DEFAULT_NO_DOUBLE_VALUE

    val dt: Long = currentWeather.dt ?: DEFAULT_NO_LONG_VALUE

    val id: Long = currentWeather.id ?: DEFAULT_NO_LONG_VALUE

    val name: String = currentWeather.name.toString()

    companion object {
        const val DEFAULT_NO_DOUBLE_VALUE: Double = 0.0
        const val DEFAULT_NO_LONG_VALUE: Long = 0L
    }

}