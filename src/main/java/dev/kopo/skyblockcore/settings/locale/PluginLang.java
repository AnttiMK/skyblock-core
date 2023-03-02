package dev.kopo.skyblockcore.settings.locale;

public enum PluginLang implements Lang {

    // General
    CHAT_PREFIX("general.prefix", "&f&lMoti&a&lSkyblock");

    private final String key;
    private final String defaultValue;

    PluginLang(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDefault() {
        return defaultValue;
    }
}
