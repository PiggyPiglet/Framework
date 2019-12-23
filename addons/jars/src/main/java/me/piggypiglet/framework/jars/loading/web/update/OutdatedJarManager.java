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

package me.piggypiglet.framework.jars.loading.web.update;

import me.piggypiglet.framework.jars.loading.web.DownloadableJar;
import me.piggypiglet.framework.logging.Logger;
import me.piggypiglet.framework.logging.LoggerFactory;

import java.io.File;

public final class OutdatedJarManager {
    private static final Logger<?> LOGGER = LoggerFactory.getLogger("OutdatedJarManager");

    /**
     * Delete any outdated jars
     *
     * @param dir   Directory to look in
     * @param files Current files
     * @throws Exception IO error when deleting
     */
    public static void deleteOutdated(String dir, DownloadableJar... files) throws Exception {
        OutdatedJarDeleter deleter = new OutdatedJarDeleter(
                new OutdatedJarScanner(new File(dir), files).scan().getNeedsUpdating()
        );

        if (deleter.needsUpdating()) {
            LOGGER.debug("Deleting outdated/irrelevant jars in %s.", dir);
            deleter.deleteAll();
        }
    }
}