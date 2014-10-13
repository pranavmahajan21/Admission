package com.mw.admission.activity;

import com.mw.admission.extra.MyApp;
import com.mw.admission.model.Event;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class ReportActivity extends MenuButtonActivity {

	boolean isHeaderThere = true;

	MyApp myApp;

	TextView label_last_TV, label_start_TV, label_ticket_TV, label_event_TV,
			label_admission_event_TV, label_rejection_event_TV,
			label_scanner_TV, label_admission_scanner_TV,
			label_rejection_scanner_TV;

	TextView start_TV, ticket_TV, admission_event_TV, rejection_event_TV,
			admission_scanner_TV, rejection_scanner_TV;

	private void initThings() {
		myApp = (MyApp) getApplicationContext();
	}

	private void setTypeface() {

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

		start_TV.setText(myApp.formatDate2(tempEvent.getScanStartDate()));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_report);

		findThings(isHeaderThere);
		initThings();
		initView();

	}

}
