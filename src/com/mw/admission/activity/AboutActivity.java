package com.mw.admission.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends Activity {

	TextView labelTV;
	TextView selectedEventTV;

	private void initThings() {
	}

	private void findThings() {
		labelTV = (TextView) findViewById(R.id.label_TV);
		selectedEventTV = (TextView) findViewById(R.id.selectedEvent_TV);
	}

	private void initView() {
		labelTV.setText("About");
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
