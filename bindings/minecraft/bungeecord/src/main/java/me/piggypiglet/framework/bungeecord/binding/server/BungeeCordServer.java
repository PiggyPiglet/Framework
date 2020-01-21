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

package me.piggypiglet.framework.bungeecord.binding.server;

import com.google.inject.Inject;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.minecraft.server.Server;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.net.InetSocketAddress;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public final class BungeeCordServer implements Server<ProxyServer> {
    private final ProxyServer handle;
    private final InetSocketAddress address;

    @Inject
    public BungeeCordServer(MainBinding main) {
        handle = ((Plugin) main.getInstance()).getProxy();
        address = handle.getConfig().getListeners().iterator().next().getHost();
    }

    @Override
    public String getIP() {
        return address.getHostName();
    }

    @Override
    public int getPort() {
        return address.getPort();
    }

    @Override
    public Set<String> getIPBans() {
        throw new UnsupportedOperationException("BungeeCord does not have a ban system.");
    }

    @Override
    public Set<UUID> getBannedPlayers() {
        throw new UnsupportedOperationException("BungeeCord does not have a ban system.");
    }

    @Override
    public String getVersion() {
        return handle.getVersion();
    }

    @Override
    public String getImplementationVersion() {
        return handle.getGameVersion();
    }

    @Override
    public Set<UUID> getOperators() {
        throw new UnsupportedOperationException("BungeeCord does not have operators.");
    }

    @Override
    public int getMaxPlayers() {
        return handle.getConfig().getPlayerLimit();
    }

    @Override
    public boolean isOnlineMode() {
        return handle.getConfig().isOnlineMode();
    }

    @Override
    public Set<UUID> getPlayers() {
        return handle.getPlayers().stream().map(ProxiedPlayer::getUniqueId).collect(Collectors.toSet());
    }

    @Override
    public Set<UUID> getWorlds() {
        throw new UnsupportedOperationException("BungeeCord does not have worlds.");
    }

    @Override
    public ProxyServer getHandle() {
        return handle;
    }
}
