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

package me.piggypiglet.framework.utils.key;

import java.math.BigInteger;

// https://stackoverflow.com/a/31858690
public final class MathKeyUtils {
    private MathKeyUtils() {
        throw new RuntimeException("This class cannot be instantiated.");
    }

    public static BigInteger f2mSum(BigInteger... values) {
        if (values.length == 0) return BigInteger.ZERO;

        BigInteger result = values[0];

        for (int i = values.length - 1; i > 0; i--) {
            result = result.xor(values[i]);
        }

        return result;
    }

    public static BigInteger f2mMultiply(BigInteger a, BigInteger b) {
        BigInteger result = BigInteger.ZERO;

        for (int i = b.bitLength(); i >= 0; i--) {
            if (b.testBit(i)) result = result.xor(a.shiftLeft(i));
        }

        return result;
    }

    public static BigInteger f2mReduce(BigInteger input, BigInteger reductionPolynom, int bitLength) {
        while (input.bitLength() > bitLength) {
            input = input.xor(reductionPolynom.shiftLeft(input.bitLength() - reductionPolynom.bitLength()));
        }

        return input;
    }
}
