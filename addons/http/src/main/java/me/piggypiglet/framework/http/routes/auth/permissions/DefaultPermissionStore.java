package me.piggypiglet.framework.http.routes.auth.permissions;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.piggypiglet.framework.addon.ConfigManager;
import me.piggypiglet.framework.http.HTTPAddon;
import me.piggypiglet.framework.managers.Manager;
import me.piggypiglet.framework.managers.objects.KeyTypeInfo;
import me.piggypiglet.framework.utils.annotations.reflection.Disabled;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Disabled
@Singleton
public final class DefaultPermissionStore extends Manager<Permission> {
    @Inject private ConfigManager configManager;

    private final Map<String, Permission> permissions = new HashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    protected void populate() {
        ((List<Map<String, Object>>) configManager.getConfigs().get(HTTPAddon.class).getItems().get("standard-authentication.tokens")).forEach(m ->
                add(new Permission((String) m.get("token"), (List<String>) m.get("permissions")))
        );
    }

    @Override
    protected KeyTypeInfo configure(KeyTypeInfo.Builder builder) {
        return builder
                .key(String.class)
                        .getter(permissions::get)
                        .exists(permissions::containsKey)
                        .bundle()
                .build();
    }

    @Override
    protected void insert(Permission item) {
        permissions.put(item.getToken(), item);
    }

    @Override
    protected void delete(Permission item) {
        permissions.remove(item.getToken());
    }

    @Override
    protected Collection<Permission> retrieveAll() {
        return permissions.values();
    }
}
