package voltskiya.apple.configs.plugin.manage;

import plugin.util.plugin.plugin.util.plugin.PluginManagedModule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface PluginManagedConfigRegister {
    default void registerAllConfigs() {
        for (PluginManagedModule module : getModulesInOrder()) {
            if (module instanceof PluginManagedModuleConfig configManager) {
                if (configManager.shouldAutoRegister()) {
                    configManager.registerAllConfigs();
                }
            }
        }
    }

    default List<PluginManagedModule> getModulesInOrder() {
        return new ArrayList<>(getRegisteredModules());
    }

    Collection<PluginManagedModule> getRegisteredModules();
}
