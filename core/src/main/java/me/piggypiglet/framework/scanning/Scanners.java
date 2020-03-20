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

    /**
     * Construct an instance of this scanner implementation, with the
     * provided main class. Uses #of(main)
     *
     * @param main Main class
     * @return Scanner instance
     */
    public Scanner create(@NotNull final Class<?> main) {
        return of(initializer, main);
    }

    /**
     * Construct a ScannerBuilder of this scanner implementation, which
     * builds said implementation, with the provided main class.
     * Uses #builder(initializer, main)
     *
     * @param main Main class
     * @return ScannerBuilder of expected Scanner implementation
     */
    public ScannerBuilder<Scanner> createBuilder(@NotNull final Class<?> main) {
        return builder(initializer, main);
    }

    /**
     * Construct a ScannerBuilder instance of this implementation, with the provided
     * main class, however with custom build logic. I.e. a manually-specified return
     * instance, and processing logic of the compiled Scanner object. Uses
     * #builder(initializer, main, builder)
     *
     * @param main    Main class
     * @param builder Custom build logic with return instance
     * @param <T>     Return type
     * @return Custom ScannerBuilder of this scanner
     */
    public <T> ScannerBuilder<T> createBuilder(@NotNull final Class<?> main, @NotNull final Function<Scanner, T> builder) {
        return builder(initializer, main, builder);
    }

    /**
     * Construct an instance of the DEFAULT scanner, with the provided
     * main class. Uses #builder(main)#build
     *
     * @param main Main class
     * @return DEFAULT Scanner implementation
     */
    public static Scanner of(@NotNull final Class<?> main) {
        return builder(main).build();
    }

    /**
     * Construct an instance of the provided scanner implementation, with the
     * provided main class. Uses #builder(initializer, main)#build
     *
     * @param initializer Custom scanner initializer
     * @param main        Main class
     * @return Provided Scanner implementation
     */
    public static Scanner of(@NotNull final Function<ScannerData, AbstractScanner> initializer, @NotNull final Class<?> main) {
        return builder(initializer, main).build();
    }

    /**
     * Construct a ScannerBuilder of the DEFAULT scanner implementation,
     * which builds said implementation, with the provided main class.
     * Uses #builder(initializer, main)
     *
     * @param main Main class
     * @return ScannerBuilder of DEFAULT Scanner implementation
     */
    public static ScannerBuilder<Scanner> builder(@NotNull final Class<?> main) {
        return builder(DEFAULT.initializer, main);
    }

    /**
     * Construct a Scanner Builder instance of the DEFAULT scanner implementation,
     * with the provided main class and build logic. Custom build logic allows for
     * pre-processing of the scanner instance, and a custom return type; allowing
     * for embeddability, or changing the implementation. Uses
     * #builder(initializer, main, builder)
     *
     * @param main    Main class
     * @param builder Builder logic
     * @param <T>     Return type
     * @return Scanner builder of DEFAULT scanner implementation
     */
    public static <T> ScannerBuilder<T> builder(@NotNull final Class<?> main, @NotNull final Function<Scanner, T> builder) {
        return builder(DEFAULT.initializer, main, builder);
    }

    /**
     * Construct a plain Scanner Builder which builds the provided scanner
     * implementation, with the provided main class.
     *
     * @param initializer Custom implementation initialization logic
     * @param main        Main class
     * @return ScannerBuilder of provided implementation
     */
    public static ScannerBuilder<Scanner> builder(@NotNull final Function<ScannerData, AbstractScanner> initializer, @NotNull final Class<?> main) {
        return new ScannerBuilder<>(initializer, main);
    }

    /**
     * Construct a Scanner Builder with custom build logic, which builds
     * the provided scanner, with the provided main class. Build logic
     * has the opportunity to pre-process the built scanner object,
     * and specify a custom instance which will be returned on
     * #build. Useful for embeddability, or general customization
     * within an API.
     *
     * @param initializer Custom implementation initialization logic
     * @param main        Main class
     * @param builder     Build logic
     * @param <T>         Return type
     * @return ScannerBuilder of provided implementation, with provided build logic
     */
    public static <T> ScannerBuilder<T> builder(@NotNull final Function<ScannerData, AbstractScanner> initializer, @NotNull final Class<?> main,
                                                @NotNull final Function<Scanner, T> builder) {
        return BuilderUtils.customBuilder(new ScannerBuilder<>(initializer, main), builder);
    }
}
