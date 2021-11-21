package voltskiya.apple.configs.plugin.manage.functions;

import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;

@FunctionalInterface
public interface ConfigLoadable<Config> {
    Config loadInstance() throws IOException, InvalidConfigurationException;
}
