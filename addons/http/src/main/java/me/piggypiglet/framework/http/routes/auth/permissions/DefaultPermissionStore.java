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

package me.piggypiglet.framework.http.routes.auth.permissions;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.piggypiglet.framework.addon.ConfigManager;
import me.piggypiglet.framework.http.HTTPAddon;
import me.piggypiglet.framework.logging.Logger;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.managers.Manager;
import me.piggypiglet.framework.managers.objects.KeyTypeInfo;
import me.piggypiglet.framework.utils.annotations.reflection.Disabled;
import me.piggypiglet.framework.utils.key.KeyUtils;

import java.security.KeyFactory;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

@Disabled
@Singleton
public final class DefaultPermissionStore extends Manager<Permission> {
    private static final Logger<?> LOGGER = LoggerFactory.getLogger("DefaultPermissionStore");
    private static final Base64.Decoder DECODER = Base64.getDecoder();
    private static final KeyFactory KEY_FACTORY;

    static {
        try {
            KEY_FACTORY = KeyFactory.getInstance("EC");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Inject private ConfigManager configManager;

    private final List<Permission> permissions = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Override
    protected void postConfigure() {
        ((List<Map<String, Object>>) configManager.getConfigs().get(HTTPAddon.class).getItems().get("standard-authentication.tokens")).forEach(m -> {
            try {
                final String pub = (String) m.get("pub");

                if (!pub.isEmpty()) {
                    add(new Permission(
                            KEY_FACTORY.generatePublic(new X509EncodedKeySpec(DECODER.decode((String) m.get("pub")))),
                            (List<String>) m.get("permissions")
                    ));
                }
            } catch (Exception e) {
                LOGGER.error(e);
            }
        });
    }

    @Override
    protected KeyTypeInfo configure(KeyTypeInfo.Builder builder) {
        return builder
                .key(String.class)
                        .getter(priv -> permissions.stream().filter(p -> {
                            try {
                                return KeyUtils.match(
                                        (ECPublicKey) p.getKey(),
                                        (ECPrivateKey) KeyFactory.getInstance("EC").generatePrivate(new PKCS8EncodedKeySpec(DECODER.decode(priv)))
                                );
                            } catch (Exception e) {
                                return false;
                            }
                        }).findAny().orElse(null))
                        .exists(priv -> get(priv) != null)
                        .bundle()
                .build();
    }

    @Override
    protected void insert(Permission item) {
        permissions.add(item);
    }

    @Override
    protected void delete(Permission item) {
        permissions.remove(item);
    }

    @Override
    protected Collection<Permission> retrieveAll() {
        return permissions;
    }
}
