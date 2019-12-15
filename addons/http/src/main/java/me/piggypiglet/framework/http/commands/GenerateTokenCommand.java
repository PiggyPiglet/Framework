package me.piggypiglet.framework.http.commands;

import me.piggypiglet.framework.commands.framework.BaseCommand;
import me.piggypiglet.framework.logging.Logger;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.user.User;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

public final class GenerateTokenCommand extends BaseCommand {
    private static final Logger<?> LOGGER = LoggerFactory.getLogger("GenerateToken");
    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final SecureRandom RANDOM;

    static {
        SecureRandom random;

        try {
            random = SecureRandom.getInstanceStrong();
        } catch (Exception e) {
            random = new SecureRandom();
        }

        RANDOM = random;
    }

    public GenerateTokenCommand() {
        super("token generate");
    }

    @Override
    protected boolean execute(User user, String[] args) {
        try {
            KeyPairGenerator g = KeyPairGenerator.getInstance("EC");
            g.initialize(new ECGenParameterSpec("secp256k1"), RANDOM);
            KeyPair pair = g.generateKeyPair();
            PublicKey pub = pair.getPublic();
            PrivateKey priv = pair.getPrivate();

            user.sendMessage("Your token: %s", ENCODER.encodeToString(priv.getEncoded()));
            user.sendMessage(String.format("Save this to config: pub: %s", ENCODER.encodeToString(pub.getEncoded())));
        } catch (Exception e) {
            LOGGER.error(e);
        }

        return true;
    }
}
