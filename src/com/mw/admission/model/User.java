package com.mw.admission.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class User implements Serializable {

	private static final long serialVersionUID = -7719557886765194596L;

	boolean isLoggedIn;
	boolean isPatternMode;
	int selectedEventIndex;

	@SerializedName("login")
	String username;
	
	String token;

	String email;

	List<Event> eventList;
	Date startTime;

	ScanSetting scanSetting;

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public boolean isPatternMode() {
		return isPatternMode;
	}

	public void setPatternMode(boolean isPatternMode) {
		this.isPatternMode = isPatternMode;
	}

	public int getSelectedEventIndex() {
		return selectedEventIndex;
	}

	public void setSelectedEventIndex(int selectedEventIndex) {
		this.selectedEventIndex = selectedEventIndex;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<Event> getEventList() {
		return eventList;
	}

	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public ScanSetting getScanSetting() {
		return scanSetting;
	}

	public void setScanSetting(ScanSetting scanSetting) {
		this.scanSetting = scanSetting;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", email=" + email + ", token="
				+ token + "]";
	}

}