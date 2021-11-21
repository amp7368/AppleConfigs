package voltskiya.apple.configs.plugin.yml;

import voltskiya.apple.configs.plugin.manage.ConfigSavingTypeGenerics;
import voltskiya.apple.configs.plugin.manage.ConfigVoltskiya;
import voltskiya.apple.configs.plugin.saveable.ConfigSaveableBuilder;
import ycm.yml.manager.ycm.Ycm;

public class ConfigYmlBuilder<Config,
        SpecificOutput extends ConfigVoltskiya<Config>>
        extends ConfigSaveableBuilder<Config, ConfigYmlBuilder<Config, SpecificOutput>, SpecificOutput> {
    public Ycm ycm = new Ycm();

    public ConfigYmlBuilder(ConfigSavingTypeGenerics<Config, ConfigYmlBuilder<Config, SpecificOutput>, SpecificOutput> savingType) {
        super(savingType);
    }

    @Override
    public ConfigYmlBuilder<Config, SpecificOutput> getBuilder() {
        return this;
    }
}
