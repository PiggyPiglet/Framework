
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

package me.piggypiglet.framework.mysql.utils;

import co.aikar.idb.DB;
import co.aikar.idb.DbRow;
import me.piggypiglet.framework.task.GRunnable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public final class MySQLUtils {
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(7);
    private static final CompletableFuture<Boolean> READY = new CompletableFuture<>();
    private static final Pattern STRING_REGEX = Pattern.compile("[^A-Za-z0-9\\[\\]\\-.()/!_%:,&'<>@\\s ]");

    /**
     * See whether MySQL is ready for operation
     *
     * @return CompletableFuture of current mysql status
     */
    public static CompletableFuture<Boolean> isReady() {
        return READY;
    }

    /**
     * Create a row in a table.
     *
     * @param table  Table name
     * @param values Row values
     * @return CompletableFuture of whether the operation was succeeded.
     */
    public static CompletableFuture<Boolean> create(String table, Map<String, Object> values) {
        CompletableFuture<Boolean> success = new CompletableFuture<>();

        mysql(r -> {
            try {
                DB.executeInsert("INSERT INTO " + table + " " + createFormat(values) + ";");
                success.complete(true);
            } catch (Exception e) {
                success.complete(false);
                e.printStackTrace();
            }
        });

        return success;
    }

    /**
     * Edit a pre-existing row in a table with new values
     *
     * @param table    Table name
     * @param location Location of the row
     * @param replace  Column names and their values to replace
     * @return boolean of whether the operation was successful
     */
    public static CompletableFuture<Boolean> set(String table, Map<String, Object> location, Map<String, Object> replace) {
        CompletableFuture<Boolean> success = new CompletableFuture<>();

        if (replace.size() > 0) {
            StringBuilder replacements = new StringBuilder();

            replace.forEach((k, v) -> replacements.append(", ").append("`").append(k).append("`=").append(format(v)));

            exists(table, location).whenComplete((r, t) -> {
                if (r) {
                    try {
                        DB.executeUpdateAsync("UPDATE `" + table + "` SET " + replacements.toString().replaceFirst(", ", "") + " WHERE " + whereFormat(location) + ";");
                        success.complete(true);
                    } catch (Exception e) {
                        success.complete(false);
                        e.printStackTrace();
                    }
                }
            });
        }

        return success;
    }

    /**
     * Get a row from a location
     *
     * @param table    Table name
     * @param location Location of the row
     * @return CompletableFuture of DbRow
     */
    public static CompletableFuture<DbRow> getRow(String table, Map<String, Object> location) {
        return DB.getFirstRowAsync("SELECT * FROM `" + table + "` WHERE " + whereFormat(location) + ";");
    }

    /**
     * Get all rows in a table
     *
     * @param table Table name
     * @return CompletableFuture of list of DbRows
     */
    public static CompletableFuture<List<DbRow>> getRows(String table) {
        return DB.getResultsAsync("SELECT * FROM `" + table + "`;");
    }

    /**
     * Remove a specific row at a location
     *
     * @param table    Table name
     * @param location Location of row to remove
     * @return CompletableFuture of whether the operation was successful
     */
    public static CompletableFuture<Boolean> remove(String table, Map<String, Object> location) {

        final CompletableFuture<Boolean> success = new CompletableFuture<>();

        exists(table, location).whenComplete((r, t) -> {
            if (r) {
                try {
                    DB.executeUpdateAsync("DELETE FROM `" + table + "` WHERE " + whereFormat(location) + ";");
                    success.complete(true);
                } catch (Exception e) {
                    success.complete(false);
                    e.printStackTrace();
                }
            }
        });

        return success;
    }

    /**
     * Check if a row exists at a specific location
     *
     * @param table  Table name
     * @param location Location of the row
     * @return CompletableFuture of whether the row exists or not
     */
    public static CompletableFuture<Boolean> exists(String table, Map<String, Object> location) {
        CompletableFuture<Boolean> exists = new CompletableFuture<>();

        EXECUTOR.submit(() -> {
            try {
                DbRow row = DB.getFirstRow("SELECT * FROM `" + table + "` WHERE " + whereFormat(location) + ";");

                if (row == null) {
                    exists.complete(false);
                } else {
                    exists.complete(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return exists;
    }

    private static String createFormat(Map<String, Object> map) {
        final StringBuilder keys = new StringBuilder("(`id`");
        final StringBuilder values = new StringBuilder("('0'");

        map.forEach((k, v) -> {
            keys.append(", `").append(k).append("`");
            values.append(", ").append(format(v));
        });
        keys.append(")");
        values.append(")");

        return keys.toString() + " VALUES " + values.toString();
    }

    private static String whereFormat(Map<String, Object> location) {
        final StringBuilder builder = new StringBuilder();

        location.forEach((key, value) -> builder.append(", ").append("`").append(key).append("`=").append(format(value)));

        return builder.toString().replaceFirst(", ", "");
    }

    private static String format(Object variable) {
        String result = "";

        switch (variable.getClass().getSimpleName()) {
            case "String":
                result = "'" + STRING_REGEX.matcher(((String) variable)).replaceAll("").replace("'", "\\'") + "'";
                break;

            case "Integer":
                result = Integer.toString((Integer) variable);
                break;

            case "Long":
                result = Long.toString((Long) variable);
                break;
        }

        return result;
    }

    private static void mysql(Consumer<GRunnable> task) {
        EXECUTOR.submit(new GRunnable() {
            @Override
            public void run() {
                task.accept(this);
            }
        });
    }
}