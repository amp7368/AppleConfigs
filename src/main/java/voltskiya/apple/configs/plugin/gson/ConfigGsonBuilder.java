package voltskiya.apple.configs.plugin.gson;

import com.google.gson.Gson;
import voltskiya.apple.configs.plugin.manage.ConfigSavingTypeGenerics;
import voltskiya.apple.configs.plugin.manage.ConfigVoltskiya;
import voltskiya.apple.configs.plugin.saveable.ConfigSaveableBuilder;

public class ConfigGsonBuilder<Config,
        SpecificOutput extends ConfigVoltskiya<Config>>
        extends ConfigSaveableBuilder<Config, ConfigGsonBuilder<Config, SpecificOutput>, SpecificOutput> {
    public Gson gson = new Gson();

    public ConfigGsonBuilder(ConfigSavingTypeGenerics<Config, ConfigGsonBuilder<Config, SpecificOutput>, SpecificOutput> savingType) {
        super(savingType);
    }

    public ConfigGsonBuilder<Config, SpecificOutput> setGson(Gson gson) {
        this.gson = gson;
        return this;
    }

    @Override
    public ConfigGsonBuilder<Config, SpecificOutput> getBuilder() {
        return this;
    }
}
