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

package me.piggypiglet.test;

import me.piggypiglet.framework.jars.loading.framework.Jar;
import me.piggypiglet.framework.jars.loading.framework.ScannableLoader;
import me.piggypiglet.test.framework.jars.framework.Module;

import java.io.File;
import java.util.Arrays;

public final class TestLoader extends ScannableLoader<Module> {
    public TestLoader() {
        super("modules/", c -> Arrays.asList(c.getInterfaces()).contains(Module.class));
    }

    @Override
    public Jar[] process(File[] files) {
        return new Jar[] {
                new Jar() {
                    @Override
                    public String getName() {
                        return "test";
                    }

                    @Override
                    public String getPath() {
                        return "modules/testr.jar";
                    }

                    @Override
                    public String getVersion() {
                        return "1.0.0";
                    }
                }
        };
    }

    @Override
    protected void init(Module instance) {
        instance.start();
    }
}
