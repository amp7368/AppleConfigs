package voltskiya.apple.configs;

import plugin.util.plugin.plugin.util.plugin.PluginManaged;
import plugin.util.plugin.plugin.util.plugin.PluginManagedModule;
import voltskiya.apple.configs.plugin.ConfigPluginYmlConfigs;

import java.util.Collection;
import java.util.List;

public class ConfigVoltskiyaPlugin extends PluginManaged {
    private static ConfigVoltskiyaPlugin instance;

    public ConfigVoltskiyaPlugin() {
        instance = this;
    }

    public static ConfigVoltskiyaPlugin get() {
        return instance;
    }

    @Override
    public Collection<PluginManagedModule> getModules() {
        return List.of(new ConfigPluginYmlConfigs());
    }
}
