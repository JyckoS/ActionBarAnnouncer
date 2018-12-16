package com.gmail.JyckoSianjaya.ActionBarAnnouncer.Main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.JyckoSianjaya.ActionBarAnnouncer.Commands.ABACommand;
import com.gmail.JyckoSianjaya.ActionBarAnnouncer.Data.ConfigStorage;
import com.gmail.JyckoSianjaya.ActionBarAnnouncer.Runnables.ABRunnable;
import com.gmail.JyckoSianjaya.ActionBarAnnouncer.Utility.ActionBarAPI;
import com.gmail.JyckoSianjaya.ActionBarAnnouncer.Utility.Utility;

public class ActionBarAnnouncer extends JavaPlugin {
	private static ActionBarAnnouncer instance;
	private ActionBarAPI aba;
	private ConfigStorage cfg;
	private ABRunnable rnable;
	private static Boolean isPAPI = false;
	@Override
	public void onEnable() {
		aba = ActionBarAPI.getInstance();
		instance = this;
		new Metrics(this);
		Utility.sendConsole("&7> Plugin Loading..");
		loadConfig();
		isPAPI = checkPAPI();
		if (isPAPI) {
			Utility.sendConsole("&7> Hooked with &cPlaceholderAPI..");
		}
		this.getCommand("actionbarannouncer").setExecutor(new ABACommand());
		rnable = ABRunnable.getInstance();
		cfg.resetCacheAnnouncement();
		rnable.setDelay(cfg.getDelay());
	}
	public static boolean isPAPIenabled() {
		return isPAPI;
	}
	public static ActionBarAnnouncer getInstance() { return instance; }
	private void loadConfig() {
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		Utility.sendConsole("&7> Config Loading..");
		cfg = ConfigStorage.getInstance();
	}
	private boolean checkPAPI() {
		return Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
	}
}
