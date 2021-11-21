package voltskiya.apple.configs.plugin.saveable;

import voltskiya.apple.configs.plugin.manage.ConfigVoltskiya;

public abstract class ConfigVoltskiyaSaveable<Config> implements ConfigVoltskiya<Config> {
    protected Config instance;
    private boolean isBeingManaged = false;

    @Override
    public void setIsBeingManaged() {
        this.isBeingManaged = true;
    }

    @Override
    public boolean getIsBeingManaged() {
        return isBeingManaged;
    }

    @Override
    public Config getConfigInstance() {
        return instance;
    }

    @Override
    public void setConfigInstance(Config instance) {
        this.instance = instance;
    }
}
