package me.piggypiglet.framework.utils.annotations.id;

public final class IDInfo {
    private final Class<?> type;
    private final ID id;

    public IDInfo(Class<?> type, ID id) {
        this.type = type;
        this.id = id;
    }

    public Class<?> getType() {
        return type;
    }

    public ID getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("IDInfo(type=%s,id=%s)", type.getSimpleName(), id.value());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IDInfo) {
            IDInfo id = (IDInfo) obj;

            return id.type.equals(type) && id.id.value().equals(this.id.value());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
