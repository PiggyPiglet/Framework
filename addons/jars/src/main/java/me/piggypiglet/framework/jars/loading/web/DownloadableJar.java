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

import me.piggypiglet.framework.jars.loading.framework.Jar;

import java.net.URL;

public final class DownloadableJar extends Jar {
    private final String name;
    private final String path;
    private final String version;
    private final String hash;
    private final URL url;

    public DownloadableJar(String name, String path, String version, String hash, URL url) {
        this.name = name;
        this.path = path;
        this.version = version;
        this.hash = hash;
        this.url = url;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getFormattedName() {
        return getName() + "-" + getVersion();
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getVersion() {
        return version;
    }

    public String getHash() {
        return hash;
    }

    public URL getUrl() {
        return url;
    }
}
