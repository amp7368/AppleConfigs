package voltskiya.apple.configs.plugin.saveable;

import voltskiya.apple.configs.plugin.manage.ConfigSavingTypeGenerics;
import voltskiya.apple.configs.plugin.manage.ConfigVoltskiya;

public class ConfigBasicBuilder<
        Config,
        SpecificOutput extends ConfigVoltskiya<Config>
        > extends ConfigSaveableBuilder<Config, ConfigBasicBuilder<Config, SpecificOutput>, SpecificOutput> {

    public ConfigBasicBuilder(ConfigSavingTypeGenerics<Config, ConfigBasicBuilder<Config, SpecificOutput>, SpecificOutput> savingType) {
        super(savingType);
    }

    @Override
    public ConfigBasicBuilder<Config, SpecificOutput> getBuilder() {
        return this;
    }
}
