package me.piggypiglet.framework.jars.loading;

import com.google.inject.Inject;
import me.piggypiglet.framework.jars.loading.update.OutdatedJarManager;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class Loader<T> {
    @Inject private JarLoader jarLoader;

    private final Class jarType;

    /**
     * Just mirror your generic here, but as a class
     * @param jarType Generic class
     */
    protected Loader(Class jarType) {
        this.jarType = jarType;
    }

    /**
     * Test if the dir and data provided is for this loader
     * @param <T_> Data type array
     * @return Predicate
     */
    public <T_> BiPredicate<String, T_[]> isCorrectLoader() {
        return (dir, datas) -> datas.length > 0 && datas[0].getClass().equals(jarType);
    }

    /**
     * Loads all the jar files
     * @param dir directory of the jars
     * @param datas the jars
     * @return Array of jars to be loaded
     */
    protected abstract Jar[] loadAll(String dir, T[] datas);

    /**
     * Executes the method to load all the jars
     * @param dir directory of the jars
     * @param datas the jas
     */
    void run(String dir, T[] datas) {
        loadAll(dir, loadAll(dir, datas));
    }

    /**
     * Executes the method to load all the jars
     * @param dir directory of the jars
     * @param jars the jas
     */
    protected final void loadAll(String dir, Jar... jars) {
        List<Jar> jarsList = Arrays.asList(jars);

        jarsList.forEach(j -> jarLoader.load(j, dir));
        OutdatedJarManager.deleteOutdated(dir, jarsList.stream().map(Jar::getFormattedName).toArray(String[]::new));
    }

    public Class getJarType() {
        return jarType;
    }
}