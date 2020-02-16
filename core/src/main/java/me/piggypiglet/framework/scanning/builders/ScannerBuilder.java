package me.piggypiglet.framework.scanning.builders;

import me.piggypiglet.framework.scanning.framework.AbstractScanner;
import me.piggypiglet.framework.scanning.framework.Scanner;
import me.piggypiglet.framework.scanning.objects.ScannerData;
import me.piggypiglet.framework.utils.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Util builder class for any type of scanner.
 */
public final class ScannerBuilder<T> extends AbstractBuilder<Scanner, T> {
    /**
     * Scanner implementation initializer with the data provided.
     */
    private final Function<ScannerData, AbstractScanner> initializer;

    private final Class<?> main;
    private String pckg = "";
    private String[] exclusions = new String[]{};
    private boolean parallelFiltering = true;

    /**
     * Initialize an instance of an explicit scanner implementation, by providing
     * essential data through a builder pattern.
     *
     * @param initializer Scanner implementation initialization logic
     * @param main        Main class of the application
     */
    public ScannerBuilder(@NotNull final Function<ScannerData, AbstractScanner> initializer, @NotNull final Class<?> main) {
        this.initializer = initializer;
        this.main = main;
    }

    /**
     * The package to scan. If not provided, the package will then be determined by
     * any logic that may be present in the initializer. If there is none, the package
     * will simply be ""; the classes scanned as a result of that vary greatly on the
     * scanner implementation.
     *
     * @param pckg Package that you wish to scan (recursive).
     * @return ScannerBuilder instance
     */
    public ScannerBuilder<T> pckg(@NotNull final String pckg) {
        this.pckg = pckg;
        return this;
    }

    /**
     * Packages to specifically not scan, in lower levels of your root package (ScannerBuilder#pckg).
     * This is the lowest level of class scanning exclusion in RPF. If you specify a package here, none
     * of it's contained classes will be forcefully loaded into the class loader.
     *
     * @param exclusions Array of packages
     * @return ScannerBuilder instance
     */
    public ScannerBuilder<T> exclusions(@NotNull final String... exclusions) {
        this.exclusions = exclusions;
        return this;
    }

    /**
     * Whether filtering logic should be done on a parallel stream. If a large amount of classes are
     * present in the classpath, there may be benefits by setting this to true (default). Smaller projects
     * should set this to false.
     *
     * @param parallelFiltering Boolean
     * @return ScannerBuilder instance
     */
    public ScannerBuilder<T> parallelFiltering(final boolean parallelFiltering) {
        this.parallelFiltering = parallelFiltering;
        return this;
    }

    /**
     * Compile the inputted data into the specified scanner implementation.
     *
     * @return Scanner instance
     */
    @Override
    protected Scanner provideBuild() {
        return initializer.apply(new ScannerData(main, pckg, exclusions, parallelFiltering));
    }
}
