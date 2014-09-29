package com.mw.admission.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TicketDetailActivity extends Activity {

	TextView labelActionBarTV;
	TextView labelHeaderTV;
	TextView selectedEventTV;

	private void initThings() {
	}

	private void findThings() {
		labelActionBarTV = (TextView) findViewById(R.id.label_action_TV);
		labelHeaderTV = (TextView) findViewById(R.id.label_TV);
		selectedEventTV = (TextView) findViewById(R.id.selectedEvent_TV);
	}

	private void initView() {
		labelActionBarTV.setText("Will Call");
		labelHeaderTV.setText("Ticket Detail");
		selectedEventTV.setText("TODO");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		findThings();
		initThings();
		initView();

	}
	
}
