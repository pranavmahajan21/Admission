package com.mw.admission.activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class TicketDetailActivity extends MenuButtonActivity {

	TextView labelActionBarTV;
	TextView labelHeaderTV;
	TextView selectedEventTV;

	private void initThings() {
	}

	public void findThings() {
		labelActionBarTV = (TextView) findViewById(R.id.label_action_TV);
		labelHeaderTV = (TextView) findViewById(R.id.label_TV);
		selectedEventTV = (TextView) findViewById(R.id.selectedEvent_TV);
	}

	public void initView() {
		labelActionBarTV.setText("Will Call");
		labelHeaderTV.setText("Ticket Detail");
		selectedEventTV.setText("TODO");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ticket_details);

		findThings();
		initThings();
		initView();

	}
	
}
