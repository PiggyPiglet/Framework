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

package me.piggypiglet.framework.minecraft.user;

import com.google.gson.Gson;
import me.piggypiglet.framework.minecraft.player.Player;
import me.piggypiglet.framework.minecraft.text.Text;
import me.piggypiglet.framework.user.User;

public abstract class MinecraftUser extends User {
    private static final Gson GSON = new Gson();

    /**
     * Provide basic info to the superclass
     *
     * @param name Name of the user
     * @param id   ID of the user
     */
    protected MinecraftUser(String name, String id) {
        super(name, id);
    }

    protected abstract void sendJsonMessage(String json);

    public void sendRawMessage(String message) {
        if (isPlayer()) {
            sendJsonMessage(GSON.toJson(Text.of(message)));
        } else {
            sendMessage(message);
        }
    }

    public abstract boolean isPlayer();

    public abstract boolean isConsole();

    public abstract Player getAsPlayer();
}
