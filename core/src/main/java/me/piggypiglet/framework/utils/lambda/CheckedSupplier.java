package me.piggypiglet.framework.utils.lambda;

@FunctionalInterface
public interface CheckedSupplier<T> {
    T get() throws Exception;
}
