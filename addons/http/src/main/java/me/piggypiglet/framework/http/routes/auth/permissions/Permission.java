package me.piggypiglet.framework.http.routes.auth.permissions;

import java.security.PublicKey;
import java.util.Collection;

public final class Permission {
    private final PublicKey key;
    private final Collection<String> permissions;

    public Permission(PublicKey key, Collection<String> permissions) {
        this.key = key;
        this.permissions = permissions;
    }

    public PublicKey getKey() {
        return key;
    }

    public Collection<String> getPermissions() {
        return permissions;
    }
}
