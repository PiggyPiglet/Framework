package me.piggypiglet.framework.mapping.gson;

import com.google.gson.Gson;
import me.piggypiglet.framework.mapping.gson.builders.GsonObjectMapperBuilder;
import me.piggypiglet.framework.mapping.gson.framework.GsonObjectMapper;
import me.piggypiglet.framework.mapping.gson.implementations.LevenshteinObjectMapper;
import me.piggypiglet.framework.mapping.gson.objects.GsonObjectMapperData;
import me.piggypiglet.framework.utils.builder.BuilderUtils;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Utility class tending to the facilitating of GsonObjectMappers
 * alike, with predefined enum mappers bundled with RPF, and functions
 * to alternatively provide your own mapper type. This class provides
 * 3 types of functions, that then again serve 3 different kinds
 * of mappers.
 * <p>
 * Quick terminology: minimal parameters refers to the essential values
 * needed in order to construct a functional mapper object. Optional
 * values expected in the builder, will never be wrapped into extra
 * parameters.
 * <p>
 * Functions:
 * <i>The mapper is determined by the mapper type, which is explained
 * underneath this list.</i>
 * <ul>
 *     <li>of :- Creates a simple instance of the mapper with
 *     the provided params. This is the equivalent of initialising
 *     gson with <code>new Gson()</code>, instead of with a GsonBuilder.</li>
 *     <li>builder&lt;T&gt; :- Creates a builder instance of the mapper,
 *     likewise to of, with the essential params.</li>
 *     <li>builder&lt;T, R&gt; :- Creates a custom builder type of the
 *     mapper, adhering to the pattern defined in AbstractBuilder's
 *     documentation.</li>
 * </ul>
 * <p>
 * Mappers:
 * <i>The underlying logic behind the functions defined above. DEFAULT
 * refers to the enum constant in this class, entitled DEFAULT.</i>
 * <ul>
 *     <li>of(minimal parameters) :- Creates a DEFAULT mapper.</li>
 *     <li>of(parameters, initializer) :- Creates a mapper with the
 *     provided mapper implementation.</li>
 *     <li>builder(minimal parameters) :- Creates a mapper builder,
 *     of the DEFAULT mapper implementation.</li>
 *     <li>builder(minimal parameters, initializer) :- Creates a mapper
 *     builder of the provided mapper implementation.</li>
 * </ul>
 * <p>
 * Addendum, GsonObjectMappers as a type also has the above mirrored,
 * but without any providable initializer. e.g.
 * <pre>
 *     LEVENSHTEIN.create(CustomObject.class)
 *     LEVENSHTEIN.createBuilder(CustomObject.class)
 *     LEVENSHTEIN.createBuilder(CustomObject.class, mapper -> {
 *         this.mapper.set(mapper);
 *         return this;
 *     });
 * </pre>
 *
 * <i>of is replaced with create, and other methods prefixed with create.
 * Parameter semantics stay consistent however, in fact these methods
 * actually rely on the static utility functions.</i>
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public enum GsonObjectMappers {
    /**
     * Simple mapper that uses the levenshtein algorithm (weighted ratio),
     * via a port of python's fuzzy wuzzy library, to match the type's
     * field names to the input's keys based on similarity.
     */
    LEVENSHTEIN(LevenshteinObjectMapper::new),
    /**
     * Default gson mapper implementation - corresponds to the Levenshtein
     * mapper.
     */
    DEFAULT(LEVENSHTEIN.initializer);

    private final Function<GsonObjectMapperData, GsonObjectMapper<?>> initializer;

    GsonObjectMappers(@NotNull final Function<GsonObjectMapperData, GsonObjectMapper<?>> initializer) {
        this.initializer = initializer;
    }

    /**
     * Default GSON instance for use within GsonObjectMappers, where
     * a custom one isn't supplied, or isn't needed. This is a plain
     * instance, initialized via <code>new Gson();</code>.
     */
    public static final Gson GSON = new Gson();

    /**
     * Construct an instance of this mapper implementation,
     * with the provided type. Uses #of(initializer, params)
     *
     * @param type Type the mapper will construct and deconstruct.
     * @param <T>  Generic type of type.
     * @return Expected GsonObjectMapper&lt;T&gt; implementation.
     */
    public <T> GsonObjectMapper<T> create(@NotNull final Class<T> type) {
        return of((Function) initializer, type);
    }

    /**
     * Construct a GsonObjectMapperBuilder instance, which builds the
     * provided implementation. Uses #builder(initializer, params)
     *
     * @param type Type the mapper will construct and deconstruct.
     * @param <T>  Generic type of type
     * @return Builder of expected GsonObjectMapper&lt;T&gt; implementation.
     */
    public <T> GsonObjectMapperBuilder<T, GsonObjectMapper<T>> createBuilder(@NotNull final Class<T> type) {
        return builder((Function) initializer, type);
    }

    /**
     * Construct a GsonObjectMapperBuilder instance, with custom build
     * logic and a specifiable return type & instance. The constructed
     * expected GsonObjectMapper instance will be accessible in the provided
     * build function. Uses #builder(initializer, params, builder)
     *
     * @param type    Type the mapper will construct and deconstruct.
     * @param builder Custom build logic, and return instance.
     * @param <T>     Generic type of type
     * @param <R>     Return type
     * @return Custom builder of expected GsonObjectMapper&lt;T&gt; implementation.
     */
    public <T, R> GsonObjectMapperBuilder<T, R> createBuilder(@NotNull final Class<T> type, @NotNull final Function<GsonObjectMapper<T>, R> builder) {
        return builder((Function) initializer, type, builder);
    }

    /**
     * Initialize a barebones instance of the DEFAULT mapper, with the
     * provided type. Uses DEFAULT#create(params)
     *
     * @param type Type the mapper will construct and deconstruct.
     * @param <T>  Generic type of type
     * @return DEFAULT mapper implementation of provided type
     */
    public static <T> GsonObjectMapper<T> of(@NotNull final Class<T> type) {
        return DEFAULT.create(type);
    }

    /**
     * Initialize a barebones instance of the provided mapper implementation,
     * with the provided type. Uses #builder(initializer, params)
     *
     * @param initializer Initialization logic for your custom mapper.
     * @param type        Type the mapper will construct and deconstruct.
     * @param <T>         Generic type of type.
     * @return Expected mapper implementation of provided type
     */
    public static <T> GsonObjectMapper<T> of(@NotNull final Function<GsonObjectMapperData, GsonObjectMapper<T>> initializer,
                                             @NotNull final Class<T> type) {
        return builder(initializer, type).build();
    }

    /**
     * Initialize a mapper builder for the DEFAULT implementation, populated
     * with the provided type. Uses DEFAULT#createBuilder(params)
     *
     * @param type Type the mapper will construct and deconstruct.
     * @param <T>  Generic type of type.
     * @return Builder for the DEFAULT mapper implementation of provided type
     */
    public static <T> GsonObjectMapperBuilder<T, GsonObjectMapper<T>> builder(@NotNull final Class<T> type) {
        return DEFAULT.createBuilder(type);
    }

    /**
     * Initialize a custom-returning mapper builder of the DEFAULT implementation,
     * with the provided type. Uses DEFAULT#createBuilder(params, builder)
     *
     * @param type    Type the mapper will construct and deconstruct
     * @param builder Build logic, and return instance
     * @param <T>     Generic type of type.
     * @param <R>     Return instance type (on #build).
     * @return Custom mapper builder of the DEFAULT implementation, for the provided
     * type
     */
    public static <T, R> GsonObjectMapperBuilder<T, R> builder(@NotNull final Class<T> type, @NotNull final Function<GsonObjectMapper<T>, R> builder) {
        return DEFAULT.createBuilder(type, builder);
    }

    /**
     * Initialize a mapper builder, of the provided implementation initializer,
     * and the type. Interacts with the builder object directly, calling it's
     * constructor, supplying the initializer and params.
     *
     * @param initializer Mapper implementation initializer
     * @param type        Type the mapper will construct and deconstruct
     * @param <T>         Generic type of type.
     * @return GsonObjectMapperBuilder of expected implementation, of the provided
     * type.
     */
    public static <T> GsonObjectMapperBuilder<T, GsonObjectMapper<T>> builder(@NotNull final Function<GsonObjectMapperData, GsonObjectMapper<T>> initializer,
                                                                              @NotNull final Class<T> type) {
        return new GsonObjectMapperBuilder<>(initializer, type);
    }

    /**
     * Create a custom-returning builder, of the provided implementation initializer,
     * and the type. Interacts with the builder object directly, calling it's
     * constructor, supplying the initializer and params, and then wrapping it via
     * BuilderUtils#customBuilder, with the provided builder logic.
     *
     * @param initializer Mapper implementation initializer
     * @param type        Type the mapper will construct and deconstruct
     * @param builder     Build logic, and return instance
     * @param <T>         Generic type of type.
     * @param <R>         Return instance type (on #build)
     * @return Custom mapper builder of the provided implementation, for the provided
     * type.
     */
    public static <T, R> GsonObjectMapperBuilder<T, R> builder(@NotNull final Function<GsonObjectMapperData, GsonObjectMapper<T>> initializer,
                                                               @NotNull final Class<T> type, @NotNull final Function<GsonObjectMapper<T>, R> builder) {
        return BuilderUtils.customBuilder(new GsonObjectMapperBuilder<>(initializer, type), builder);
    }
}
