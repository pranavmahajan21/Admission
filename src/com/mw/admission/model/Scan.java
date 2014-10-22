package com.mw.admission.model;

import java.io.Serializable;
import java.util.Date;

public class Scan implements Serializable {

	private static final long serialVersionUID = 6937381401958484517L;

	String barcode;

	int result;
	// 0 -> valid barcode & not checked in i.e. ideal case
	// 1 -> valid barcode & checked in i.e. duplicate
	// 2 -> invalid barcode

	Date scanDate;

	public Scan() {
		super();
	}

	public Scan(String barcode, int result, Date scanDate) {
		super();
		this.barcode = barcode;
		this.result = result;
		this.scanDate = scanDate;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public Date getScanDate() {
		return scanDate;
	}

	public void setScanDate(Date scanDate) {
		this.scanDate = scanDate;
	}

}
