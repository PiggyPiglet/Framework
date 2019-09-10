package me.piggypiglet.framework.jda.startup;

import com.google.inject.Inject;
import me.piggypiglet.framework.jda.annotation.ID;
import me.piggypiglet.framework.reflection.Reflections;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import net.dv8tion.jda.api.JDA;

public final class GuildBindingRegisterable extends StartupRegisterable {
    @Inject private Reflections reflections;
    @Inject private JDA jda;

    @Override
    protected void execute() {


//        info.forEach(i -> {
//            switch (i.type.getSimpleName().toLowerCase()) {
//                case "guild":
//                    addAnnotatedBinding(Guild.class, Ids.id(i.id.value()), jda.getGuildById(i.id.value()));
//                    break;
//            }
//        });
    }

    private static class IDInfo {
        private final Class<?> type;
        private final ID id;

        private IDInfo(Class<?> type, ID id) {
            this.type = type;
            this.id = id;
        }

        @Override
        public String toString() {
            System.out.println(type);
            System.out.println(id);
            return String.format("IDInfo(type=%s,id=%s)", type.getSimpleName(), id.value());
        }
    }
}
