package me.piggypiglet.framework.minecraft.versions;

import java.util.HashSet;
import java.util.Set;

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

    private final Set<Integer> ids = new HashSet<>();

    ProtocolVersions(int begin, int end) {
        for (int i = begin; i <= end; ++i) {
            ids.add(i);
        }
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
