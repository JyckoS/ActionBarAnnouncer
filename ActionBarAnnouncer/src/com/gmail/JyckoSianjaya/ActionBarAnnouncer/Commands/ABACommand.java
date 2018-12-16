package com.gmail.JyckoSianjaya.ActionBarAnnouncer.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.JyckoSianjaya.ActionBarAnnouncer.Data.ConfigStorage;
import com.gmail.JyckoSianjaya.ActionBarAnnouncer.Main.ActionBarAnnouncer;
import com.gmail.JyckoSianjaya.ActionBarAnnouncer.Utility.Utility;

import me.clip.placeholderapi.PlaceholderAPI;

public class ABACommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("ActionBarAnnouncer.admin")) return true;
		recheck(sender, cmd, args);
		return true;
	}
	private void recheck(CommandSender sender, Command cmd, String[] args) {
		Player p = null;
		if (sender instanceof Player) p = (Player) sender;
		Boolean isPlayer = p != null;
		if (args.length == 0) {
			if (isPlayer) {
			Utility.sendCenteredMessage(p, "&b&lAction&3&lBar&f&lAnnouncer &7by &aGober");
			Utility.sendCenteredMessage(p, "&7Type &f\"/actionbarannouncer help\" &7for help!");
			return;
			}
			Utility.sendMsg(sender, "&b&lAction&3&lBar&f&lAnnouncer &7by &aGober");
			Utility.sendMsg(sender, "&7Type &f\"/actionbarannouncer help\" &7for help!");
			return;
		}
		switch (args[0]) {
		case "help":
			default:
				Utility.sendMsg(sender, "&b&l  Action&3&lBar&f&lAnnouncer");
				Utility.sendMsg(sender, "&7Available Commands:");
				Utility.sendMsg(sender, "&8> &f/aba &creload");
				Utility.sendMsg(sender, "&8> &f/aba &ebroadcast <Message>");
				Utility.sendMsg(sender, "&8> &f/aba &esend <Player> <Message>");
				return;
		case "reload":
			ConfigStorage.getInstance().reloadConfig();
			Utility.sendMsg(sender, "&c> &7Config Reloaded!");
			return;
		case "broadcast":
		{
			if (args.length < 2) {
				Utility.sendMsg(sender, "&c&l<!> &7Please use &f/aba broadcast <ToBroadcast>");
				return;
			}
				String broadcast = "";
				for (int i = 1; i < args.length; i++) {
					broadcast = broadcast + args[i] + " ";
				}
				for (Player player : Bukkit.getOnlinePlayers()) {
					String clon = broadcast;
					if (ActionBarAnnouncer.isPAPIenabled()) {
						clon = PlaceholderAPI.setPlaceholders(player, clon);
					}
					Utility.sendActionBar(player, clon);
				}
				Utility.sendMsg(sender, "&a&l<!> &aSuccessfully sent the broadcast message: " + broadcast);
				return;
		}
		case "send":
		{
			if (args.length < 3) {
				Utility.sendMsg(sender, "&c&l<!> &7Please use &f/aba send <Player> <Message>");
				return;
			}
				String broadcast = args[1];
				if (Bukkit.getPlayer(broadcast) == null) {
					Utility.sendMsg(sender, "&c&l<!> &cThat person is not online!");
					return;
				}
				String tomessage = "";
				for (int i = 2; i < args.length; i++) {
					tomessage = tomessage + args[i] + " ";
					continue;
				}
				Player target = Bukkit.getPlayer(broadcast);
				if (ActionBarAnnouncer.isPAPIenabled()) {
					tomessage = PlaceholderAPI.setPlaceholders(target, tomessage);
				}
				Utility.sendActionBar(target, tomessage);
				Utility.sendMsg(sender, "&a&l<!> &aSuccessfully sent the " + target.getName() + " the message: " + tomessage);
				return;
		}
		}
	}
}
