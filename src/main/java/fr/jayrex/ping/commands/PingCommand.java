package fr.jayrex.ping.commands;

import fr.jayrex.ping.Ping;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;


public class PingCommand extends Command {
    private Ping plugin;

    public PingCommand(Ping plugin) {
        super("ping");
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
	public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage("Seul un joueur peut exécuter cette commande");
            return;
        }

        ProxiedPlayer p = (ProxiedPlayer) sender;
        if (args.length == 0) {
        	
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("ping.ping").replace("%ping%", "" + p.getPing())));
        } else {

            String target = args.length > 0 ? args[0] : null;
            ProxiedPlayer targetP = plugin.getProxy().getPlayer(target);
            if (targetP == null) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ping.ping-player")));
                return;
            }
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("ping.not-found")
                            .replace("%ping%", "" + targetP.getPing())
                            .replace("%target%", targetP.getName())));
        }
    }

}