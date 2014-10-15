package com.mw.admission.activity;

import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mw.admission.extra.MyApp;
import com.mw.admission.model.Ticket;

public class TicketDetailActivity extends MenuButtonActivity {

	boolean isHeaderThere = true;

	MyApp myApp;
	Intent previousIntent;

	int position;
	Ticket selectedTicket;

	LayoutInflater inflater;

	LinearLayout parentLL;
	LinearLayout oldChildLL;
	View newChildLL;

	TextView nameTV, barcodeTV, statusTV, orderNumberTV, numberOfTicketsTV,
			actionTV, action2TV;
	TextView scanDateTV, scannerIDTV;

	TextView label_name_TV, label_barcode_TV, label_status_TV, label_order_TV,
			label_ticket_TV, label_action_TV;

	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	Gson gson;

	private void initThings() {
		previousIntent = getIntent();

		myApp = (MyApp) getApplicationContext();
		
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		editor = sharedPreferences.edit();
		
		GsonBuilder builder = new GsonBuilder();
		gson = builder.create();

		position = previousIntent.getIntExtra("position", 0);
		selectedTicket = myApp.getTicketList().get(position);

		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		newChildLL = inflater.inflate(R.layout.child_ticket_details, null);
	}

	private void setTypeface() {

		nameTV.setTypeface(myApp.getTypefaceRegularSans());
		barcodeTV.setTypeface(myApp.getTypefaceRegularSans());
		statusTV.setTypeface(myApp.getTypefaceRegularSans());
		orderNumberTV.setTypeface(myApp.getTypefaceRegularSans());
		numberOfTicketsTV.setTypeface(myApp.getTypefaceRegularSans());

		actionTV.setTypeface(myApp.getTypefaceRegularSans());
		action2TV.setTypeface(myApp.getTypefaceRegularSans());

		label_name_TV.setTypeface(myApp.getTypefaceRegularSans());
		label_barcode_TV.setTypeface(myApp.getTypefaceRegularSans());
		label_status_TV.setTypeface(myApp.getTypefaceRegularSans());
		label_order_TV.setTypeface(myApp.getTypefaceRegularSans());
		label_ticket_TV.setTypeface(myApp.getTypefaceRegularSans());
		label_action_TV.setTypeface(myApp.getTypefaceRegularSans());
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

		actionTV = (TextView) findViewById(R.id.action_TV);
		action2TV = (TextView) findViewById(R.id.action2_TV);

		label_name_TV = (TextView) findViewById(R.id.label_name_TV);
		label_barcode_TV = (TextView) findViewById(R.id.label_barcode_TV);
		label_status_TV = (TextView) findViewById(R.id.label_status_TV);
		label_order_TV = (TextView) findViewById(R.id.label_order_TV);
		label_ticket_TV = (TextView) findViewById(R.id.label_ticket_TV);
		label_action_TV = (TextView) findViewById(R.id.label_action_TV);

		setTypeface();
	}

	private void findThingsForNewView() {
		scanDateTV = (TextView) findViewById(R.id.scanDate_TV);
		scannerIDTV = (TextView) findViewById(R.id.scannerID_TV);

		scanDateTV.setTypeface(myApp.getTypefaceRegularSans());
		scannerIDTV.setTypeface(myApp.getTypefaceRegularSans());
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
			onAdmit(null);
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ticket_details);

		initThings();
		findThings();
		initView();

	}

	public void onAdmit(View view) {
		if (view != null) {
			selectedTicket.setScanTimeAndScannerIDAndCheckedIn(new Date(),
					myApp.getLoginUser().getUsername(), true);

			myApp.getTicketList().add(position, selectedTicket);

			// TODO update order map
			// BEWARE** This code leads to OutOfMemoryException **BEWARE

			// List<Ticket> tempTicketList = myApp.getOrderMap().get(
			// selectedTicket.getOrderId());
			// for (int i = 0; i < tempTicketList.size(); i++) {
			// if (tempTicketList.get(i).getBarcode()
			// .equals(selectedTicket.getBarcode())) {
			// tempTicketList.add(i, selectedTicket);
			// }
			// }
			myApp.isTicketValid(selectedTicket.getBarcode(), true, position);

			// TODO update preferences
			editor.putString("ticketList",
					gson.toJson(myApp.getTicketList()));
			editor.commit();
		}

		statusTV.setText("Admitted");
		statusTV.setTextColor(Color.parseColor("#ff0000"));

		// replace view in parent LL
		parentLL.removeView(oldChildLL);
		parentLL.addView(newChildLL);

		findThingsForNewView();

		scanDateTV.setText(selectedTicket.getScanTime().toString());
		scannerIDTV.setText(selectedTicket.getScannerID());

	}

	public void onAdmitAll(View view) {

	}

}
