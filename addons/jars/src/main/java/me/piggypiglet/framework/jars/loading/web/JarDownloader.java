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

package me.piggypiglet.framework.jars.loading.web;

import me.piggypiglet.framework.jars.loading.web.update.OutdatedJarManager;
import me.piggypiglet.framework.logging.Logger;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.utils.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public final class JarDownloader {
    private static final Logger LOGGER = LoggerFactory.getLogger("JarDownloader");

    public static void download(String dir, DownloadableJar... jars) {
        OutdatedJarManager.deleteOutdated(dir, jars);

        for (DownloadableJar jar : jars) {
            dir = dir.replace("/", File.separator);
            final String name = jar.getFormattedName();
            final File file = new File(dir, name + ".jar");
            final URL url = jar.getUrl();
            final String hash = jar.getHash();

            if (!file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.getParentFile().mkdirs();

                try {
                    LOGGER.info("Downloading jar %s from %s with hash %s.", name, url.toString(), hash);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

                    try (InputStream is = connection.getInputStream()) {
                        Files.copy(is, Paths.get(file.toURI()), StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (!file.exists()) {
                LOGGER.error("Unable to download %s", name);
                return;
            }

            if (!FileUtils.md5Checksum(file).equals(hash)) {
                LOGGER.error("Hash doesn't match on %s", name);
                return;
            }

            LOGGER.info("Successfully downloaded %s", name);
        }
    }
}
