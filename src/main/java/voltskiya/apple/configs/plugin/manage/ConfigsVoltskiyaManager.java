package voltskiya.apple.configs.plugin.manage;

import java.util.*;

public class ConfigsVoltskiyaManager {
    private static final ConfigsVoltskiyaManager instance = new ConfigsVoltskiyaManager();

    private final Map<String, ConfigVoltskiya<?>> configs = new HashMap<>();
    private final List<String> configNames = new ArrayList<>();

    public static ConfigsVoltskiyaManager get() {
        return instance;
    }

    public synchronized void addConfig(ConfigVoltskiya<?> config) {
        configs.put(config.getName().toLowerCase(Locale.ROOT), config);
        configNames.add(config.getName());
        configNames.sort(String.CASE_INSENSITIVE_ORDER);
    }

    public synchronized List<String> getConfigStrings() {
        return configNames;
    }

    public synchronized ConfigVoltskiya<?> getConfig(String configName) {
        return configs.get(configName.toLowerCase(Locale.ROOT));
    }
}
