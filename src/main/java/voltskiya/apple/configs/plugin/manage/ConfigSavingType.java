package voltskiya.apple.configs.plugin.manage;

import plugin.util.plugin.plugin.util.plugin.PluginManagedModule;
import voltskiya.apple.configs.plugin.gson.ConfigGsonBuilder;
import voltskiya.apple.configs.plugin.gson.ConfigVoltskiyaGson;
import voltskiya.apple.configs.plugin.saveable.ConfigBasicBuilder;
import voltskiya.apple.configs.plugin.saveable.ConfigVoltskiyaSaveableSingleton;
import voltskiya.apple.configs.plugin.yml.ConfigVoltskiyaYml;
import voltskiya.apple.configs.plugin.yml.ConfigYmlBuilder;

public class ConfigSavingType {
    public static <Config> ConfigGsonBuilder<Config, ConfigVoltskiyaGson<Config>> gson(PluginManagedModule module, Class<Config> clazz) {
        return new ConfigSavingTypeSingleton<Config, ConfigGsonBuilder<Config, ConfigVoltskiyaGson<Config>>, ConfigVoltskiyaGson<Config>>
                (ConfigGsonBuilder::new, ConfigVoltskiyaGson::new).createBuilder().setModule(module).setConfigClass(clazz);
    }

    public static <Config> ConfigYmlBuilder<Config, ConfigVoltskiyaYml<Config>> yml(PluginManagedModule module, Class<Config> clazz) {
        return new ConfigSavingTypeSingleton<Config, ConfigYmlBuilder<Config, ConfigVoltskiyaYml<Config>>, ConfigVoltskiyaYml<Config>>
                (ConfigYmlBuilder::new, ConfigVoltskiyaYml::new).createBuilder().setModule(module).setConfigClass(clazz);
    }

    public static <Config> ConfigBasicBuilder<Config, ConfigVoltskiyaSaveableSingleton<Config>> basic(PluginManagedModule module, Class<Config> clazz) {
        return new ConfigSavingTypeSingleton<Config, ConfigBasicBuilder<Config, ConfigVoltskiyaSaveableSingleton<Config>>, ConfigVoltskiyaSaveableSingleton<Config>>
                (ConfigBasicBuilder::new, ConfigVoltskiyaSaveableSingleton::new).createBuilder().setModule(module).setConfigClass(clazz);
    }
}
