package me.piggypiglet.framework.mysql.utils;

import co.aikar.idb.DB;
import co.aikar.idb.DbRow;
import com.google.common.base.Preconditions;
import me.piggypiglet.framework.task.GRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MySQLUtils {
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(7);
    private static final CompletableFuture<Boolean> READY = new CompletableFuture<>();

    /**
     * See whether MySQL is ready for operation
     * @return CompletableFuture of current mysql status
     */
    public static CompletableFuture<Boolean> isReady() {
        return READY;
    }

    /**
     * Create a row in a table.
     * @param table Table name
     * @param keys Columns to fill
     * @param values Column values
     * @return CompletableFuture of whether the operation was succeeded.
     */
    public static CompletableFuture<Boolean> create(String table, String[] keys, Object... values) {
        Preconditions.checkArgument(keys.length == values.length, "Key length doesn't match value length.");

        CompletableFuture<Boolean> success = new CompletableFuture<>();
        String formattedKeys = keyFormat(keys);
        String formattedValues = valueFormat(values);

        mysql(r -> {
            try {
                DB.executeInsert(format("INSERT INTO " + table + " " + formattedKeys + " VALUES " + formattedValues, values));
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
     * @param table Table name
     * @param location Location of the row
     * @param replace Column names and their values to replace
     * @return boolean of whether the operation was successful
     */
    public static CompletableFuture<Boolean> set(String table, Map.Entry<String[], Object[]> location, Map.Entry<String[], Object[]> replace) {
        String[] replaceKeys = replace.getKey();
        Object[] replaceValues = replace.getValue();
        String[] locKeys = location.getKey();
        Object[] locValues = location.getValue();
        Preconditions.checkArgument(replaceKeys.length == replaceValues.length && locKeys.length == locValues.length, "Replace keys don't match value length, or Loc keys don't match value length");

        CompletableFuture<Boolean> success = new CompletableFuture<>();
        int replaceKeysLength = replaceKeys.length;

        if (replaceKeysLength > 0) {
            StringBuilder replacements = new StringBuilder();

            for (int i = 0; i < replaceKeysLength - 2; ++i) {
                replacements.append(replaceKeys[i]).append("=").append("%s");
            }

            replacements.append(replaceKeys[replaceKeysLength - 1]).append("=").append("%s");

            exists(table, location.getKey(), location.getValue()).whenComplete((r, t) -> {
                if (r) {
                    try {
                        DB.executeUpdateAsync("UPDATE `" + table + "` SET " + format(replacements.toString(), replaceValues) + " WHERE " + whereFormat(locKeys, locValues) + ";");
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
     * @param table Table name
     * @param keys Column names
     * @param values Values to look for in columns
     * @return CompletableFuture of DbRow
     */
    public static CompletableFuture<DbRow> getRow(String table, String[] keys, Object[] values) {
        return DB.getFirstRowAsync("SELECT * FROM `" + table + "` WHERE " + whereFormat(keys, values) + ";");
    }

    /**
     * Get all rows in a table
     * @param table Table name
     * @return CompletableFuture of list of DbRows
     */
    public static CompletableFuture<List<DbRow>> getRows(String table) {
        return DB.getResultsAsync("SELECT * FROM `" + table + "`;");
    }

    /**
     * Remove a specific row at a location
     * @param table Table name
     * @param keys Column names
     * @param values Column values
     * @return CompletableFuture of whether the operation was successful
     */
    public static CompletableFuture<Boolean> remove(String table, String[] keys, Object[] values) {
        Preconditions.checkArgument(keys.length == values.length, "Key length doesn't match values length.");

        final CompletableFuture<Boolean> success = new CompletableFuture<>();

        exists(table, keys, values).whenComplete((r, t) -> {
            if (r) {
                try {
                    DB.executeUpdateAsync("DELETE FROM `" + table + "` WHERE " + whereFormat(keys, values) + ";");
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
     * @param table Table name
     * @param keys Column names
     * @param values Column values
     * @return CompletableFuture of whether the row exists or not
     */
    public static CompletableFuture<Boolean> exists(String table, String[] keys, Object[] values) {
        Preconditions.checkArgument(keys.length == values.length, "Key and value length don't match.");

        return DB.getFirstRowAsync("SELECT * FROM `" + table + "` WHERE " + whereFormat(keys, values) + ";").thenApply(r -> !r.isEmpty());
    }

    private static String keyFormat(String[] keys) {
        StringBuilder keysBuilder = new StringBuilder("(`id`");
        Arrays.stream(keys).forEach(k -> keysBuilder.append(", `").append(k).append("`"));
        keysBuilder.append(")");
        return keysBuilder.toString();
    }

    private static String valueFormat(Object[] values) {
        StringBuilder valuesBuilder = new StringBuilder("('0'");
        for (int i = 0; i < values.length; ++i) valuesBuilder.append(", ").append("%s");
        valuesBuilder.append(");");
        return valuesBuilder.toString();
    }

    private static String whereFormat(String[] keys, Object[] values) {
        return "`" + format(String.join("=%s AND `", keys) + "`=%s", values);
    }

    private static String format(String str, Object... variables) {
        final AtomicReference<String> string = new AtomicReference<>(str);

        if (variables != null) {
            Arrays.stream(variables).forEach(param -> {
                switch (param.getClass().getSimpleName()) {
                    case "String":
                        string.set(string.get().replaceFirst("%s", "'" +
                                ((String) param).replaceAll("[^A-Za-z0-9\\[\\]\\-.()/!_%:,&'<>@\\s ]", "")
                                        .replace("'", "ℶ")
                                + "'").replace("ℶ", "\\'"));
                        break;

                    case "Integer":
                        string.set(string.get().replaceFirst("%s", Integer.toString((Integer) param)));
                        break;

                    case "Long":
                        string.set(string.get().replaceFirst("%s", Long.toString((Long) param)));
                        break;
                }
            });
        }

        return string.get();
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