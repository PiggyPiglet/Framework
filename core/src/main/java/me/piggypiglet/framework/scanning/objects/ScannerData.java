package me.piggypiglet.framework.scanning.objects;

import org.jetbrains.annotations.NotNull;

/**
 * Internal utility data object for use in scanner initializer logic.
 */
public class ScannerData {
    private final Class<?> main;
    private final String pckg;
    private final String[] exclusions;
    private final boolean parallelFiltering;

    public ScannerData(@NotNull final Class<?> main, @NotNull final String pckg,
                       @NotNull final String[] exclusions, final boolean parallelFiltering) {
        this.main = main;
        this.pckg = pckg;
        this.exclusions = exclusions;
        this.parallelFiltering = parallelFiltering;
    }

    /**
     * Get the application's main class.
     *
     * @return Main class
     */
    public Class<?> getMain() {
        return main;
    }

    /**
     * Get the package to scan.
     *
     * @return Package in string form
     */
    public String getPckg() {
        return pckg;
    }

    /**
     * Get an array of packages that have been requested for exclusion
     * when scanning.
     *
     * @return Array of packages in string form.
     */
    public String[] getExclusions() {
        return exclusions;
    }

    /**
     * Get whether parallel filtering is enabled for the scanner implementation.
     *
     * @return boolean
     */
    public boolean isParallelFiltering() {
        return parallelFiltering;
    }
}
