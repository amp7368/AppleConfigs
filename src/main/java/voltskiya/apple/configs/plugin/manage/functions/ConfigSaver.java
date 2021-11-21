package voltskiya.apple.configs.plugin.manage.functions;

@FunctionalInterface
public interface ConfigSaver<Config> {
    void saveInstance(Config instance);
}
