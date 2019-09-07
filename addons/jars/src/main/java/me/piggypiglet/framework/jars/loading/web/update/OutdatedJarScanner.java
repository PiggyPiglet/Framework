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

import com.google.common.collect.ImmutableList;
import me.piggypiglet.framework.jars.loading.web.DownloadableJar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class OutdatedJarScanner {
    private final File directory;
    private final List<String> files;
    private ImmutableList<File> needsUpdating = null;

    public OutdatedJarScanner(File directory, DownloadableJar... files) throws FileNotFoundException {
        this.directory = directory;
        this.files = Arrays.stream(files).map(DownloadableJar::getFormattedName).collect(Collectors.toList());

        runChecks();
    }

    private void runChecks() throws FileNotFoundException {
        if (directory == null || !directory.exists()) throw new FileNotFoundException("Jar directory does not exist!");
    }

    public OutdatedJarScanner scan() {
        files.replaceAll(this::cutJarAtVersion);
        File[] files = directory.listFiles(f -> !this.files.contains(cutJarAtVersion(f.getName().replace(".jar", ""))));

        if (files != null) {
            needsUpdating = ImmutableList.copyOf(files);
        }

        return this;
    }

    public ImmutableList<File> getNeedsUpdating() {
        return needsUpdating;
    }

    private String cutJarAtVersion(String fileName) {
        String[] split = fileName.split("-");
        return split[split.length - 1];
    }
}
