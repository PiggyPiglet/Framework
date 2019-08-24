package me.piggypiglet.framework.jars.loading;

import java.net.URL;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public interface Jar {
    /**
     * Get the name of a jar
     * @return name of jar
     */
    String getName();

    /**
     * Get the version of a jar
     * @return version of jar
     */
    String getVersion();

    /**
     * Get the formatted name of a jar
     * @return name + version appended
     */
    default String getFormattedName() {
        return getName() + "-" + getVersion();
    }

    /**
     * Get the hash of a jar
     * @return hash of jar
     */
    String getHash();

    /**
     * Get the url of a jar
     * @return url of jar
     */
    URL getUrl();
}