package me.piggypiglet.framework.scanning;

import me.piggypiglet.framework.scanning.builders.ScannerBuilder;
import me.piggypiglet.framework.scanning.framework.AbstractScanner;
import me.piggypiglet.framework.scanning.framework.Scanner;
import me.piggypiglet.framework.scanning.implementations.ZISScanner;
import me.piggypiglet.framework.scanning.objects.ScannerData;
import me.piggypiglet.framework.utils.builder.BuilderUtils;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Utility class for the initialization of scanner implementations.
 * This enum stores all scanner implementations in RPF and provides
 * easy access to them via a method, and also allows you to provide
 * custom implementations.
 */
public enum Scanners {
    ZIS(ZISScanner::new),
    /**
     * The default scanner which will be used on any members that don't
     * ask for an explicit scanner implementation type, or initializer.
     * Currently the ZIS (ZipInputStream) scanner is used.
     */
    DEFAULT(ZIS.initializer);

    private final Function<ScannerData, AbstractScanner> initializer;

    Scanners(@NotNull final Function<ScannerData, AbstractScanner> initializer) {
        this.initializer = initializer;
    }

    public Scanner create(@NotNull final Class<?> main) {
        return of(initializer, main);
    }

    public ScannerBuilder<Scanner> createBuilder(@NotNull final Class<?> main) {
        return builder(initializer, main);
    }

    public <T> ScannerBuilder<T> createBuilder(@NotNull final Class<?> main, @NotNull final Function<Scanner, T> builder) {
        return builder(initializer, main, builder);
    }

    public static Scanner of(@NotNull final Class<?> main) {
        return builder(main).build();
    }

    public static Scanner of(@NotNull final Function<ScannerData, AbstractScanner> initializer, @NotNull final Class<?> main) {
        return builder(initializer, main).build();
    }

    /**
     * Get a default ScannerBuilder instance, with Scanner as the
     * return type.
     *
     * @param main Main class
     * @return ScannerBuilder returning Scanner
     */
    public static ScannerBuilder<Scanner> builder(@NotNull final Class<?> main) {
        return builder(DEFAULT.initializer, main);
    }

    /**
     * Get a default ScannerBuilder instance, with a custom
     * return type.
     *
     * @param main    Main class
     * @param builder Builder return logic
     * @param <T>     Return type
     * @return ScannerBuilder returning T
     */
    public static <T> ScannerBuilder<T> builder(@NotNull final Class<?> main, @NotNull final Function<Scanner, T> builder) {
        return builder(DEFAULT.initializer, main, builder);
    }

    /**
     * Get a ScannerBuilder instance that builds a custom
     * scanner implementation, that isn't included in RPF.
     * The initializer provides a ScannerData object which
     * stores crucial metadata that has to be passed to the
     * AbstractScanner super constructor.
     *
     * @param initializer Custom scanner initialization logic
     * @param main        Main class
     * @return ScannerBuilder returning Scanner
     */
    public static ScannerBuilder<Scanner> builder(@NotNull final Function<ScannerData, AbstractScanner> initializer, @NotNull final Class<?> main) {
        return new ScannerBuilder<>(initializer, main);
    }

    public static <T> ScannerBuilder<T> builder(@NotNull final Function<ScannerData, AbstractScanner> initializer, @NotNull final Class<?> main,
                                                @NotNull final Function<Scanner, T> builder) {
        return BuilderUtils.customBuilder(new ScannerBuilder<>(initializer, main), builder);
    }
}
