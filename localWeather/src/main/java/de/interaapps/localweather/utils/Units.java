package de.interaapps.localweather.utils;

import org.jetbrains.annotations.Contract;

public enum Units {
    METRIC("metric"),
    IMPERIAL("imperial");

    private String title;
    Units(String title) {
        this.title = title;
    }

    @Contract(pure = true)
    public String getTitle() {
        return title;
    }
}