package voltskiya.apple.configs.plugin.manage;

import apple.utilities.util.FileFormatting;
import plugin.util.plugin.plugin.util.plugin.PluginManagedModule;
import voltskiya.apple.configs.plugin.ConfigVoltskiyaUtils;
import voltskiya.apple.configs.plugin.gson.ConfigGsonBuilder;
import voltskiya.apple.configs.plugin.gson.ConfigVoltskiyaGson;
import voltskiya.apple.configs.plugin.manage.functions.ConfigCreateEmpty;
import voltskiya.apple.configs.plugin.saveable.ConfigBasicBuilder;
import voltskiya.apple.configs.plugin.saveable.ConfigSaveableBuilder;
import voltskiya.apple.configs.plugin.saveable.ConfigVoltskiyaSaveableSingleton;
import voltskiya.apple.configs.plugin.yml.ConfigVoltskiyaYml;
import voltskiya.apple.configs.plugin.yml.ConfigYmlBuilder;

import java.util.Collection;
import java.util.Collections;

public interface PluginManagedModuleConfig extends FileFormatting {
    PluginManagedModule getModule();

    default boolean shouldAutoRegister() {
        return true;
    }

    default <Config> Config registerConfigYml(ConfigCreateEmpty<Config> createEmptyInstance, Class<Config> configClass, String name, String... fileChildren) {
        ConfigVoltskiyaYml<Config> config =
                ConfigSavingType.yml(getModule(), configClass)
                        .setCreateEmptyInstance(createEmptyInstance)
                        .setName(name)
                        .setFileToSave(fileChildren)
                        .create();
        config.manageConfig();
        return config.loadNow();
    }

    default <Config> Config registerConfigYml(Class<Config> configClass, String name, String... fileChildren) {
        ConfigCreateEmpty<Config> constructor = ConfigVoltskiyaUtils.getDefaultConstructor(configClass);
        return registerConfigYml(constructor, configClass, name, fileChildren);
    }

    default <Config> Config registerConfigGson(ConfigCreateEmpty<Config> createEmptyInstance, Class<Config> configClass, String name, String... fileChildren) {
        ConfigVoltskiyaGson<Config> config =
                ConfigSavingType.gson(getModule(), configClass)
                        .setCreateEmptyInstance(createEmptyInstance)
                        .setName(name)
                        .setFileToSave(fileChildren)
                        .create();
        config.manageConfig();
        return config.loadNow();
    }

    default <Config> Config registerConfigGson(Class<Config> configClass, String name, String... fileChildren) {
        ConfigCreateEmpty<Config> constructor = ConfigVoltskiyaUtils.getDefaultConstructor(configClass);
        return registerConfigGson(constructor, configClass, name, fileChildren);
    }

    default <Config> Config registerConfig(ConfigSaveableBuilder<Config, ?, ?> configBuilder) {
        ConfigVoltskiya<Config> config = configBuilder
                .create();
        config.manageConfig();
        return config.loadNow();
    }

    default void registerAllConfigs() {
        for (ConfigVoltskiya<?> config : getExtraConfigsToRegister()) {
            if (config.shouldAutoRegister()) {
                config.manageConfig();
                config.loadNow();
            }
        }
        for (ConfigBuilderHolder<?> configShell : getConfigsToRegister()) {
            Collection<? extends ConfigVoltskiya<?>> configs = configShell.createAll();
            for (ConfigVoltskiya<?> config : configs) {
                if (config.shouldAutoRegister()) {
                    config.manageConfig();
                    config.loadNow();
                }
            }
        }
    }

    default <Config> ConfigGsonBuilder<Config, ConfigVoltskiyaGson<Config>> gson(Class<Config> clazz) {
        return ConfigSavingType.gson(getModule(), clazz);
    }

    default <Config> ConfigYmlBuilder<Config, ConfigVoltskiyaYml<Config>> yml(Class<Config> clazz) {
        return ConfigSavingType.yml(getModule(), clazz);
    }

    default <Config> ConfigBasicBuilder<Config, ConfigVoltskiyaSaveableSingleton<Config>> basic(Class<Config> clazz) {
        return ConfigSavingType.basic(getModule(), clazz);
    }

    default ConfigFolderBuilder configFolder(ConfigBuilderHolder<?>... configs) {
        return new ConfigFolderBuilder(configs);
    }

    Collection<ConfigBuilderHolder<?>> getConfigsToRegister();

    default Collection<ConfigVoltskiya<?>> getExtraConfigsToRegister() {
        return Collections.emptyList();
    }
}
