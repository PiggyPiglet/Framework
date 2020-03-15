/*
 * MIT License
 *
 * Copyright (c) 2019-2020 PiggyPiglet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.piggypiglet.framework.file.framework;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("unused")
public interface FileConfiguration {
    /**
     * Get an object at a specific path
     *
     * @param path Path of the object
     * @return Object
     */
    @Nullable
    Object get(@NotNull final String path);

    /**
     * Get an object at a specific path, and if it's null, use a default value
     *
     * @param path Path of the object
     * @param def  Default value
     * @return Object
     */
    @NotNull
    Object get(@NotNull final String path, @NotNull final Object def);

    /**
     * Get a config section as a FileConfiguration at a specific path
     *
     * @param path Path of the section
     * @return FileConfiguration
     */
    @Nullable
    FileConfiguration getConfigSection(@NotNull final String path);

    /**
     * Get a config section as a FileConfiguration at a specific path, and if it's null, use a default value
     *
     * @param path Path of the section
     * @param def  Default value
     * @return FileConfiguration
     */
    @NotNull
    FileConfiguration getConfigSection(@NotNull final String path, @NotNull final FileConfiguration def);

    /**
     * Get an String at a specific path
     *
     * @param path Path of the String
     * @return String
     */
    @Nullable
    String getString(@NotNull final String path);

    /**
     * Get a String at a specific path, and if it's null, use a default value
     *
     * @param path Path of the String
     * @param def  Default value
     * @return String
     */
    @NotNull
    String getString(@NotNull final String path, @NotNull final String def);

    /**
     * Get an Integer at a specific path
     *
     * @param path Path of the Integer
     * @return Integer
     */
    @Nullable
    Integer getInt(@NotNull final String path);

    /**
     * Get an int at a specific path, and if it's null, use a default value
     *
     * @param path Path of the int
     * @param def  Default value
     * @return int
     */
    int getInt(@NotNull final String path, @NotNull final int def);

    /**
     * Get a Long at a specific path
     *
     * @param path Path of the Long
     * @return Long
     */
    @Nullable
    Long getLong(@NotNull final String path);

    /**
     * Get a Long at a specific path, and if it's null, use a default value
     *
     * @param path Path of the Long
     * @param def  Default value
     * @return Long
     */
    long getLong(@NotNull final String path, final long def);

    /**
     * Get a Double at a specific path
     *
     * @param path Path of the Double
     * @return Double
     */
    @Nullable
    Double getDouble(@NotNull final String path);

    /**
     * Get a Double at a specific path, and if it's null, use a default value
     *
     * @param path Path of the Double
     * @param def  Default value
     * @return Double
     */
    double getDouble(@NotNull final String path, final double def);

    /**
     * Get a Boolean at a specific path
     *
     * @param path Path of the Boolean
     * @return Boolean
     */
    @Nullable
    Boolean getBoolean(@NotNull final String path);

    /**
     * Get a boolean at a specific path, and if it's null, use a default value
     *
     * @param path Path of the Boolean
     * @param def  Default value
     * @return Boolean
     */
    @NotNull
    boolean getBoolean(@NotNull final String path, final boolean def);

    /**
     * Get a list of FileConfigurations at a specific path
     *
     * @param path Path of the list
     * @return List of FileConfiguration
     */
    @Nullable
    List<FileConfiguration> getConfigList(@NotNull final String path);

    /**
     * Get a list of FileConfigurations at a specific path, and if it's null, use a default value
     *
     * @param path Path of the list
     * @param def  Default value
     * @return List of FileConfiguration
     */
    @NotNull
    List<FileConfiguration> getConfigList(@NotNull final String path, @NotNull final List<FileConfiguration> def);

    /**
     * Get a List at a specific path
     *
     * @param path Path of the List
     * @param <T>  Object type
     * @return List
     */
    @Nullable
    <T> List<T> getList(@NotNull final String path);

    /**
     * Get a List at a specific path, and if it's null, use a default value
     *
     * @param path Path of the List
     * @param def  Default value
     * @param <T>  Object type
     * @return List
     */
    @NotNull
    <T> List<T> getList(@NotNull final String path, @NotNull final List<T> def);
}