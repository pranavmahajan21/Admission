package com.mw.admission.activity;

import com.mw.admission.extra.MyApp;
import com.mw.admission.model.Ticket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TicketDetailActivity extends MenuButtonActivity {

	boolean isHeaderThere = true;

	MyApp myApp;
	Intent previousIntent;

	Ticket selectedTicket;

	LayoutInflater inflater;

	LinearLayout parentLL;
	LinearLayout oldChildLL;
	View newChildLL;

	TextView nameTV, barcodeTV, statusTV, orderNumberTV, numberOfTicketsTV;
	TextView scanDateTV, scannerIDTV;

	private void initThings() {
		previousIntent = getIntent();

		myApp = (MyApp) getApplicationContext();
		selectedTicket = myApp.getTicketList().get(
				previousIntent.getIntExtra("position", 0));

		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		newChildLL = inflater.inflate(R.layout.child_ticket_details, null);
	}

	public void findThings() {
		super.findThings(isHeaderThere);

		parentLL = (LinearLayout) findViewById(R.id.parent_LL);
		oldChildLL = (LinearLayout) findViewById(R.id.child_LL);

		nameTV = (TextView) findViewById(R.id.name_TV);
		barcodeTV = (TextView) findViewById(R.id.barcode_TV);
		statusTV = (TextView) findViewById(R.id.status_TV);
		orderNumberTV = (TextView) findViewById(R.id.orderNumber_TV);
		numberOfTicketsTV = (TextView) findViewById(R.id.numberOfTickets_TV);
	}

	private void findThingsForNewView() {
		scanDateTV = (TextView) findViewById(R.id.scanDate_TV);
		scannerIDTV = (TextView) findViewById(R.id.scannerID_TV);
	}

	public void initView() {
		super.initView();
		getLabelActionBarTV().setText("Will Call");
		getLabelHeaderTV().setText("Ticket Detail");

		nameTV.setText(selectedTicket.getNameOfGuest());
		barcodeTV.setText(selectedTicket.getBarcode());
		orderNumberTV.setText(selectedTicket.getOrderId());

		// If we don't convert int to String it gives ResourceNotFoundException,
		// don't know why
		numberOfTicketsTV.setText(Integer.toString(selectedTicket
				.getOrderQuantity()));

		if (selectedTicket.isCheckedIn()) {
			statusTV.setText("Admitted");

			// replace view in parent LL
			parentLL.removeView(oldChildLL);
			parentLL.addView(newChildLL);

			findThingsForNewView();

			scanDateTV.setText(selectedTicket.getScanTime().toString());
			scannerIDTV.setText(selectedTicket.getScannerID());
		}

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

	public void onAdmit(View view) {

	}

	public void onAdmitAll(View view) {

	}

}
