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

package me.piggypiglet.framework.utils.key;

import java.math.BigInteger;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.*;

import static me.piggypiglet.framework.utils.key.MathKeyUtils.*;

// https://stackoverflow.com/a/31858690
public final class KeyUtils {
    private KeyUtils() {
        throw new RuntimeException("This class cannot be instantiated.");
    }

    public static boolean match(ECPublicKey pub, ECPrivateKey priv) {
        ECParameterSpec pubSpec = pub.getParams(), privSpec = priv.getParams();
        EllipticCurve pubCurve = pubSpec.getCurve(), privCurve = privSpec.getCurve();
        ECField pubField = pubCurve.getField(), privField = privCurve.getField();
        BigInteger privA = privCurve.getA(), privB = privCurve.getB();

        if (pubSpec != privSpec
                && (pubSpec.getCofactor() != privSpec.getCofactor()
                || !pubSpec.getOrder().equals( privSpec.getOrder())
                || !pubSpec.getGenerator().equals(privSpec.getGenerator())
                || pubCurve != privCurve
                && (!pubCurve.getA().equals(privA)
                || !pubCurve.getB().equals(privB)
                || privField.getFieldSize() != pubField.getFieldSize())))
            return false;

        ECPoint w = pub.getW();
        BigInteger x = w.getAffineX(), y = w.getAffineY();

        if (privField instanceof ECFieldFp) {
            BigInteger privP = ((ECFieldFp) privField).getP();
            return pubField instanceof ECFieldFp && privP.equals(((ECFieldFp) pubField).getP())
                    && y.pow(2).subtract(x.pow(3)).subtract(privA.multiply(x)).subtract(privB).mod(privP).signum() == 0;
        }

        if (privField instanceof ECFieldF2m) {
            int m = ((ECFieldF2m) privField).getM();
            BigInteger rp = ((ECFieldF2m) privField).getReductionPolynomial();
            if (!(pubField instanceof ECFieldF2m) || m != ((ECFieldF2m) privField ).getM() || ! rp.equals(((ECFieldF2m) privField ).getReductionPolynomial()))
                return false;
            BigInteger x2 = f2mReduce(f2mMultiply(x, x), rp, m);
            return f2mReduce(f2mSum(f2mMultiply(y, y), f2mMultiply(x, y), f2mMultiply(x, x2), f2mMultiply(privA, x2), privB), rp, m).signum() == 0;
        }

        return false;
    }
}
