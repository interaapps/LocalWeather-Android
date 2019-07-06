package de.interaapps.localweather.utils;

import org.jetbrains.annotations.Contract;

public enum Lang {
    ARABIC("ar"),
    BULGARIAN("bg"),
    CATALAN("ca"),
    CZECH("cz"),
    GERMAN("de"),
    GREEK("el"),
    ENGLISH("en"),
    PERSIAN("fa"),
    FINNISH("fi"),
    FRENCH("fr"),
    GALICIAN("gl"),
    CROATIAN("hr"),
    HUNGARIAN("hu"),
    ITALIAN("it"),
    JAPANESE("ja"),
    KOREAN("kr"),
    LATVIAN("la"),
    LITHUANIAN("lt"),
    MACEDONIAN("mk"),
    DUTCH("nl"),
    POLISH("pl"),
    PORTUGUESE("pt"),
    ROMANIAN("ro"),
    RUSSIAN("ru"),
    SWEDISH("se"),
    SLOVAK("sk"),
    SLOVENIAN("sl"),
    SPANISH("es"),
    TURKISH("tr"),
    UKRAINIAN("ua"),
    VIETNAMESE("vi"),
    CHINESE_SIMPLIFIED("zh_cn"),
    CHINESE_TRADITIONAL("zh_tw");


    private String tag;
    Lang(String tag) {
        this.tag = tag;
    }

    @Contract(pure = true)
    public String getTag() {
        return tag;
    }
}
