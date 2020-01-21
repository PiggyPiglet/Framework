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

package me.piggypiglet.framework.http.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import fi.iki.elonen.NanoHTTPD;
import me.piggypiglet.framework.addon.ConfigManager;
import me.piggypiglet.framework.http.HTTPAddon;
import me.piggypiglet.framework.logging.Logger;
import me.piggypiglet.framework.logging.LoggerFactory;

import javax.net.ssl.KeyManagerFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Map;

/**
 * Wrapper class for NanoHTTP HTTP server
 */
@Singleton
public final class HTTPServer {
    @Inject private ConfigManager configManager;
    @Inject private ResponseHandler responseHandler;

    private static final Logger LOGGER = LoggerFactory.getLogger("HTTP");

    private NanoHTTPD nanoHTTPD;

    /**
     * Start the HTTP server
     */
    public void start() {
        final Map<String, Object> items = configManager.getConfigs().get(HTTPAddon.class).getItems();
        String ip = (String) items.get("host");
        int port = (int) (double) items.get("port");

        try {
            nanoHTTPD = new NestedServer(ip, port);

            if ((boolean) items.get("ssl.enabled")) {
                char[] password = ((String) items.get("ssl.password")).toCharArray();
                KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
                InputStream in = new FileInputStream((String) items.get("ssl.path"));
                ks.load(in, password);
                in.close();
                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                keyManagerFactory.init(ks, password);
                password = null; // not redundant, "clears" the password from memory

                nanoHTTPD.makeSecure(NanoHTTPD.makeSSLSocketFactory(ks, keyManagerFactory), null);
            }

            nanoHTTPD.start();
            LOGGER.debug("HTTP Server started at %s:%s", ip, port);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    /**
     * Stop the HTTP Server
     */
    public void stop() {
        LOGGER.debug("Shutting down HTTP server.");
        nanoHTTPD.stop();
    }

    private final class NestedServer extends NanoHTTPD {
        NestedServer(String ip, int port) {
            super(ip, port);
        }

        @Override
        public Response serve(IHTTPSession session) {
            return responseHandler.serve(session);
        }
    }
}