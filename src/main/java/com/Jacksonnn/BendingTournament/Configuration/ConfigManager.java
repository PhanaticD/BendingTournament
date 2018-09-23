package com.Jacksonnn.BendingTournament.Configuration;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;

public class ConfigManager {
    public static Config langConfig;
    public static Config defaultConfig;
    public static Config killConfig;
    public static Config bannedWords;


    public ConfigManager() {
        defaultConfig = new Config(new File("config.yml"));
        langConfig = new Config(new File("language.yml"));
        configCheck(ConfigType.DEFAULT);
        configCheck(ConfigType.LANGUAGE);
    }

    public static void configCheck(ConfigType type) {
        FileConfiguration config;
        if (type == ConfigType.DEFAULT) {
            config = defaultConfig.get();

            config.addDefault("Storage.engine", "sqlite");

            config.addDefault("Storage.settings.host", "localhost");
            config.addDefault("Storage.settings.port", 3306);
            config.addDefault("Storage.settings.password", "");
            config.addDefault("Storage.settings.database", "minecraft");
            config.addDefault("Storage.settings.username", "root");
            config.addDefault("Storage.settings.ssl", false);

            config.addDefault("Storage.settings.path", "db.sql");
            defaultConfig.save();
        } else if (type == ConfigType.LANGUAGE) {
            config = langConfig.get();

            config.addDefault("Language", "");
            langConfig.save();
        }
    }
}