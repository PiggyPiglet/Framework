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

package me.piggypiglet.framework.utils.annotations.registerable;

import me.piggypiglet.framework.bootstrap.BootPriority;
import me.piggypiglet.framework.registerables.StartupRegisterable;

/**
 * StartupRegisterable wrapper for applications
 */
public final class RegisterableData {
    private final Class<? extends StartupRegisterable> registerable;
    private final BootPriority priority;

    /**
     * Populate the object with your registerable, and BootPriority.MANUAL
     * @param registerable StartupRegisterable
     */
    public RegisterableData(Class<? extends StartupRegisterable> registerable) {
        this(registerable, BootPriority.MANUAL);
    }

    /**
     * Populate the object with your registerable, and a custom boot priority
     * @param registerable StartupRegisterable
     * @param priority BootPriority
     */
    public RegisterableData(Class<? extends StartupRegisterable> registerable, BootPriority priority) {
        this.registerable = registerable;
        this.priority = priority;
    }

    /**
     * Retrieve registerable info from the Startup annotation (addons)
     * @param startup Startup annotation
     */
    public RegisterableData(Startup startup) {
        this.registerable = startup.value();
        this.priority = startup.priority();
    }

    /**
     * @return StartupRegisterable
     */
    public Class<? extends StartupRegisterable> getRegisterable() {
        return registerable;
    }

    /**
     * @return BootPriority
     */
    public BootPriority getPriority() {
        return priority;
    }
}
