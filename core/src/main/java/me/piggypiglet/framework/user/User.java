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

package me.piggypiglet.framework.user;

public abstract class User {
    private final String name;
    private final String id;

    /**
     * Provide basic info to the superclass
     * @param name Name of the user
     * @param id ID of the user
     */
    protected User(String name, String id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Get the user's name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Get the user's unique identifier
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * Does the user have a specific permission
     * @param permission String
     * @return Boolean
     */
    public abstract boolean hasPermission(String permission);

    /**
     * Implementation of sending the user a message
     * @param message Message to send
     */
    protected abstract void sendMessage(String message);

    /**
     * Send the user a message
     * @param message Message to send
     * @param vars Vars to replace in the message (uses String#format)
     */
    public void sendMessage(String message, Object... vars) {
        String clone = message;

        if (vars != null) {
            clone = String.format(message, vars);
        }

        sendMessage(clone);
    }
}
