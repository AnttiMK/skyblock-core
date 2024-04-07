package dev.kopo.skyblockcore.util;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Namespaced;

public class FontKey {

    private static final Namespaced NAMESPACE = () -> "realmi";
    public static final Key GUI = Key.key(NAMESPACE, "gui");

    private FontKey() {
    }
}
