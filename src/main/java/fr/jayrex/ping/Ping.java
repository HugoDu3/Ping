package fr.jayrex.ping;

import com.google.common.io.ByteStreams;
import fr.jayrex.ping.commands.PingCommand;
import fr.jayrex.ping.listeners.TabCompleteListener;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;


public class Ping extends Plugin {
    private Configuration config;
    private Ping instance;


    public void onEnable() {
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new PingCommand(this));
        ProxyServer.getInstance().getPluginManager().registerListener(this, new TabCompleteListener(this));
        this.loadConfig();
    }

    private void loadConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }
            File config = new File(getDataFolder().getPath(), "config.yml");
            if (!config.exists()) {
                try {
                    config.createNewFile();
                    try (InputStream is = getResourceAsStream("config.yml");
                         OutputStream os = new FileOutputStream(config)) {
                        ByteStreams.copy(is, os);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Impossible de créer cela", e);
                }
            }
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfig() {
        return config;
    }

    public Ping getInstance() {
        return instance;
    }
}