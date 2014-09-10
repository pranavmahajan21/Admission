package com.mw.admission.model;

import java.io.Serializable;
import java.util.Date;

public class Scan implements Serializable {

	private static final long serialVersionUID = 6937381401958484517L;

	String barcode, result;
	Date scanDate;

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getScanDate() {
		return scanDate;
	}

	public void setScanDate(Date scanDate) {
		this.scanDate = scanDate;
	}

}
