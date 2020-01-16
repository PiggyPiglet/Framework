package me.piggypiglet.framework.mapper.wip;

import me.piggypiglet.framework.mapper.ObjectMapper;

final class EnumObjectMapper implements ObjectMapper<String, Enum<?>> {
    @SuppressWarnings("rawtypes")
    private final Class<? extends Enum> enumClazz;

    EnumObjectMapper(Class<? extends Enum<?>> enumClazz) {
        this.enumClazz = enumClazz;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Enum<?> dataToType(String data) {
        return Enum.valueOf(enumClazz, data.toUpperCase());
    }

    @Override
    public String typeToData(Enum<?> type) {
        return type.toString();
    }
}
