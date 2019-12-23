/*
 * MIT License
 *
 * Copyright (c) 2019 PiggyPiglet
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

package me.piggypiglet.framework.utils;

import com.google.common.io.Resources;
import me.piggypiglet.framework.Framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;

public final class FileUtils {
    private FileUtils() {
        throw new RuntimeException("This class cannot be instantiated.");
    }

    /**
     * Export an inputstream into an external file
     *
     * @param in          InputStream
     * @param destination Destination path
     * @throws Exception IO error
     */
    public static void exportResource(InputStream in, String destination) throws Exception {
        Files.copy(in, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Gets the raw plaintext content of a file
     *
     * @param file File to read
     * @return String
     * @throws Exception when read process errors
     */
    @SuppressWarnings("UnstableApiUsage")
    public static String readFileToString(File file) throws Exception {
        return com.google.common.io.Files.asCharSource(file, StandardCharsets.UTF_8).read();
    }

    /**
     * Gets the raw plaintext content of an embedded file.
     *
     * @param path Path of the file
     * @return String
     * @throws Exception when read process errors
     */
    public static String readEmbedToString(String path) throws Exception {
        return readEmbedToString(path, Framework.class);
    }

    /**
     * Gets the raw plaintext content of an embedded file.
     *
     * @param path  Path of the file
     * @param clazz Class to get the resource from.
     * @return String
     * @throws Exception when read process errors
     */
    @SuppressWarnings("UnstableApiUsage")
    public static String readEmbedToString(String path, Class<?> clazz) throws Exception {
        return Resources.toString(clazz.getResource(path), StandardCharsets.UTF_8);
    }

    //todo: use guava Hashing

    /**
     * Get the md5 hash/checksum of a file
     *
     * @param file File
     * @return checksum
     * @throws Exception IO exception
     */
    public static String md5Checksum(File file) throws Exception {
        try (InputStream fis = new FileInputStream(file)) {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int read;

            do {
                read = fis.read(buffer);

                if (read > 0) {
                    digest.update(buffer, 0, read);
                }
            } while (read != -1);

            byte[] checksumBytes = digest.digest();
            StringBuilder checksum = new StringBuilder();

            for (byte b : checksumBytes) {
                checksum.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }

            return checksum.toString();
        }
    }
}