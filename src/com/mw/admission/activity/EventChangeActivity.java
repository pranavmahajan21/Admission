package com.mw.admission.activity;

import java.util.List;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.mw.admission.adapter.EventAdapter;
import com.mw.admission.extra.MyApp;
import com.mw.admission.model.Event;

public class EventChangeActivity extends MenuButtonActivity {

	MyApp myApp;

	TextView labelTV;
	TextView selectedEventTV;

	ListView eventLV;
	EventAdapter adapter;
	List<Event> eventList;

	private void initThings() {
		myApp = (MyApp) getApplicationContext();
		eventList = myApp.getEventList();

		if (eventList != null && eventList.size() > 0) {
			adapter = new EventAdapter(this, eventList);
		}
	}

	private void findThings() {
		labelTV = (TextView) findViewById(R.id.label_TV);
		selectedEventTV = (TextView) findViewById(R.id.selectedEvent_TV);

		eventLV = (ListView) findViewById(R.id.event_LV);
	}

	private void initView() {
		labelTV.setText("Change Event");
		selectedEventTV.setText("TODO");

		if (adapter != null) {
			eventLV.setAdapter(adapter);
		} else {
			// no events
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list);

		initThings();
		findThings();
		initView();

	}
}