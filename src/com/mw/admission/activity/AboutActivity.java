package com.mw.admission.activity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends MenuButtonActivity {

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
		labelActionBarTV.setText("About");
		labelHeaderTV.setText("About");
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
