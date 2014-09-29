package com.mw.admission.activity;

import android.os.Bundle;
import android.widget.TextView;

public class ContactActivity extends MenuButtonActivity {

	TextView labelActionBarTV;
	TextView labelTV;
	TextView selectedEventTV;

	private void initThings() {
	}

	private void findThings() {
		labelTV = (TextView) findViewById(R.id.label_TV);
		selectedEventTV = (TextView) findViewById(R.id.selectedEvent_TV);
	}

	private void initView() {
		labelTV.setText("Contact Us");
		selectedEventTV.setText("TODO");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);

		findThings();
		initThings();
		initView();

	}

}
