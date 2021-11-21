package voltskiya.apple.configs.plugin.manage;

import voltskiya.apple.configs.plugin.saveable.ConfigSaveableBuilder;

import java.util.function.Function;

public final class ConfigSavingTypeSingleton<
        Config,
        SpecificBuilder extends ConfigSaveableBuilder<Config, SpecificBuilder, SpecificOutput>,
        SpecificOutput extends ConfigVoltskiya<Config>>
        implements ConfigSavingTypeGenerics<Config, SpecificBuilder, SpecificOutput> {

    private final Function<ConfigSavingTypeGenerics<Config, SpecificBuilder, SpecificOutput>, SpecificBuilder> builder;
    private final Function<SpecificBuilder, SpecificOutput> constructor;

    public ConfigSavingTypeSingleton(Function<ConfigSavingTypeGenerics<Config, SpecificBuilder, SpecificOutput>, SpecificBuilder> builder,
                                     Function<SpecificBuilder, SpecificOutput> constructor) {
        this.builder = builder;
        this.constructor = constructor;
    }

    @Override
    public Function<SpecificBuilder, SpecificOutput> getConstructor() {
        return constructor;
    }

    public SpecificBuilder createBuilder() {
        return builder.apply(this);
    }

    public ConfigSavingTypeSingleton<Config, SpecificBuilder, SpecificOutput> getSavingType() {
        return this;
    }
}
