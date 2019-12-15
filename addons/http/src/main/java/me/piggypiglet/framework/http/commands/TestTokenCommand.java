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

package me.piggypiglet.framework.http.commands;

import com.google.inject.Inject;
import me.piggypiglet.framework.addon.ConfigManager;
import me.piggypiglet.framework.commands.framework.BaseCommand;
import me.piggypiglet.framework.http.HTTPAddon;
import me.piggypiglet.framework.user.User;
import me.piggypiglet.framework.utils.key.KeyUtils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class TestTokenCommand extends BaseCommand {
    private static final Base64.Decoder DECODER = Base64.getDecoder();
    private static final Base64.Encoder ENCODER = Base64.getEncoder();

    @Inject private ConfigManager configManager;

    public TestTokenCommand() {
        super("token test");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected boolean execute(User user, String[] args) {
        if (args.length > 0) {
            Optional<Map<String, Object>> optional = ((List<Map<String, Object>>) configManager.getConfigs().get(HTTPAddon.class).getItems().get("standard-authentication.tokens")).stream()
                    .filter(m -> {
                        try {
                            PrivateKey priv = KeyFactory.getInstance("EC").generatePrivate(new PKCS8EncodedKeySpec(DECODER.decode(args[0])));
                            PublicKey pub = KeyFactory.getInstance("EC").generatePublic(new X509EncodedKeySpec(DECODER.decode((String) m.get("pub"))));

                            return KeyUtils.match((ECPublicKey) pub, (ECPrivateKey) priv);
                        } catch (Exception e) {
                            return false;
                        }
                    }).findAny();

            if (optional.isPresent()) {
                user.sendMessage("Found a matching key.");
            } else {
                user.sendMessage("No keys match this password.");
            }

            return true;
        }

        return false;
    }
}
