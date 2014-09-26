package com.mw.admission.model;

public class MenuItem {

	String text;
	boolean isArrowVisible;
	
	public MenuItem() {
		super();
	}

	public MenuItem(String text, boolean isArrowVisible) {
		super();
		this.text = text;
		this.isArrowVisible = isArrowVisible;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isArrowVisible() {
		return isArrowVisible;
	}

	public void setArrowVisible(boolean isArrowVisible) {
		this.isArrowVisible = isArrowVisible;
	}
	
}
