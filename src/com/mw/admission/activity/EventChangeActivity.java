package com.mw.admission.activity;

import java.util.List;

import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.mw.admission.adapter.EventAdapter;
import com.mw.admission.extra.MyApp;
import com.mw.admission.model.Event;

public class EventChangeActivity extends MenuButtonActivity {

	boolean isHeaderThere = true;
	
	MyApp myApp;

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

	public void findThings(boolean isHeaderThere) {
		super.findThings(isHeaderThere);
		eventLV = (ListView) findViewById(R.id.event_LV);
	}

	public void initView() {
		super.initView();
		getLabelActionBarTV().setText("Change Event");
		getLabelHeaderTV().setText("Change Event");

		if (adapter != null) {
			eventLV.setAdapter(adapter);
		} else {
			// no events
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_event_list);

		initThings();
		findThings(isHeaderThere);
		initView();

	}
}