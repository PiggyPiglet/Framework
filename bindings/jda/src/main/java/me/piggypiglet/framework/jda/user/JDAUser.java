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

package me.piggypiglet.framework.jda.user;

import me.piggypiglet.framework.user.User;
import net.dv8tion.jda.api.entities.TextChannel;

public final class JDAUser extends User {
    private final net.dv8tion.jda.api.entities.User user;
    private final TextChannel channel;

    public JDAUser(net.dv8tion.jda.api.entities.User user, TextChannel channel) {
        super(
                user.getName(),
                user.getId()
        );
        this.user = user;
        this.channel = channel;
    }

    @Override
    protected void sendMessage(String message) {
        channel.sendMessage(message).queue();
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    public net.dv8tion.jda.api.entities.User getUser() {
        return user;
    }

    public TextChannel getChannel() {
        return channel;
    }
}
