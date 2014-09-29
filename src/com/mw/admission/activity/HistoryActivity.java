package com.mw.admission.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class HistoryActivity extends MenuButtonActivity {

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
		labelActionBarTV.setText("History");
		labelHeaderTV.setText("History");
		selectedEventTV.setText("TODO");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about);

		findThings();
		initThings();
		initView();

	}
	
}
