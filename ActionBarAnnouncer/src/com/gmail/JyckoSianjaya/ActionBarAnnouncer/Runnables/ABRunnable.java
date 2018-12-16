package com.gmail.JyckoSianjaya.ActionBarAnnouncer.Runnables;

import java.util.ArrayList;

import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.JyckoSianjaya.ActionBarAnnouncer.Data.ConfigStorage;
import com.gmail.JyckoSianjaya.ActionBarAnnouncer.Main.ActionBarAnnouncer;

public class ABRunnable {
	private static ABRunnable instance;
	private ArrayList<ABTask> tasks = new ArrayList<ABTask>();
	private int delay = 1800;
	private ABRunnable() {
		new BukkitRunnable() {
			int currentdelay = 0;
			Boolean dotask = false;
			@Override
			public void run() {
				if (currentdelay < delay) {
					if (!dotask) {
					currentdelay++;
					}
				}
				if (dotask && tasks.isEmpty()) {
					ConfigStorage.getInstance().resetCacheAnnouncement();
					return;
				}
				else if (currentdelay >= delay) {
					if (tasks.isEmpty()) {
						ConfigStorage.getInstance().resetCacheAnnouncement();
					}
					currentdelay = 0;
					dotask = true;
				}
				if (!dotask) return;
				ABTask todo = tasks.get(0);
				if (todo.getHealth() <= 0) {
					dotask = false;
					tasks.remove(todo);
					currentdelay = 0;
				}
				todo.runTask();
				todo.reduceHealth();
		}
		}.runTaskTimerAsynchronously(ActionBarAnnouncer.getInstance(), 1L, 1L);
	}
	public void setDelay(int newdelay) {
		this.delay = newdelay;
	}
	public void clearTask() {
		tasks.clear();
	}
	public void addTask(ABTask task) {
		this.tasks.add(task);
	}
	public static boolean haveInstance() {
		return instance != null;
	}
	public static ABRunnable getInstance() {
		if (instance == null) instance = new ABRunnable();
		return instance;
	}
}
