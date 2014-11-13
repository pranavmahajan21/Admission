package com.mw.admission.activity;

import java.util.List;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.mw.admission.extra.MyApp;
import com.mw.admission.model.Event;
import com.mw.admission.model.Scan;
import com.mw.admission.model.Ticket;

public class ReportActivity extends MenuButtonActivity {

	boolean isHeaderThere = true;

	List<Scan> scanList;
	List<Ticket> ticketList;
	MyApp myApp;

	TextView label_last_TV, label_start_TV, label_ticket_TV, label_event_TV,
			label_admission_event_TV, label_rejection_event_TV,
			label_scanner_TV, label_admission_scanner_TV,
			label_rejection_scanner_TV;

	TextView start_TV, ticket_TV, admission_event_TV, rejection_event_TV,
			admission_scanner_TV, rejection_scanner_TV;

	private void initThings() {
		myApp = (MyApp) getApplicationContext();
		scanList = myApp.getScanList();
		ticketList = myApp.getTicketList();
	}

	private void setTypeface() {
		start_TV.setTypeface(myApp.getTypefaceRegularSans());
		ticket_TV.setTypeface(myApp.getTypefaceRegularSans());
		admission_event_TV.setTypeface(myApp.getTypefaceRegularSans());
		rejection_event_TV.setTypeface(myApp.getTypefaceRegularSans());
		admission_scanner_TV.setTypeface(myApp.getTypefaceRegularSans());
		rejection_scanner_TV.setTypeface(myApp.getTypefaceRegularSans());

		label_last_TV.setTypeface(myApp.getTypefaceRegularSans());
		label_start_TV.setTypeface(myApp.getTypefaceRegularSans());
		label_ticket_TV.setTypeface(myApp.getTypefaceRegularSans());
		label_event_TV.setTypeface(myApp.getTypefaceRegularSans());
		label_admission_event_TV.setTypeface(myApp.getTypefaceRegularSans());
		label_rejection_event_TV.setTypeface(myApp.getTypefaceRegularSans());
		label_scanner_TV.setTypeface(myApp.getTypefaceRegularSans());
		label_admission_scanner_TV.setTypeface(myApp.getTypefaceRegularSans());
		label_rejection_scanner_TV.setTypeface(myApp.getTypefaceRegularSans());
	}

	public void findThings(boolean isHeaderThere) {
		super.findThings(isHeaderThere);

		start_TV = (TextView) findViewById(R.id.start_TV);
		ticket_TV = (TextView) findViewById(R.id.ticket_TV);
		admission_event_TV = (TextView) findViewById(R.id.admission_event_TV);
		rejection_event_TV = (TextView) findViewById(R.id.rejection_event_TV);
		admission_scanner_TV = (TextView) findViewById(R.id.admission_scanner_TV);
		rejection_scanner_TV = (TextView) findViewById(R.id.rejection_scanner_TV);

		label_last_TV = (TextView) findViewById(R.id.label_last_TV);
		label_start_TV = (TextView) findViewById(R.id.label_start_TV);
		label_ticket_TV = (TextView) findViewById(R.id.label_ticket_TV);
		label_event_TV = (TextView) findViewById(R.id.label_event_TV);
		label_admission_event_TV = (TextView) findViewById(R.id.label_admission_event_TV);
		label_rejection_event_TV = (TextView) findViewById(R.id.label_rejection_event_TV);
		label_scanner_TV = (TextView) findViewById(R.id.label_scanner_TV);
		label_admission_scanner_TV = (TextView) findViewById(R.id.label_admission_scanner_TV);
		label_rejection_scanner_TV = (TextView) findViewById(R.id.label_rejection_scanner_TV);

		setTypeface();
	}

	public void initView() {
		super.initView();
		getLabelActionBarTV().setText("Reports");
		getLabelHeaderTV().setText("Admission Report");

		Event tempEvent = myApp.getSelectedEvent();

		start_TV.setText(myApp.formatDateToString2(tempEvent.getScanStartDate()));

		// TODO: check if the code breaks if no ticket was fetched. Basically is
		// myApp.getTicketList() is null or 0.
		int count = 0;
		for (int i = 0; i < ticketList.size(); i++) {
			if (ticketList.get(i).isCheckedIn()) {
				count++;
			}
		}
		admission_event_TV.setText(Integer.toString(count));

		int count2 = 0;
		for (int i = 0; i < scanList.size(); i++) {
			if (scanList.get(i).getResult() == 0) {
				count2++;
			}
		}
		if (count2 > 0) {
			admission_scanner_TV.setText(Integer.toString(count2));
			rejection_scanner_TV.setText(Integer.toString(scanList.size()
					- count2));

		} else {
			admission_scanner_TV.setText("-");
			rejection_scanner_TV.setText("-");
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_report);

		initThings();
		findThings(isHeaderThere);
		initView();

	}

}
