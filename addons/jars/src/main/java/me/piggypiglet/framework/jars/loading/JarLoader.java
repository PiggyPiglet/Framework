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

package me.piggypiglet.framework.jars.loading;

import com.google.inject.Inject;
import me.piggypiglet.framework.jars.loading.framework.Jar;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.logging.framework.Logger;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public final class JarLoader {
    private final URLClassLoader classLoader;

    @Inject
    public JarLoader(URLClassLoader loader) {
        classLoader = loader;
    }

    private static final Logger<?> LOGGER = LoggerFactory.getLogger("JarLoader");

    public URLClassLoader load(Jar jar) throws Exception {
        final String name = jar.getName();
        final File file = new File(jar.getPath());

        LOGGER.debug("Loading jar %s.", name);

        if (!file.exists()) {
            LOGGER.debug("Jar doesn't exist for %s.", name);
            return null;
        }

        final URLClassLoader loader = new URLClassLoader(new URL[]{file.toURI().toURL()}, classLoader);
        LOGGER.debug("Successfully loaded jar %s", name);
        return loader;
    }

    public void unload(Jar jar, URLClassLoader loader) throws Exception {
        loader.close();
        LOGGER.debug("Unloaded %s from the classpath.", jar.getName());
    }
}
