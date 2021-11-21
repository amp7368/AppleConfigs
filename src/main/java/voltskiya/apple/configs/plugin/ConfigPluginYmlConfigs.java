package voltskiya.apple.configs.plugin;

import plugin.util.plugin.plugin.util.plugin.PluginManagedModule;
import voltskiya.apple.configs.plugin.manage.ConfigsVoltskiyaCommand;

public class ConfigPluginYmlConfigs extends PluginManagedModule {
    private static ConfigPluginYmlConfigs instance;

    public ConfigPluginYmlConfigs() {
        instance = this;
    }

    public static ConfigPluginYmlConfigs get() {
        return instance;
    }

    @Override
    public void enable() {
        new ConfigsVoltskiyaCommand();
    }

    @Override
    public String getName() {
        return "YmlConfigs";
    }
}
