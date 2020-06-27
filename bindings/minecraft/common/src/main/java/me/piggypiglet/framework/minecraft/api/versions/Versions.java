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

package me.piggypiglet.framework.minecraft.api.versions;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public enum Versions {
    V1_16(701, 736),
    V1_15(565, 578),
    V1_14(472, 498),
    V1_13(383, 404),
    V1_12(328, 340),
    V1_11(314, 316),
    V1_10(204, 210),
    V1_9(103, 110),
    V1_8(44, 47),
    V1_7(3, 5),
    UNKNOWN(-1, -1);

    private final Set<Integer> ids = new HashSet<>();

    Versions(final int begin, final int end) {
        for (int i = begin; i <= end; ++i) {
            ids.add(i);
        }
    }

    @NotNull
    public Set<Integer> getIds() {
        return ids;
    }

    @NotNull
    public static Versions fromId(final int id) {
        for (Versions ver : values()) {
            if (ver.ids.contains(id)) {
                return ver;
            }
        }

        return UNKNOWN;
    }

    @NotNull
    public static Versions fromName(@NotNull final String version) {
        switch (version.toLowerCase()) {
            case "1_7":
                return V1_7;

            case "1_8":
                return V1_8;

            case "1_9":
                return V1_9;

            case "1_10":
                return V1_10;

            case "1_11":
                return V1_11;

            case "1_12":
                return V1_12;

            case "1_13":
                return V1_13;

            case "1_14":
                return V1_14;

            case "1_15":
                return V1_15;

            default:
                return getLatest();
        }
    }

    @NotNull
    public static Versions getLatest() {
        return V1_16;
    }
}
