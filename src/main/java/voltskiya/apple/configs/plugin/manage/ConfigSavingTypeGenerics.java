package voltskiya.apple.configs.plugin.manage;

import voltskiya.apple.configs.plugin.saveable.ConfigSaveableBuilder;

import java.util.function.Function;

public interface ConfigSavingTypeGenerics<
        Config,
        SpecificBuilder extends ConfigSaveableBuilder<Config, SpecificBuilder, SpecificOutput>,
        SpecificOutput extends ConfigVoltskiya<Config>> {
    SpecificBuilder createBuilder();

    Function<SpecificBuilder, SpecificOutput> getConstructor();
}
