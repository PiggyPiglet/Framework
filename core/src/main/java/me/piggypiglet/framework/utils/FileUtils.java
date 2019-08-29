package me.piggypiglet.framework.utils;

import me.piggypiglet.framework.Framework;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class FileUtils {
    /**
     * Export an inputstream into an external file
     * @param in InputStream
     * @param destination Destination path
     * @return Successful or not
     */
    public static boolean exportResource(InputStream in, String destination) {
        boolean success = true;

        try {
            Files.copy(in, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            success = false;
            e.printStackTrace();
        }

        return success;
    }

    /**
     * Wrapper for commons-io FileUtils#readFileToString, automatically uses UTF-8.
     * Gets the raw plaintext content of a file
     * @param file File to read
     * @return String
     */
    public static String readFileToString(File file) {
        try {
            return org.apache.commons.io.FileUtils.readFileToString(file, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Wrapper for commons-io IOUtils#toString, automatically uses UTF-8.
     * Gets the raw plaintext content of an embedded file.
     * @param path Path of the file
     * @return String
     */
    public static String readEmbedToString(String path) {
        try {
            return IOUtils.toString(Framework.class.getResourceAsStream(path), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get the md5 hash/checksum of a file
     * @param file File
     * @return checksum
     */
    public static String md5Checksum(File file) {
        try {
            InputStream fis = new FileInputStream(file);
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int read;

            do {
                read = fis.read(buffer);

                if (read > 0 ) {
                    digest.update(buffer, 0, read);
                }
            } while (read != -1);

            fis.close();

            byte[] checksumBytes = digest.digest();
            StringBuilder checksum = new StringBuilder();

            for (byte b : checksumBytes) {
                checksum.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }

            return checksum.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}