package me.piggypiglet.framework.minecraft.versions;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum ProtocolVersions {
    V1_14(472, 498),
    V1_13(383, 404),
    V1_12(328, 340),
    V1_11(314, 316),
    V1_10(204, 210),
    V1_9(103, 110),
    V1_8(44, 47),
    V1_7(3, 5),
    UNKNOWN(-1, -1);

    private final Set<Integer> ids;

    ProtocolVersions(int begin, int end) {
        ids = IntStream.rangeClosed(begin, end-begin).boxed().collect(Collectors.toSet());
    }

    public Set<Integer> getIds() {
        return ids;
    }

    public static ProtocolVersions fromId(int id) {
        for (ProtocolVersions ver : values()) {
            if (ver.ids.contains(id)) {
                return ver;
            }
        }

        return UNKNOWN;
    }
}
