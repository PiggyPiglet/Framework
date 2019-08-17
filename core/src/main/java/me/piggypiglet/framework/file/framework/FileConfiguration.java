package me.piggypiglet.framework.file.framework;

import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@SuppressWarnings("unused")
public interface FileConfiguration {
    /**
     * Get an object at a specific path
     * @param path Path of the object
     * @return Object
     */
    Object get(String path);

    /**
     * Get an object at a specific path, and if it's null, use a default value
     * @param path Path of the object
     * @param def Default value
     * @return Object
     */
    Object get(String path, Object def);

    /**
     * Get a config section as a FileConfiguration at a specific path
     * @param path Path of the section
     * @return FileConfiguration
     */
    FileConfiguration getConfigSection(String path);

    /**
     * Get a config section as a FileConfiguration at a specific path, and if it's null, use a default value
     * @param path Path of the section
     * @param def Default value
     * @return FileConfiguration
     */
    FileConfiguration getConfigSection(String path, FileConfiguration def);

    /**
     * Get an String at a specific path
     * @param path Path of the String
     * @return String
     */
    String getString(String path);

    /**
     * Get a String at a specific path, and if it's null, use a default value
     * @param path Path of the String
     * @param def Default value
     * @return String
     */
    String getString(String path, String def);

    /**
     * Get an Integer at a specific path
     * @param path Path of the Integer
     * @return Integer
     */
    Integer getInt(String path);

    /**
     * Get an int at a specific path, and if it's null, use a default value
     * @param path Path of the int
     * @param def Default value
     * @return int
     */
    int getInt(String path, int def);

    /**
     * Get a Long at a specific path
     * @param path Path of the Long
     * @return Long
     */
    Long getLong(String path);

    /**
     * Get a Long at a specific path, and if it's null, use a default value
     * @param path Path of the Long
     * @param def Default value
     * @return Long
     */
    long getLong(String path, long def);

    /**
     * Get a Double at a specific path
     * @param path Path of the Double
     * @return Double
     */
    Double getDouble(String path);

    /**
     * Get a Double at a specific path, and if it's null, use a default value
     * @param path Path of the Double
     * @param def Default value
     * @return Double
     */
    double getDouble(String path, double def);

    /**
     * Get a Boolean at a specific path
     * @param path Path of the Boolean
     * @return Boolean
     */
    Boolean getBoolean(String path);

    /**
     * Get a boolean at a specific path, and if it's null, use a default value
     * @param path Path of the Boolean
     * @param def Default value
     * @return Boolean
     */
    boolean getBoolean(String path, boolean def);

    /**
     * Get a list of strings at a specific path
     * @param path Path of the list
     * @return List of Strings
     */
    List<String> getStringList(String path);

    /**
     * Get a list of strings at a specific path, and if it's null, use a default value
     * @param path Path of the list
     * @param def Default value
     * @return List of Strings
     */
    List<String> getStringList(String path, List<String> def);

    /**
     * Get a list of FileConfigurations at a specific path
     * @param path Path of the list
     * @return List of FileConfiguration
     */
    List<FileConfiguration> getConfigList(String path);

    /**
     * Get a list of FileConfigurations at a specific path, and if it's null, use a default value
     * @param path Path of the list
     * @param def Default value
     * @return List of FileConfiguration
     */
    List<FileConfiguration> getConfigList(String path, List<FileConfiguration> def);

    /**
     * Get a List at a specific path
     * @param path Path of the List
     * @return List
     */
    List<?> getList(String path);

    /**
     * Get a List at a specific path, and if it's null, use a default value
     * @param path Path of the List
     * @param def Default value
     * @return List
     */
    List<?> getList(String path, List<?> def);
}