package me.piggypiglet.framework.http.routes.auth.permissions;

import java.util.Collection;

public final class Permission {
    private final String token;
    private final Collection<String> permissions;

    public Permission(String token, Collection<String> permissions) {
        this.token = token;
        this.permissions = permissions;
    }

    public String getToken() {
        return token;
    }

    public Collection<String> getPermissions() {
        return permissions;
    }
}
