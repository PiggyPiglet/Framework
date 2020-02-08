package me.piggypiglet.framework.scanning;

import me.piggypiglet.framework.scanning.builders.ScannerBuilder;
import me.piggypiglet.framework.scanning.framework.AbstractScanner;
import me.piggypiglet.framework.scanning.framework.Scanner;
import me.piggypiglet.framework.scanning.implementations.ZISScanner;
import me.piggypiglet.framework.utils.builder.BuilderUtils;

import javax.annotation.Nonnull;
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

    private final Function<ScannerBuilder.ScannerData, AbstractScanner> initializer;

    Scanners(@Nonnull final Function<ScannerBuilder.ScannerData, AbstractScanner> initializer) {
        this.initializer = initializer;
    }

    public static ScannerBuilder<Scanner> of(@Nonnull final Class<?> main) {
        return new ScannerBuilder<>(DEFAULT.initializer, main);
    }

    public static <T> ScannerBuilder<T> of(@Nonnull final Class<?> main, @Nonnull final Function<Scanner, T> builder) {
        return BuilderUtils.customBuilder(new ScannerBuilder<>(DEFAULT.initializer, main), builder);
    }

    public static ScannerBuilder<Scanner> of(@Nonnull final Scanners type, @Nonnull final Class<?> main) {
        return new ScannerBuilder<>(type.initializer, main);
    }

    public static ScannerBuilder<Scanner> of(@Nonnull final Function<ScannerBuilder.ScannerData, AbstractScanner> initializer, @Nonnull final Class<?> main) {
        return new ScannerBuilder<>(initializer, main);
    }
}
