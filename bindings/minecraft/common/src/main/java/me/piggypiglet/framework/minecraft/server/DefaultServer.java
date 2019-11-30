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

package me.piggypiglet.framework.minecraft.server;

import me.piggypiglet.framework.utils.annotations.reflection.Disabled;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Disabled
public final class DefaultServer implements Server<DefaultServer> {
    @Override
    public String getIP() {
        return "127.0.0.1";
    }

    @Override
    public int getPort() {
        return -1;
    }

    @Override
    public Set<String> getIPBans() {
        return new HashSet<>();
    }

    @Override
    public Set<UUID> getBannedPlayers() {
        return new HashSet<>();
    }

    @Override
    public String getVersion() {
        return "null";
    }

    @Override
    public String getImplementationVersion() {
        return "null";
    }

    @Override
    public Set<UUID> getOperators() {
        return new HashSet<>();
    }

    @Override
    public int getMaxPlayers() {
        return -1;
    }

    @Override
    public boolean isOnlineMode() {
        return false;
    }

    @Override
    public Set<UUID> getPlayers() {
        return new HashSet<>();
    }

    @Override
    public Set<UUID> getWorlds() {
        return new HashSet<>();
    }

    @Override
    public DefaultServer getHandle() {
        return this;
    }
}
