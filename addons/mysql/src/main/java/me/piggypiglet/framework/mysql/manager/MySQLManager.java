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

package me.piggypiglet.framework.mysql.manager;

import me.piggypiglet.framework.managers.implementations.SearchableManager;
import me.piggypiglet.framework.mysql.table.Table;
import me.piggypiglet.framework.mysql.utils.MySQLUtils;
import me.piggypiglet.framework.utils.SearchUtils;

public abstract class MySQLManager<S extends SearchUtils.Searchable> extends SearchableManager<S> {
    private final Table<S> table;

    protected MySQLManager(Table<S> table) {
        this.table = table;
    }

    @Override
    public void setup() {
        MySQLUtils.isReady().whenComplete((b, t) -> {
            if (b) {
                table.getAll().whenComplete((s, th) -> {
                    items.addAll(s);
                    populate(items);
                });
            }
        });
    }

    public Table<S> getTable() {
        return table;
    }
}
