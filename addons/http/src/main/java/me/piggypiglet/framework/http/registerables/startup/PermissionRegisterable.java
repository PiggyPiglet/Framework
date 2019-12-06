package me.piggypiglet.framework.http.registerables.startup;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import me.piggypiglet.framework.http.routes.auth.permissions.DefaultPermissionStore;
import me.piggypiglet.framework.http.routes.auth.permissions.Permission;
import me.piggypiglet.framework.managers.Manager;
import me.piggypiglet.framework.managers.ManagersManager;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.clazz.ClassUtils;

import java.util.Optional;

public final class PermissionRegisterable extends StartupRegisterable {
    @Inject private ManagersManager managersManager;
    @Inject private DefaultPermissionStore defaultPermissionStore;

    @Override
    protected void execute() {
        final Optional<Manager> manager = managersManager.getManagers().stream().filter(m -> {
            try {
                return Permission.class.isAssignableFrom(ClassUtils.getImplementedGeneric(m.getClass()));
            } catch (Exception e) {
                return false;
            }
        }).findAny();

        final TypeLiteral<Manager<Permission>> typeLiteral = new TypeLiteral<Manager<Permission>>(){};

        if (manager.isPresent()) {
            addBinding(typeLiteral, (Manager<Permission>) manager.get());
        } else {
            addBinding(typeLiteral, defaultPermissionStore);
        }
    }
}
