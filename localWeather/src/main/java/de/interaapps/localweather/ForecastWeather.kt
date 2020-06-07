package de.interaapps.localweather

import com.kwabenaberko.openweathermaplib.models.threehourforecast.ThreeHourForecast

class ForecastWeather(private val threeHourForecast: ThreeHourForecast) {

    val descriptions: Array<Array<String>>
        get() {
            val description: MutableList<Array<String>> = mutableListOf()
            for (i in 0 until threeHourForecast.list.size) {
                val desc = mutableListOf<String>()
                for (forecast in threeHourForecast.list[i].weatherArray) {
                    desc.add(forecast.description)
                }
                description.add(desc.toTypedArray())
            }
            return description.toTypedArray()
        }

    val icons: Array<Array<String>>
        get() {
            val icon: MutableList<Array<String>> = mutableListOf()
            for(i in 0 until threeHourForecast.list.size) {
                val ico = mutableListOf<String>()
                for(forecast in threeHourForecast.list[i].weatherArray) {
                    ico.add(forecast.icon)
                }
                icon.add(ico.toTypedArray())
            }
            return icon.toTypedArray()
        }

    val mains: Array<Array<String>>
        get() {
            val main: MutableList<Array<String>> = mutableListOf()
            for(i in 0 until threeHourForecast.list.size) {
                val mai = mutableListOf<String>()
                for(forecast in threeHourForecast.list[i].weatherArray) {
                    mai.add(forecast.main)
                }
                main.add(mai.toTypedArray())
            }
            return main.toTypedArray()
        }

    val ids: Array<Array<Long>>
        get() {
            val id: MutableList<Array<Long>> = mutableListOf()
            for(j in 0 until threeHourForecast.list.size) {
                val i = mutableListOf<Long>()
                for(forecast in threeHourForecast.list[j].weatherArray) {
                    i.add(forecast.id)
                }
                id.add(i.toTypedArray())
            }
            return id.toTypedArray()
        }

    val temperatures: Array<Double>
        get() {
            val temp = mutableListOf<Double>()
            for(forecast in threeHourForecast.list) {
                temp.add(forecast.main.temp)
            }
            return temp.toTypedArray()
        }

    val maxTemperatures: Array<Double>
        get() {
            val temp = mutableListOf<Double>()
            for(forecast in threeHourForecast.list) {
                temp.add(forecast.main.tempMax)
            }
            return temp.toTypedArray()
        }

    val minTemperatures: Array<Double>
        get() {
            val temp = mutableListOf<Double>()
            for(forecast in threeHourForecast.list) {
                temp.add(forecast.main.tempMin)
            }
            return temp.toTypedArray()
        }

    val kfTemperatures: Array<Double>
        get() {
            val temp = mutableListOf<Double>()
            for(forecast in threeHourForecast.list) {
                temp.add(forecast.main.tempKf)
            }
            return temp.toTypedArray()
        }

    val groundLevels: Array<Double>
        get() {
            val ground = mutableListOf<Double>()
            for(forecast in threeHourForecast.list) {
                ground.add(forecast.main.grndLevel)
            }
            return ground.toTypedArray()
        }

    val humidities: Array<Double>
        get() {
            val humidity = mutableListOf<Double>()
            for(forecast in threeHourForecast.list) {
                humidity.add(forecast.main.humidity)
            }
            return humidity.toTypedArray()
        }

    val pressures: Array<Double>
        get() {
            val pressure = mutableListOf<Double>()
            for(forecast in threeHourForecast.list) {
                pressure.add(forecast.main.pressure)
            }
            return pressure.toTypedArray()
        }

    val seaLevels: Array<Double>
        get() {
            val sea = mutableListOf<Double>()
            for(forecast in threeHourForecast.list) {
                sea.add(forecast.main.seaLevel)
            }
            return sea.toTypedArray()
        }

    val windSpeeds: Array<Double>
        get() {
            val wind = mutableListOf<Double>()
            for(forecast in threeHourForecast.list) {
                wind.add(forecast.wind.speed)
            }
            return wind.toTypedArray()
        }

    val windAngles: Array<Double>
        get() {
            val wind = mutableListOf<Double>()
            for(forecast in threeHourForecast.list) {
                wind.add(forecast.wind.deg)
            }
            return wind.toTypedArray()
        }

    val clouds: Array<Double>
        get() {
            val cloud = mutableListOf<Double>()
            for(forecast in threeHourForecast.list) {
                cloud.add(forecast.clouds.all)
            }
            return cloud.toTypedArray()
        }

    val countries: Array<String>
        get() {
            val country = mutableListOf<String>()
            for(forecast in threeHourForecast.list) {
                country.add(forecast.sys.country)
            }
            return country.toTypedArray()
        }

    val messages: Array<Double>
        get() {
            val message = mutableListOf<Double>()
            for(forecast in threeHourForecast.list) {
                message.add(forecast.sys.message)
            }
            return message.toTypedArray()
        }

    val pods: Array<String>
        get() {
            val pod = mutableListOf<String>()
            for(forecast in threeHourForecast.list) {
                pod.add(forecast.sys.pod)
            }
            return pod.toTypedArray()
        }

    val sunrises: Array<Long>
        get() {
            val sun = mutableListOf<Long>()
            for(forecast in threeHourForecast.list) {
                sun.add(forecast.sys.sunrise)
            }
            return sun.toTypedArray()
        }

    val sunsets: Array<Long>
        get() {
            val sun = mutableListOf<Long>()
            for(forecast in threeHourForecast.list) {
                sun.add(forecast.sys.sunset)
            }
            return sun.toTypedArray()
        }

    val dts: Array<Long>
        get() {
            val dt = mutableListOf<Long>()
            for(forecast in threeHourForecast.list) {
                dt.add(forecast.dt)
            }
            return dt.toTypedArray()
        }

    val dtTexts: Array<String>
        get() {
            val dt = mutableListOf<String>()
            for(forecast in threeHourForecast.list) {
                dt.add(forecast.dtTxt)
            }
            return dt.toTypedArray()
        }

    val rains: Array<String>
        get() {
            val rain = mutableListOf<String>()
            for(forecast in threeHourForecast.list) {
                rain.add(forecast.rain.toString())
            }
            return rain.toTypedArray()
        }

    val snows: Array<String>
        get() {
            val snow = mutableListOf<String>()
            for(forecast in threeHourForecast.list) {
                snow.add(forecast.snow.toString())
            }
            return snow.toTypedArray()
        }

    val cnt: Int = threeHourForecast.cnt

    val cod: String = threeHourForecast.cod

    val message: Double = threeHourForecast.message

    val cityName: String = threeHourForecast.city.name

    val cityLatitude: Double = threeHourForecast.city.coord.lat

    val cityLongitude: Double = threeHourForecast.city.coord.lon

    val country: String = threeHourForecast.city.country

    val cityPopulation: Long = threeHourForecast.city.population
}