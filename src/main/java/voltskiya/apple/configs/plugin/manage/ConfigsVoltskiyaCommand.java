package voltskiya.apple.configs.plugin.manage;

import apple.utilities.structures.Pair;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import voltskiya.apple.configs.plugin.ConfigPluginYmlConfigs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConfigsVoltskiyaCommand implements CommandExecutor, TabCompleter {
    private static final Gson GSON = new Gson();

    public ConfigsVoltskiyaCommand() {
        PluginCommand command = Bukkit.getPluginCommand("config");
        if (command == null) {
            ConfigPluginYmlConfigs.get().logger().severe("There is no command 'config' in VoltskiyaConfigs");
            return;
        }
        command.setPermission("voltskiya.config.edit");
        command.setExecutor(this);
        command.setTabCompleter(this);
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("voltskiya.config.edit")) return false;
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "To few arguments");
            return false;
        }
        String getOrSet = args[0];
        String configName = args[1];
        ConfigVoltskiya<?> config = ConfigsVoltskiyaManager.get().getConfig(configName);
        List<String> restOfArgs = new ArrayList<>(Arrays.asList(args).subList(2, args.length));
        if (config == null) {
            sender.sendMessage(ChatColor.RED + String.format("'%s' is not a valid config file", configName));
            return false;
        }
        if (getOrSet.equalsIgnoreCase("get")) {
            return onGetCommand(sender, config, restOfArgs);
        } else if (getOrSet.equalsIgnoreCase("set")) {
            List<String> propertyPath = restOfArgs.subList(0, restOfArgs.size() - 1);
            String lastArg = restOfArgs.get(restOfArgs.size() - 1);
            return onSetCommand(sender, config, propertyPath, lastArg);
        } else {
            sender.sendMessage(ChatColor.RED + "Invalid subcommand");
            return false;
        }
    }


    private boolean onGetCommand(CommandSender sender, ConfigVoltskiya<?> config, List<String> propertyPath) {
        @Nullable ConfigVoltskiya.FieldThing fieldThing = config.getFieldInPath(propertyPath);
        if (fieldThing == null) {
            sender.sendMessage(ChatColor.RED + "No such field");
            return false;
        }
        sender.sendMessage(ChatColor.GREEN + fieldThing.getFieldName() + " is set to " + ChatColor.AQUA + GSON.toJson(fieldThing.getInstance()));
        return true;
    }

    private boolean onSetCommand(CommandSender sender, ConfigVoltskiya<?> config, List<String> propertyPath, String setValue) {
        @Nullable ConfigVoltskiya.FieldThing fieldThing = config.getFieldInPath(propertyPath);
        if (fieldThing == null) {
            sender.sendMessage(ChatColor.RED + "No such field");
            return false;
        }
        boolean success = fieldThing.setFieldToValue(setValue);
        config.saveQueue();
        if (success)
            sender.sendMessage(ChatColor.GREEN + fieldThing.getFieldName() + " is now set to " + ChatColor.AQUA + GSON.toJson(fieldThing.getInstance()));
        else
            sender.sendMessage(ChatColor.RED + fieldThing.getFieldName() + " could not be set to " + ChatColor.AQUA + GSON.toJson(fieldThing.getInstance()));
        return true;
    }

    @Override
    @Nullable
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("get", "set");
        } else if (args.length == 2) {
            return ConfigsVoltskiyaManager.get().getConfigStrings();
        } else {
            ConfigVoltskiya<?> configRetrieved = ConfigsVoltskiyaManager.get().getConfig(args[1]);
            if (configRetrieved == null) return null;

            List<String> propertyPath = new ArrayList<>(Arrays.asList(args).subList(2, args.length));
            @Nullable Pair<ConfigVoltskiya.FieldThing, List<String>> fieldsInPath = configRetrieved.getFieldNamesUnderPath(propertyPath);
            if (fieldsInPath == null) {
                propertyPath = new ArrayList<>(Arrays.asList(args).subList(2, args.length - 1));
                fieldsInPath = configRetrieved.getFieldNamesUnderPath(propertyPath);
            }
            if (fieldsInPath == null) {
                return Collections.emptyList();
            }
            if (fieldsInPath.getValue().isEmpty()) {
                return List.of(fieldsInPath.getKey().getInstance().toString());
            }
            return fieldsInPath.getValue();
        }
    }
}
