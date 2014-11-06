package com.mw.admission.model;

import java.io.Serializable;
import java.util.Date;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.mw.admission.extra.MyApp;

public class Ticket implements Serializable {

	private static final long serialVersionUID = -4396689988903006695L;

	Context context;

	public Ticket() {
		super();
	}

	public Ticket(Context context) {
		this.context = context;
	}

	@SerializedName("id")
	String ticketId;

	@SerializedName("name")
	String nameOfGuest;

	@SerializedName("barcode")
	String barcode;

	@SerializedName("order_id")
	String orderId;

	@SerializedName("order_quantity")
	int orderQuantity;

	@SerializedName("quantity")
	int quantityTicket;

	@SerializedName("checked_in")
	boolean checkedIn;

//	 @SerializedName("scanned_at")
//	 Date scanTime;

	@SerializedName("scanned_at")
	String scanned_at;

	Date scanTime;

	@SerializedName("scanner_id")
	String scannerID;

	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", nameOfGuest=" + nameOfGuest
				+ "]";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Ticket) {
			if (this.getTicketId().equals(((Ticket) o).getTicketId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getNameOfGuest() {
		return nameOfGuest;
	}

	public void setNameOfGuest(String nameOfGuest) {
		this.nameOfGuest = nameOfGuest;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public int getQuantityTicket() {
		return quantityTicket;
	}

	public void setQuantityTicket(int quantityTicket) {
		this.quantityTicket = quantityTicket;
	}

	public Date getScanTime() {
		return scanTime;
	}

	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}

	public boolean isCheckedIn() {
		return checkedIn;
	}

	public void setCheckedIn(boolean checkedIn) {
		this.checkedIn = checkedIn;
	}

	public String getScannerID() {
		return scannerID;
	}

	public void setScannerID(String scannerID) {
		this.scannerID = scannerID;
	}

	public String getScanned_at() {
		return scanned_at;
	}

	public void setScanned_at(String scanned_at) {
		this.scanned_at = scanned_at;
		setScanTime(((MyApp) context.getApplicationContext())
				.formatStringToDate(this.scanned_at));
	}

	public void setScanTimeAndScannerIDAndCheckedIn(Date date,
			String scannerID, boolean checkedIn) {
		setScanTime(date);
		setScannerID(scannerID);
		setCheckedIn(checkedIn);
	}
}