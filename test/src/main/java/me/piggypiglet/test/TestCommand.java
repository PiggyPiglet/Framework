package me.piggypiglet.test;

import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.json.JsonParser;
import me.piggypiglet.framework.user.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class TestCommand extends Command {
    public TestCommand() {
        super("test");
    }

    @Override
    protected boolean execute(User user, String[] args) {
        System.out.println("test");

        JsonParser json;

        try {
            json = new JsonParser(replaceLast(test("https://api.extendedclip.com").replaceFirst("\\{", "{\"data\":["), "\\}", "]}"));
        } catch (Exception e) {
            throw new RuntimeException("Could not load expansion data from the ecloud.");
        }
        System.out.println("test2");

        json.getJsonList("data");
        return true;
    }

    private String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }

    private String test(String url) throws Exception {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openConnection().getInputStream(), StandardCharsets.UTF_8));
        final StringBuilder builder = new StringBuilder();

        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            builder.append(inputLine);
        }

        reader.close();
        return builder.toString();
    }
}
