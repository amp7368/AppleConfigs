package voltskiya.apple.configs.plugin.yml;

import org.bukkit.configuration.InvalidConfigurationException;
import voltskiya.apple.configs.plugin.saveable.ConfigVoltskiyaSaveableSingleton;
import ycm.yml.manager.ycm.Ycm;

import java.io.IOException;

public class ConfigVoltskiyaYml<Config> extends ConfigVoltskiyaSaveableSingleton<Config> {
    protected Ycm ycm;

    public ConfigVoltskiyaYml(ConfigYmlBuilder<Config, ConfigVoltskiyaYml<Config>> builder) {
        super(builder);
        this.ycm = builder.ycm;
    }

    @Override
    public void saveInstance() throws IOException {
        getYcm().toFile(instance, getFile());
    }

    @Override
    public Config loadInstance() throws IOException, InvalidConfigurationException {
        return ycm.toConfig(getFile(), getConfigClass());
    }

    protected Ycm getYcm() {
        return ycm;
    }

    protected void setYcm(Ycm ycm) {
        this.ycm = ycm;
    }
}
