package me.piggypiglet.framework.http.registerables.startup;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import me.piggypiglet.framework.http.routes.auth.permissions.DefaultPermissionStore;
import me.piggypiglet.framework.http.routes.auth.permissions.Permission;
import me.piggypiglet.framework.managers.Manager;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.scanning.Scanner;
import me.piggypiglet.framework.utils.clazz.ClassUtils;

import java.util.Optional;

public final class PermissionRegisterable extends StartupRegisterable {
    @Inject private Scanner scanner;
    @Inject private DefaultPermissionStore defaultPermissionStore;

    @Override
    protected void execute() {
        final Optional<? extends Manager> manager = scanner.getSubTypesOf(Manager.class).stream().filter(m -> {
            try {
                return Permission.class.isAssignableFrom(ClassUtils.getImplementedGeneric(m.getClass()));
            } catch (Exception e) {
                return false;
            }
        }).map(injector::getInstance).findAny();

        final TypeLiteral<Manager<Permission>> typeLiteral = new TypeLiteral<Manager<Permission>>(){};

        if (manager.isPresent()) {
            //noinspection unchecked
            addBinding(typeLiteral, (Manager<Permission>) manager.get());
        } else {
            addBinding(typeLiteral, defaultPermissionStore);
        }
    }
}
