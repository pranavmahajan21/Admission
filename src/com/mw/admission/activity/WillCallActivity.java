package com.mw.admission.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.mw.admission.adapter.TicketAdapter;
import com.mw.admission.extra.MyApp;
import com.mw.admission.model.Ticket;

public class WillCallActivity extends MenuButtonActivity {

	MyApp myApp;
	
	Intent nextIntent;
	
	TextView labelActionBarTV;
	TextView labelHeaderTV;
	TextView selectedEventTV;

	ListView ticketLV;
	List<Ticket> ticketList;
	TicketAdapter adapter;
	
	private void initThings() {
		myApp = (MyApp) getApplicationContext();
		ticketList = myApp.getTicketList();
		
		if (ticketList != null && ticketList.size() > 0) {
			adapter = new TicketAdapter(this, ticketList, true);
		}
		
		nextIntent = new Intent(this, TicketDetailActivity.class);
	}

	public void findThings() {
		labelActionBarTV = (TextView) findViewById(R.id.label_action_TV);
		labelHeaderTV = (TextView) findViewById(R.id.label_TV);
		selectedEventTV = (TextView) findViewById(R.id.selectedEvent_TV);
		
		ticketLV= (ListView) findViewById(R.id.ticket_LV);
	}

	public void initView() {
		labelActionBarTV.setText("Will Call");
		labelHeaderTV.setText("Will Call");
		selectedEventTV.setText("TODO");
		
		if (adapter != null) {
			ticketLV.setAdapter(adapter);
		} else {
			// no tickets
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_will_call);

		findThings();
		initThings();
		initView();

		ticketLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				nextIntent.putExtra("position", position);
				startActivity(nextIntent);
			}
		
		});
		
	}

}