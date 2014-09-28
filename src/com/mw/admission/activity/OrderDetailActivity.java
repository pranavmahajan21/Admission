package com.mw.admission.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class OrderDetailActivity extends Activity {

	TextView labelTV;
	TextView selectedEventTV;

	private void initThings() {
	}

	private void findThings() {
		labelTV = (TextView) findViewById(R.id.label_TV);
		selectedEventTV = (TextView) findViewById(R.id.selectedEvent_TV);
	}

	private void initView() {
		labelTV.setText("Order Detail");
		selectedEventTV.setText("TODO");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_details);
		findThings();
		initThings();
		initView();
	}

}
