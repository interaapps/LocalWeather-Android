package de.interaapps.localweather;

import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;

public class Weather {

    private CurrentWeather currentWeather;

    public Weather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public String[] getDescriptions() {
        String[] descriptions = new String[]{""};
        for(int i = 0; i < currentWeather.getWeather().size(); i++) {
            descriptions[i] = currentWeather.getWeather().get(i).getDescription();
        }
        return descriptions;
    }

    public String[] getIcons() {
        String[] icons = new String[]{""};
        for (int i = 0; i < currentWeather.getWeather().size(); i++) {
            icons[i] = currentWeather.getWeather().get(i).getIcon();
        }
        return icons;
    }

    public String[] getMains() {
        String[] mains = new String[]{""};
        for (int i = 0; i < currentWeather.getWeather().size(); i++) {
            mains[i] = currentWeather.getWeather().get(i).getMain();
        }
        return mains;
    }

    public long[] getIds() {
        long[] ids = new long[]{0};
        for(int i = 0; i < currentWeather.getWeather().size(); i++) {
            ids[i] = currentWeather.getWeather().get(i).getId();
        }
        return ids;
    }

    public double getTemp() {
        return currentWeather.getMain().getTemp();
    }

    public double getMaxTemp() {
        return currentWeather.getMain().getTempMax();
    }

    public double getMinTemp() {
        return currentWeather.getMain().getTempMin();
    }

    public double getTempKf() {
        return currentWeather.getMain().getTempKf();
    }

    public double getGrndLevel() {
        return currentWeather.getMain().getGrndLevel();
    }

    public double getHumidity() {
        return currentWeather.getMain().getHumidity();
    }

    public double getPressure() {
        return currentWeather.getMain().getPressure();
    }

    public double getSeaLevel() {
        return  currentWeather.getMain().getSeaLevel();
    }

    public double getWindSpeed() {
        return currentWeather.getWind().getSpeed();
    }

    public double getWindDeg() {
        return currentWeather.getWind().getDeg();
    }

    public String getBase() {
        return currentWeather.getBase();
    }

    public double getClouds() {
        return currentWeather.getClouds().getAll();
    }

    public String getCountry() {
        return currentWeather.getSys().getCountry();
    }

    public double getMessage() {
        return currentWeather.getSys().getMessage();
    }

    public String getPod() {
        return currentWeather.getSys().getPod();
    }

    public long getSunrise() {
        return currentWeather.getSys().getSunrise();
    }

    public long getSunset() {
        return currentWeather.getSys().getSunset();
    }

    public double getLat() {
        return currentWeather.getCoord().getLat();
    }

    public double getLon() {
        return currentWeather.getCoord().getLon();
    }

    public long getDt() {
        return currentWeather.getDt();
    }

    public long getId() {
        return currentWeather.getId();
    }

    public String getName() {
        return currentWeather.getName();
    }
}
