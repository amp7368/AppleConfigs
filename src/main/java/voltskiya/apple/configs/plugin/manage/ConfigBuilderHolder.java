package voltskiya.apple.configs.plugin.manage;

import plugin.util.plugin.plugin.util.plugin.PluginManagedModule;

import java.io.File;
import java.util.Collection;
import java.util.function.Function;

public interface ConfigBuilderHolder<Me extends ConfigBuilderHolder<Me>> {
    Me setLogInvalidConfig(String logInvalidConfig);

    Me setLogIOExceptionLoading(String logIOExceptionLoading);

    Me setLogIOExceptionSaving(String logIOExceptionSaving);

    Me setModule(PluginManagedModule module);

    Me setFileToSave(File fileToSave);

    Me setFileToSave(String... fileChildren);

    Collection<? extends ConfigVoltskiya<?>> createAll();

    Me setPrependFileToSave(String... prependFolder);

    Me setPostpendFileToSave(String... prependFolder);

    Me setExtension(String extension);

    Me setExtension(Function<String, String> extension);

    Me nameAsExtension();

    Me setShouldBeManaged(boolean shouldBeManaged);
}
