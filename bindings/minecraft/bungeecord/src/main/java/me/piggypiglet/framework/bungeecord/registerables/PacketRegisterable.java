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

package me.piggypiglet.framework.bungeecord.registerables;

import me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.inventory.WindowItems;
import me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.utils.PacketUtils;
import me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.utils.Protocol;
import me.piggypiglet.framework.minecraft.versions.ProtocolVersions;
import me.piggypiglet.framework.registerables.StartupRegisterable;

public final class PacketRegisterable extends StartupRegisterable {
    @Override
    protected void execute() {
        PacketUtils.register(Protocol.TO_CLIENT, WindowItems.class, PacketUtils.maps(14, ProtocolVersions.values()));
    }
}
