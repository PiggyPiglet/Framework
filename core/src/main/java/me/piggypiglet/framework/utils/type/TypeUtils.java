package me.piggypiglet.framework.utils.type;

public final class TypeUtils {
    /**
     * Check if a value is null, if it is, return an optional def, if def isn't provided, return a configurable null value
     * @param value     Value
     * @param nullValue Default null value if def & value is null
     * @param def       Default value if null
     * @param <T>       Type of value
     * @return value, nullValue, or def
     */
    @SafeVarargs
    public static <T> T valueNullDef(T value, T nullValue, T... def) {
        return value == null ? def.length >= 1 ? def[0] : nullValue : value;
    }

    /**
     * Get a classes generic, a bit iffy, might not work in all scenarios
     * @param clazz Class to get the generic from
     * @param <T> Type
     * @return Class of the generic type, with the type as the classes generic
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClassGeneric(Class clazz) throws GenericException {
        try {
            return (Class<T>) Class.forName(clazz.getGenericInterfaces()[0].getTypeName().split("<")[1].replace(">", ""));
        } catch (Exception e) {
            throw new GenericException("Can't find generic on " + clazz.getName());
        }
    }
}