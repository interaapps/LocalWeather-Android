package de.interaapps.localweather;

import com.kwabenaberko.openweathermaplib.models.threehourforecast.ThreeHourForecast;

public class ForecastWeather {

    private ThreeHourForecast threeHourForecast;

    public ForecastWeather(ThreeHourForecast threeHourForecast) {
        this.threeHourForecast = threeHourForecast;
    }

    public String[] getDescriptions() {
        String[] descriptions = new String[]{""};
        for(int i = 0; i < threeHourForecast.getList().size(); i++) {
            for(int j = 0; j < threeHourForecast.getList().get(i).getWeatherArray().size(); j++) {
                descriptions[i] = threeHourForecast.getList().get(i).getWeatherArray().get(j).getDescription();
            }
        }
        return descriptions;
    }

    public String[] getIcons() {
        String[] icons = new String[]{""};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            for(int j = 0; j < threeHourForecast.getList().get(i).getWeatherArray().size(); j++) {
                icons[i] = threeHourForecast.getList().get(i).getWeatherArray().get(j).getIcon();
            }
        }
        return icons;
    }

    public String[] getMains() {
        String[] mains = new String[]{""};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            for(int j = 0; j < threeHourForecast.getList().get(i).getWeatherArray().size(); j++) {
                mains[i] = threeHourForecast.getList().get(i).getWeatherArray().get(j).getMain();
            }
        }
        return mains;
    }

    public long[] getIds() {
        long[] ids = new long[]{0};
        for(int i = 0; i < threeHourForecast.getList().size(); i++) {
            for(int j = 0; j < threeHourForecast.getList().get(i).getWeatherArray().size(); j++) {
                ids[i] = threeHourForecast.getList().get(i).getWeatherArray().get(j).getId();
            }
        }
        return ids;
    }

    public double[] getTemps() {
        double[] temps = new double[]{0};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            temps[i] = threeHourForecast.getList().get(i).getMain().getTemp();
        }
        return temps;
    }

    public double[] getMaxTemps() {
        double[] maxTemps = new double[]{0};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            maxTemps[i] = threeHourForecast.getList().get(i).getMain().getTempMax();
        }
        return maxTemps;
    }

    public double[] getMinTemps() {
        double[] minTemps = new double[]{0};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            minTemps[i] = threeHourForecast.getList().get(i).getMain().getTempMin();
        }
        return minTemps;
    }

    public double[] getTempKfs() {
        double[] tempsKf = new double[]{0};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            tempsKf[i] = threeHourForecast.getList().get(i).getMain().getTempKf();
        }
        return tempsKf;
    }

    public double[] getGrndLevels() {
        double[] grnds = new double[]{0};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            grnds[i] = threeHourForecast.getList().get(i).getMain().getGrndLevel();
        }
        return grnds;
    }

    public double[] getHumidities() {
        double[] humidities = new double[]{0};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            humidities[i] = threeHourForecast.getList().get(i).getMain().getHumidity();
        }
        return humidities;
    }

    public double[] getPressures() {
        double[] pressures = new double[]{0};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            pressures[i] = threeHourForecast.getList().get(i).getMain().getPressure();
        }
        return pressures;
    }

    public double[] getSeaLevels() {
        double[] seas = new double[]{0};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            seas[i] = threeHourForecast.getList().get(i).getMain().getSeaLevel();
        }
        return seas;
    }

    public double[] getWindSpeeds() {
        double[] wind = new double[]{0};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            wind[i] = threeHourForecast.getList().get(i).getWind().getSpeed();
        }
        return wind;
    }

    public double[] getWindDegs() {
        double[] wind = new double[]{0};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            wind[i] = threeHourForecast.getList().get(i).getWind().getDeg();
        }
        return wind;
    }

    public double[] getClouds() {
        double[] clouds = new double[]{0};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            clouds[i] = threeHourForecast.getList().get(i).getClouds().getAll();
        }
        return clouds;
    }

    public String[] getCountries() {
        String[] countries = new String[]{""};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            countries[i] = threeHourForecast.getList().get(i).getSys().getCountry();
        }
        return countries;
    }

    public double[] getMessages() {
        double[] messages = new double[]{0};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            messages[i] = threeHourForecast.getList().get(i).getSys().getMessage();
        }
        return messages;
    }

    public String[] getPods() {
        String[] pods = new String[]{""};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            pods[i] = threeHourForecast.getList().get(i).getSys().getPod();
        }
        return pods;
    }

    public long[] getSunrises() {
        long[] sunrises = new long[]{0};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            sunrises[i] = threeHourForecast.getList().get(i).getSys().getSunrise();
        }
        return sunrises;
    }

    public long[] getSunsets() {
        long[] sunsets = new long[]{0};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            sunsets[i] = threeHourForecast.getList().get(i).getSys().getSunset();
        }
        return sunsets;
    }

    public long[] getDts() {
        long[] dts = new long[]{0};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            dts[i] = threeHourForecast.getList().get(i).getDt();
        }
        return dts;
    }

    public String[] getDtTexts() {
        String[] dtTexts = new String[]{""};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            dtTexts[i] = threeHourForecast.getList().get(i).getDtTxt();
        }
        return dtTexts;
    }

    public String[] getRains() {
        String[] rains = new String[]{""};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            rains[i] = threeHourForecast.getList().get(i).getRain().toString();
        }
        return rains;
    }

    public String[] getSnows() {
        String[] snows = new String[]{""};
        for (int i = 0; i < threeHourForecast.getList().size(); i++) {
            snows[i] = threeHourForecast.getList().get(i).getSnow().toString();
        }
        return snows;
    }

    public int getCnt() {
        return threeHourForecast.getCnt();
    }

    public String getCod() {
        return threeHourForecast.getCod();
    }

    public double getMessage() {
        return threeHourForecast.getMessage();
    }

    public String getName() {
        return threeHourForecast.getCity().getName();
    }

    public double getLat() {
        return threeHourForecast.getCity().getCoord().getLat();
    }

    public double getLon() {
        return threeHourForecast.getCity().getCoord().getLon();
    }

    public String getCountry() {
        return threeHourForecast.getCity().getCountry();
    }

    private long getId() {
        return threeHourForecast.getCity().getId();
    }

    private long getPopulation() {
        return threeHourForecast.getCity().getPopulation();
    }
}
