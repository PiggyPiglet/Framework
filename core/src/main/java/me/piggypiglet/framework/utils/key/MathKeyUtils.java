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
