package com.mw.admission.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mw.admission.adapter.TicketAdapter;
import com.mw.admission.extra.MyApp;
import com.mw.admission.model.Ticket;

public class OrderDetailActivity extends MenuButtonActivity {

	boolean isHeaderThere = true;

	MyApp myApp;
	Intent previousIntent;

	LayoutInflater inflater;

	int position;

	TextView nameTV, orderNumberTV, numberOfTicketsTV;

	LinearLayout parentLL;
	LinearLayout childLL;

	ListView barcodeLV;
	List<Ticket> ticketsOfOrderList;
	TicketAdapter adapter;

	private void initThings() {
		previousIntent = getIntent();
		position = previousIntent.getIntExtra("position", 0);
		myApp = (MyApp) getApplicationContext();

		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		ticketsOfOrderList = new ArrayList<List<Ticket>>(myApp.getOrderMap()
				.values()).get(position);

		adapter = new TicketAdapter(this, ticketsOfOrderList, 2);
	}

	private void findThings() {
		super.findThings(isHeaderThere);

		nameTV = (TextView) findViewById(R.id.name_TV);
		orderNumberTV = (TextView) findViewById(R.id.orderNumber_TV);
		numberOfTicketsTV = (TextView) findViewById(R.id.numberOfTickets_TV);

		barcodeLV = (ListView) findViewById(R.id.barcode_LV);

		parentLL = (LinearLayout) findViewById(R.id.parent_LL);
		childLL = (LinearLayout) findViewById(R.id.child_LL);
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

		barcodeLV.setAdapter(adapter);
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
		parentLL.removeView(childLL);
		adapter = new TicketAdapter(this, ticketsOfOrderList, 3);
		barcodeLV.setAdapter(adapter);
		barcodeLV.setDivider(new ColorDrawable(this.getResources().getColor(R.color.grey_background)));
//		barcodeLV.setDividerHeight(1);
	}

	public void onAdmitAll(View view) {

	}

}
