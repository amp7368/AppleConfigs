package voltskiya.apple.configs.plugin.manage;

import plugin.util.plugin.plugin.util.plugin.PluginManagedModule;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ConfigFolderBuilder implements ConfigBuilderHolder<ConfigFolderBuilder> {
    private final ConfigBuilderHolder<?>[] configs;

    public ConfigFolderBuilder(ConfigBuilderHolder<?>... configs) {
        this.configs = configs;
    }

    public ConfigFolderBuilder doToEach(Consumer<ConfigBuilderHolder<?>> application) {
        for (ConfigBuilderHolder<?> config : configs) {
            application.accept(config);
        }
        return this;
    }

    @Override
    public ConfigFolderBuilder setLogInvalidConfig(String logInvalidConfig) {
        doToEach((c) -> c.setLogInvalidConfig(logInvalidConfig));
        return this;
    }

    @Override
    public ConfigFolderBuilder setLogIOExceptionLoading(String logIOExceptionLoading) {
        doToEach((c) -> c.setLogIOExceptionLoading(logIOExceptionLoading));
        return this;
    }

    @Override
    public ConfigFolderBuilder setLogIOExceptionSaving(String logIOExceptionSaving) {
        doToEach((c) -> c.setLogIOExceptionSaving(logIOExceptionSaving));
        return this;
    }

    @Override
    public ConfigFolderBuilder setModule(PluginManagedModule module) {
        doToEach((c) -> c.setModule(module));
        return this;
    }

    @Override
    public ConfigFolderBuilder setFileToSave(File fileToSave) {
        doToEach((c) -> c.setFileToSave(fileToSave));
        return this;
    }

    @Override
    public ConfigFolderBuilder setFileToSave(String... fileChildren) {
        doToEach((c) -> c.setFileToSave(fileChildren));
        return this;
    }

    @Override
    public Collection<? extends ConfigVoltskiya<?>> createAll() {
        List<ConfigVoltskiya<?>> created = new ArrayList<>();
        for (ConfigBuilderHolder<?> config : this.configs) {
            created.addAll(config.createAll());
        }
        return created;
    }

    @Override
    public ConfigFolderBuilder setPrependFileToSave(String... prependFolder) {
        return doToEach((c) -> c.setPrependFileToSave(prependFolder));
    }

    @Override
    public ConfigFolderBuilder setPostpendFileToSave(String... postpendFolder) {
        return doToEach((c) -> c.setPostpendFileToSave(postpendFolder));
    }

    @Override
    public ConfigFolderBuilder setShouldBeManaged(boolean shouldBeManaged) {
        return doToEach((c) -> c.setShouldBeManaged(shouldBeManaged));
    }

    @Override
    public ConfigFolderBuilder setExtension(String extension) {
        return doToEach((c) -> c.setExtension(extension));

    }

    @Override
    public ConfigFolderBuilder setExtension(Function<String, String> extension) {
        return doToEach((c) -> c.setExtension(extension));

    }

    @Override
    public ConfigFolderBuilder nameAsExtension() {
        return doToEach(ConfigBuilderHolder::nameAsExtension);
    }
}
