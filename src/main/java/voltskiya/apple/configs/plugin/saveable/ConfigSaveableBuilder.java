package voltskiya.apple.configs.plugin.saveable;

import apple.utilities.util.FileFormatting;
import plugin.util.plugin.plugin.util.plugin.PluginManagedModule;
import voltskiya.apple.configs.plugin.ConfigVoltskiyaUtils;
import voltskiya.apple.configs.plugin.manage.ConfigBuilderHolder;
import voltskiya.apple.configs.plugin.manage.ConfigSavingTypeGenerics;
import voltskiya.apple.configs.plugin.manage.ConfigVoltskiya;
import voltskiya.apple.configs.plugin.manage.functions.ConfigCreateEmpty;
import voltskiya.apple.configs.plugin.manage.functions.ConfigLoadable;
import voltskiya.apple.configs.plugin.manage.functions.ConfigSaver;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public abstract class ConfigSaveableBuilder<
        Config,
        SpecificBuilder extends ConfigSaveableBuilder<Config, SpecificBuilder, SpecificOutput>,
        SpecificOutput extends ConfigVoltskiya<Config>> implements ConfigBuilderHolder<SpecificBuilder> {
    private final ConfigSavingTypeGenerics<Config, SpecificBuilder, SpecificOutput> savingType;
    boolean shouldBeManaged;
    ConfigLoadable<Config> loading;
    ConfigSaver<Config> saving;
    String logInvalidConfig;
    String logIOExceptionLoading;
    String logIOExceptionSaving;
    ConfigCreateEmpty<Config> createEmptyInstance;
    Class<Config> configClass;
    String name;
    PluginManagedModule module;
    private File fileToSave;
    private List<String> fileChildren = new ArrayList<>();
    private Function<String, String> extension;
    private boolean nameAsFileName = false;

    public ConfigSaveableBuilder(ConfigSavingTypeGenerics<Config, SpecificBuilder, SpecificOutput> savingType) {
        this.savingType = savingType;
    }

    public abstract SpecificBuilder getBuilder();

    public SpecificOutput create() {
        return savingType.getConstructor().apply(getBuilder());
    }

    @Override
    public Collection<? extends ConfigVoltskiya<?>> createAll() {
        return List.of(create());
    }

    public SpecificBuilder setLoading(ConfigLoadable<Config> loading) {
        this.loading = loading;
        return getBuilder();
    }

    public SpecificBuilder setSaving(ConfigSaver<Config> saving) {
        this.saving = saving;
        return getBuilder();
    }

    public SpecificBuilder setLogInvalidConfig(String logInvalidConfig) {
        this.logInvalidConfig = logInvalidConfig;
        return getBuilder();
    }

    public SpecificBuilder setShouldBeManaged(boolean shouldBeManaged) {
        this.shouldBeManaged = shouldBeManaged;
        return getBuilder();
    }

    public SpecificBuilder setLogIOExceptionLoading(String logIOExceptionLoading) {
        this.logIOExceptionLoading = logIOExceptionLoading;
        return getBuilder();
    }

    public SpecificBuilder setLogIOExceptionSaving(String logIOExceptionSaving) {
        this.logIOExceptionSaving = logIOExceptionSaving;
        return getBuilder();
    }

    public SpecificBuilder setCreateEmptyInstance(ConfigCreateEmpty<Config> createEmptyInstance) {
        this.createEmptyInstance = createEmptyInstance;
        return getBuilder();
    }

    public SpecificBuilder setConfigClass(Class<Config> configClass) {
        this.configClass = configClass;
        if (this.createEmptyInstance == null) {
            this.createEmptyInstance = ConfigVoltskiyaUtils.getDefaultConstructor(configClass);
        }
        return getBuilder();
    }

    public SpecificBuilder setModule(PluginManagedModule module) {
        this.module = module;
        return getBuilder();
    }

    public SpecificBuilder setPrependFileToSave(String... prependFolder) {
        ArrayList<String> file = new ArrayList<>(List.of(prependFolder));
        file.addAll(this.fileChildren);
        this.fileChildren = file;
        return getBuilder();
    }

    public SpecificBuilder setPostpendFileToSave(String... prependFolder) {
        ArrayList<String> file = new ArrayList<>(this.fileChildren);
        file.addAll(List.of(prependFolder));
        this.fileChildren = file;
        return getBuilder();
    }

    public SpecificBuilder setExtension(String extension) {
        this.extension = s -> FileFormatting.extension(s, extension);
        return getBuilder();
    }

    public SpecificBuilder setExtension(Function<String, String> extension) {
        this.extension = extension;
        return getBuilder();
    }

    public SpecificBuilder setRequired(
            String name,
            File fileToSave) {
        this.name = name;
        this.fileToSave = fileToSave;
        return getBuilder();
    }

    public SpecificBuilder setRequired(
            String name,
            String... fileChildren) {
        this.name = name;
        this.fileToSave = module.getFile(fileChildren);
        return getBuilder();
    }

    public SpecificBuilder nameAsExtension() {
        this.nameAsFileName = !nameAsFileName;
        return getBuilder();
    }

    public File getFileToSave() {
        if (fileToSave == null) fileToSave = module.getDataFolder();
        if (nameAsFileName) fileChildren.add(name);
        if (extension != null) {
            int index = fileChildren.size() - 1;
            fileChildren.set(index, extension.apply(fileChildren.get(index)));
        }
        return FileFormatting.fileWithChildren(fileToSave, fileChildren.toArray(String[]::new));
    }

    public SpecificBuilder setFileToSave(File fileToSave) {
        this.fileToSave = fileToSave;
        return getBuilder();
    }

    public SpecificBuilder setFileToSave(String... fileChildren) {
        this.fileChildren = new ArrayList<>(List.of(fileChildren));
        return getBuilder();
    }

    public String getName() {
        if (extension == null) {
            String[] split = name.split("\\.");
            if (split.length != 1) {
                return String.join(".", Arrays.stream(split).toList().subList(0, split.length));
            }
        }
        return name;
    }

    public SpecificBuilder setName(String name) {
        this.name = name;
        return getBuilder();
    }
}