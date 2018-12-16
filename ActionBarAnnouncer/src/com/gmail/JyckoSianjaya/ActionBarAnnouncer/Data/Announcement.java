package com.gmail.JyckoSianjaya.ActionBarAnnouncer.Data;

import java.util.ArrayList;
import java.util.List;

public class Announcement {
	private int delays = 20;
	private ArrayList<String> announces = new ArrayList<String>();
	public Announcement(int delay, List<String> announces) {
		this.delays = delay;
		this.announces = new ArrayList<String>(announces);
	}
	public int getDelay() {
		return delays;
	}
	public String getAnnouncement(int index) {
		if (index >= announces.size()) {
			return null;
		}
		return announces.get(index);
	}
	public ArrayList<String> getAnnouncements() {
		return this.announces;
	}
	
}
