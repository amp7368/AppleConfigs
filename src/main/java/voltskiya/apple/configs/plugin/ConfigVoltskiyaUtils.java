package voltskiya.apple.configs.plugin;

import voltskiya.apple.configs.plugin.manage.functions.ConfigCreateEmpty;

import java.lang.reflect.InvocationTargetException;

public class ConfigVoltskiyaUtils {
    public static <Config> ConfigCreateEmpty<Config> getDefaultConstructor(Class<Config> configClass) {
        return () -> {
            try {
                return configClass.getConstructor().newInstance();
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        };
    }
}
