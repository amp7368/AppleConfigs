package voltskiya.apple.configs.plugin.saveable;

import org.bukkit.configuration.InvalidConfigurationException;
import plugin.util.plugin.plugin.util.plugin.PluginManagedModule;
import voltskiya.apple.configs.plugin.manage.functions.ConfigCreateEmpty;
import voltskiya.apple.configs.plugin.manage.functions.ConfigLoadable;
import voltskiya.apple.configs.plugin.manage.functions.ConfigSaveable;
import voltskiya.apple.configs.plugin.manage.functions.ConfigSaver;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class ConfigVoltskiyaSaveableSingleton<Config> extends ConfigVoltskiyaSaveable<Config> {
    private final ConfigLoadable<Config> loading;
    private final ConfigSaver<Config> saving;
    private final String logInvalidConfig;
    private final String logIOExceptionLoading;
    private final String logIOExceptionSaving;
    private final ConfigCreateEmpty<Config> createEmptyInstance;
    private final Class<Config> configClass;
    private final String name;
    private final PluginManagedModule module;
    private final File fileToSave;
    private final boolean shouldBeManaged;

    public ConfigVoltskiyaSaveableSingleton(ConfigSaveableBuilder<Config, ?, ?> builder) {
        this.loading = builder.loading;
        this.saving = builder.saving;
        this.logInvalidConfig = builder.logInvalidConfig;
        this.logIOExceptionLoading = builder.logIOExceptionLoading;
        this.logIOExceptionSaving = builder.logIOExceptionSaving;
        this.createEmptyInstance = builder.createEmptyInstance;
        this.configClass = builder.configClass;
        this.module = builder.module;
        this.name = builder.getName();
        this.fileToSave = builder.getFileToSave();
        shouldBeManaged = builder.shouldBeManaged;
    }

    @Override
    public void saveInstance() throws IOException {
        Config instance = getConfigInstance();
        if (saving == null) {
            if (instance instanceof ConfigSaveable saveable) {
                saveable.saveInstance();
            } else {
                getModule().log(Level.SEVERE, getLogIOExceptionSaving());
            }
        } else {
            saving.saveInstance(instance);
        }
    }

    @Override
    public Config loadInstance() throws IOException, InvalidConfigurationException {
        return loading.loadInstance();
    }

    @Override
    public String getLogInvalidConfig() {
        return logInvalidConfig == null ? super.getLogInvalidConfig() : logInvalidConfig;
    }

    @Override
    public String getLogIOExceptionLoading() {
        return logIOExceptionLoading == null ? super.getLogIOExceptionLoading() : logIOExceptionLoading;
    }

    @Override
    public String getLogIOExceptionSaving() {
        return logIOExceptionSaving == null ? super.getLogIOExceptionSaving() : logIOExceptionSaving;
    }

    @Override
    public Config createEmptyInstance() {
        return createEmptyInstance.createEmpty();
    }

    @Override
    public File getFile() {
        return this.fileToSave;
    }

    @Override
    public Class<Config> getConfigClass() {
        return configClass;
    }

    @Override
    public PluginManagedModule getModule() {
        return module;
    }

    @Override
    public String getName() {
        return name;
    }
}
