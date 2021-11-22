package voltskiya.apple.configs.module;

import plugin.util.plugin.plugin.util.plugin.PluginManagedModule;
import voltskiya.apple.configs.module.configs.ConfigExample1;
import voltskiya.apple.configs.module.configs.ConfigExample2;
import voltskiya.apple.configs.module.configs.ConfigExample4;
import voltskiya.apple.configs.plugin.manage.ConfigBuilderHolder;
import voltskiya.apple.configs.plugin.manage.PluginManagedModuleConfig;

import java.util.Collection;
import java.util.List;

public class PluginConfigExample extends PluginManagedModule implements PluginManagedModuleConfig {
    @Override
    public void enable() {
    }

    @Override
    public String getName() {
        return "ConfigExamples";
    }

    @Override
    public Collection<ConfigBuilderHolder<?>> getConfigsToRegister() {
        return List.of(
                configFolder(
                        yml(ConfigExample1.class).setRequired("configExample1").setExtension("yml"),
                        gson(ConfigExample2.class).setRequired("configExample2").setExtension("json")
                ).setFileToSave("configFolder1").nameAsExtension(),
                configFolder(
                        yml(ConfigExample4.class).setRequired("configExample4").setExtension(this::extensionYmlI) // ConfigExample4 extends ConfigExample3
                ).setFileToSave("configFolder2").nameAsExtension()
        );
    }
}
