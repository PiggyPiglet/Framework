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

package me.piggypiglet.framework.http.commands;

import me.piggypiglet.framework.commands.framework.BaseCommand;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.logging.framework.Logger;
import me.piggypiglet.framework.user.User;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

public final class GenerateTokenCommand extends BaseCommand {
    private static final Logger<?> LOGGER = LoggerFactory.getLogger("GenerateToken");
    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final SecureRandom RANDOM;

    static {
        SecureRandom random;

        try {
            random = SecureRandom.getInstanceStrong();
        } catch (Exception e) {
            random = new SecureRandom();
        }

        RANDOM = random;
    }

    public GenerateTokenCommand() {
        super("token generate");
    }

    @Override
    protected boolean execute(User user, String[] args) {
        try {
            KeyPairGenerator g = KeyPairGenerator.getInstance("EC");
            g.initialize(new ECGenParameterSpec("secp256k1"), RANDOM);
            KeyPair pair = g.generateKeyPair();
            PublicKey pub = pair.getPublic();
            PrivateKey priv = pair.getPrivate();

            user.sendMessage("Your token: %s", ENCODER.encodeToString(priv.getEncoded()));
            user.sendMessage(String.format("Save this to config: pub: %s", ENCODER.encodeToString(pub.getEncoded())));
        } catch (Exception e) {
            LOGGER.error(e);
        }

        return true;
    }
}
