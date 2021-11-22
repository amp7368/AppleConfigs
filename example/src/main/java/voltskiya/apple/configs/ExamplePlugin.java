package voltskiya.apple.configs;

import plugin.util.plugin.plugin.util.plugin.PluginManaged;
import plugin.util.plugin.plugin.util.plugin.PluginManagedModule;
import voltskiya.apple.configs.module.PluginConfigExample;
import voltskiya.apple.configs.plugin.manage.PluginManagedConfigRegister;

import java.util.Collection;
import java.util.List;

public class ExamplePlugin extends PluginManaged implements PluginManagedConfigRegister {
    @Override
    public void initialize() {
        registerAllConfigs();
    }


    @Override
    public Collection<PluginManagedModule> getModules() {
        return List.of(new PluginConfigExample());
    }
}
