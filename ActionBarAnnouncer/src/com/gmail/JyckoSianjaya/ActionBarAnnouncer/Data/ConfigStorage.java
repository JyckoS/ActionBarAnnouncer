package com.gmail.JyckoSianjaya.ActionBarAnnouncer.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.gmail.JyckoSianjaya.ActionBarAnnouncer.Main.ActionBarAnnouncer;
import com.gmail.JyckoSianjaya.ActionBarAnnouncer.Runnables.ABRunnable;
import com.gmail.JyckoSianjaya.ActionBarAnnouncer.Runnables.ABTask;
import com.gmail.JyckoSianjaya.ActionBarAnnouncer.Utility.Utility;

import me.clip.placeholderapi.PlaceholderAPI;

public class ConfigStorage {
	private static ConfigStorage instance;
	private Boolean autoannounce = false;
	private int delay = 60;
	private ABRunnable rnable;
	private ArrayList<Announcement> toannounce = new ArrayList<Announcement>();
	private ArrayList<Announcement> cacheannouncement = new ArrayList<Announcement>();
	public HashMap<String, Announcement> announcements = new HashMap<String, Announcement>();
	private ConfigStorage() {
		reloadConfig();
	}
	public static ConfigStorage getInstance() {
		if (instance == null) instance = new ConfigStorage();
		return instance;
	}
	public void reloadConfig() {
		toannounce.clear();
		cacheannouncement.clear();
		ActionBarAnnouncer.getInstance().reloadConfig();
		FileConfiguration config = ActionBarAnnouncer.getInstance().getConfig();
		this.delay = config.getInt("announcements.delay");
		this.autoannounce = config.getBoolean("auto_announce");
		Set<String> keys = config.getConfigurationSection("announcements").getConfigurationSection("announces").getKeys(false);
		for (String str : keys) {
			ConfigurationSection section = config.getConfigurationSection("announcements").getConfigurationSection("announces").getConfigurationSection(str);
			int delaybetween = section.getInt("delay_between");
			List<String> announcements = section.getStringList("announces");
			Announcement announce = new Announcement(delaybetween, announcements);
			this.announcements.put(str, announce);
			this.toannounce.add(announce);
		}
		Utility.sendConsole("&7> Config &aloaded!");
		Utility.sendConsole("&a> Plugin loaded~");
		if (ABRunnable.haveInstance()) {
			ABRunnable.getInstance().clearTask();
		}
	}
	public void resetCacheAnnouncement() {
		cacheannouncement = new ArrayList<Announcement>(this.toannounce);
		for (Announcement ann : cacheannouncement) {
			ABRunnable.getInstance().addTask(new ABTask() {
				ArrayList<String> tobc = (ArrayList<String>) ann.getAnnouncements().clone();
				int delays = ann.getDelay();
				int current = 0;
				int health = 10;
				@Override
				public int getHealth() {
					// TODO Auto-generated method stub
					return health;
				}

				@Override
				public void reduceHealth() {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void runTask() {
					// TODO Auto-generated method stub
					if (tobc.isEmpty()) {
						health = 0;
						return;
					}
					if (current < delays) {
						current++;
					}
					if (current >= delays) {
						for (Player p : Bukkit.getOnlinePlayers()) {
							String tobr = tobc.get(0);
							tobc.remove(0);
							if (ActionBarAnnouncer.isPAPIenabled()) {
								tobr = PlaceholderAPI.setPlaceholders(p, tobr);
							}
							Utility.sendActionBar(p, tobr);
						}
						current = 0;
						return;
					}
				}

			});
		}
	}
	public boolean doesAutoAnnounce() { return this.autoannounce; }
	public int getDelay() { return this.delay; }
	public Announcement getCacheAnnouncement() {
		if (cacheannouncement.size() <= 0) {
			cacheannouncement = new ArrayList<Announcement>(this.toannounce);
		}
		return cacheannouncement.get(0);
	}
}
