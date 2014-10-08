package com.mw.admission.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.mw.admission.extra.MyApp;
import com.mw.admission.model.Ticket;

public class OrderDetailActivity extends MenuButtonActivity {

	boolean isHeaderThere = true;

	MyApp myApp;
	Intent previousIntent;

	LayoutInflater inflater;

	int position;

	TextView nameTV, orderNumberTV, numberOfTicketsTV;
	List<Ticket> ticketsOfOrderList;

	private void initThings() {
		previousIntent = getIntent();
		position = previousIntent.getIntExtra("position", 0);
		myApp = (MyApp) getApplicationContext();

		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		ticketsOfOrderList = new ArrayList<List<Ticket>>(myApp.getOrderMap().values()).get(position);
//				List<List<Ticket>> aa =new ArrayList<List<Ticket>>(myApp.getOrderMap().values());
//				(position);
	}

	private void findThings() {
		super.findThings(isHeaderThere);

		nameTV = (TextView) findViewById(R.id.name_TV);
		orderNumberTV = (TextView) findViewById(R.id.orderNumber_TV);
		numberOfTicketsTV = (TextView) findViewById(R.id.numberOfTickets_TV);
	}

	public void initView() {
		super.initView();
		getLabelActionBarTV().setText("Will Call");
		getLabelHeaderTV().setText("Order Detail");

		Ticket tempTicket = ticketsOfOrderList.get(0);

		nameTV.setText(tempTicket.getNameOfGuest());
		orderNumberTV.setText(tempTicket.getOrderId());

		numberOfTicketsTV.setText(Integer.toString(tempTicket
				.getOrderQuantity()));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_order_details);
		findThings();
		initThings();
		initView();
	}

	public void onAdmitIndividually(View view) {

	}

	public void onAdmitAll(View view) {

	}

}
