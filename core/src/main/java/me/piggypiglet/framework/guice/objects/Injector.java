package me.piggypiglet.framework.guice.objects;

public final class Injector {
    private final com.google.inject.Injector injector;

    public Injector(com.google.inject.Injector injector) {
        this.injector = injector;
    }

    public <T> T getInstance(Class<T> clazz) {
        try {
            return injector.getInstance(clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public com.google.inject.Injector getReal() {
        return injector;
    }
}
