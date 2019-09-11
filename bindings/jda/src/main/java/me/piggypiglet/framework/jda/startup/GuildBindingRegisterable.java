package me.piggypiglet.framework.jda.startup;

import com.google.inject.Inject;
import me.piggypiglet.framework.reflection.Reflections;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.annotations.id.ID;
import me.piggypiglet.framework.utils.annotations.id.IDInfo;
import me.piggypiglet.framework.utils.annotations.id.Ids;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

import java.util.Set;
import java.util.stream.Collectors;

public final class GuildBindingRegisterable extends StartupRegisterable {
    @Inject private Reflections reflections;
    @Inject private JDA jda;

    @Override
    protected void execute() {
        final Set<IDInfo> info = reflections.getParametersInConstructorsAnnotatedWith(ID.class).stream()
                .map(p -> new IDInfo(p.getType(), p.getAnnotation(ID.class)))
                .collect(Collectors.toSet());

        reflections.getFieldsAnnotatedWith(ID.class).forEach(f -> info.add(new IDInfo(f.getType(), f.getAnnotation(ID.class))));

        info.forEach(i -> {
            switch (i.getType().getSimpleName().toLowerCase()) {
                case "guild":
                    addAnnotatedBinding(Guild.class, Ids.id(i.getId().value()), jda.getGuildById(i.getId().value()));
                    break;
            }
        });
    }
}
