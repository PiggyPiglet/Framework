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

package me.piggypiglet.framework.file.implementations;

import me.piggypiglet.framework.file.framework.AbstractFileConfiguration;
import me.piggypiglet.framework.file.framework.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public final class BlankFileConfiguration extends AbstractFileConfiguration {
    public BlankFileConfiguration() {
        super(s -> true);
    }

    @Override
    public Object get(String path) {
        return null;
    }

    @Override
    public FileConfiguration getConfigSection(String path) {
        return this;
    }

    @Override
    public String getString(String path) {
        return NULL_STRING;
    }

    @Override
    public Integer getInt(String path) {
        return NULL_NUM;
    }

    @Override
    public Long getLong(String path) {
        return (long) NULL_NUM;
    }

    @Override
    public Double getDouble(String path) {
        return (double) NULL_NUM;
    }

    @Override
    public Boolean getBoolean(String path) {
        return NULL_BOOL;
    }

    @Override
    public List<String> getStringList(String path) {
        return new ArrayList<>();
    }

    @Override
    public List<FileConfiguration> getConfigList(String path) {
        return new ArrayList<>();
    }

    @Override
    public List<?> getList(String path) {
        return new ArrayList<>();
    }
}