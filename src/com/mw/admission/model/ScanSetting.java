package com.mw.admission.model;

import java.io.Serializable;

public class ScanSetting implements Serializable {

	private static final long serialVersionUID = 1760437485423937612L;

	boolean isOnlineMode, isContinuousScan, hasSound, hasFlashlight;

	public boolean isOnlineMode() {
		return isOnlineMode;
	}

	public void setOnlineMode(boolean isOnlineMode) {
		this.isOnlineMode = isOnlineMode;
	}

	public boolean isContinuousScan() {
		return isContinuousScan;
	}

	public void setContinuousScan(boolean isContinuousScan) {
		this.isContinuousScan = isContinuousScan;
	}

	public boolean isHasSound() {
		return hasSound;
	}

	public void setHasSound(boolean hasSound) {
		this.hasSound = hasSound;
	}

	public boolean isHasFlashlight() {
		return hasFlashlight;
	}

	public void setHasFlashlight(boolean hasFlashlight) {
		this.hasFlashlight = hasFlashlight;
	}

}
