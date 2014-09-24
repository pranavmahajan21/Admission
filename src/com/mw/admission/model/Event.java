package com.mw.admission.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Event implements Serializable {

	private static final long serialVersionUID = -5990446164790566066L;

	String id, name;

	Date date, scanStartDate;

	long total, totalCheckedIn;

	List<Ticket> ticketList;
	List<Scan> scanList;
	
	public Event()
	{
		
	}

	public Event(String name, Date date) {
		super();
		this.name = name;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getScanStartDate() {
		return scanStartDate;
	}

	public void setScanStartDate(Date scanStartDate) {
		this.scanStartDate = scanStartDate;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getTotalCheckedIn() {
		return totalCheckedIn;
	}

	public void setTotalCheckedIn(long totalCheckedIn) {
		this.totalCheckedIn = totalCheckedIn;
	}

	public List<Ticket> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<Ticket> ticketList) {
		this.ticketList = ticketList;
	}

	public List<Scan> getScanList() {
		return scanList;
	}

	public void setScanList(List<Scan> scanList) {
		this.scanList = scanList;
	}

}
