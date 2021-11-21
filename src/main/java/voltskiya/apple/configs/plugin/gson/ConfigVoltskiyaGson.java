package voltskiya.apple.configs.plugin.gson;

import com.google.gson.Gson;
import voltskiya.apple.configs.plugin.saveable.ConfigVoltskiyaSaveableSingleton;

import java.io.*;

public class ConfigVoltskiyaGson<Config> extends ConfigVoltskiyaSaveableSingleton<Config> {
    protected Gson gson;

    public ConfigVoltskiyaGson(ConfigGsonBuilder<Config, ConfigVoltskiyaGson<Config>> builder) {
        super(builder);
        this.gson = builder.gson;
    }

    @Override
    public void saveInstance() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFile()))) {
            gson.toJson(getConfigInstance(), writer);
        }
    }

    @Override
    public Config loadInstance() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(getFile()))) {
            return gson.fromJson(reader, getConfigClass());
        }
    }
}
