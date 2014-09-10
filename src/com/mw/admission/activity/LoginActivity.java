package com.mw.admission.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.mw.admission.adapter.EventAdapter;
import com.mw.admission.model.Event;

public class LoginActivity extends Activity {

	// Intent nextIntent;
	LayoutInflater inflater;

	LinearLayout parentViewLL;
	LinearLayout childViewLoginLL;
	View childViewEventLL;
	View childViewOptionLL;

	ListView eventLV;
	EventAdapter adapter;
	List<Event> aa;

	private void findLoginThings() {
		parentViewLL = (LinearLayout) findViewById(R.id.parent_view_LL);
		childViewLoginLL = (LinearLayout) findViewById(R.id.child_view_LL);
	}

	private void findEventThings() {
		eventLV = (ListView) findViewById(R.id.event_LV);

		adapter = new EventAdapter(this, aa);
	}

	private void initThings() {
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Toast.makeText(this, "dsadsa", Toast.LENGTH_SHORT).show();
		setContentView(R.layout.activity_login);

		findLoginThings();
		initThings();
	}

	public void onForgotPassword(View view) {

	}

	public void onLogin(View view) {
		// JsonObjectRequest dnkj = new JsonObjectRequest(url, jsonRequest,
		// listener, errorListener);
		// JsonObjectRequest e = new JsonObjectRequest(method, url, jsonRequest,
		// listener, errorListener);
		aa = new ArrayList<Event>();
		aa.add(new Event("Event1", new Date()));
		aa.add(new Event("Event2", new Date()));
		aa.add(new Event("Event3", new Date()));
		aa.add(new Event("Event4", new Date()));
		aa.add(new Event("Event5", new Date()));

		childViewEventLL = inflater.inflate(R.layout.child_list_event, null);
		parentViewLL.removeAllViews();
		parentViewLL.addView(childViewEventLL);
		findEventThings();
		eventLV.setAdapter(adapter);

		eventLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// fetch tickets
				
				childViewOptionLL = inflater.inflate(R.layout.child_option, null);
				parentViewLL.removeAllViews();
				parentViewLL.addView(childViewOptionLL);
			}

		});

	}

	public void onScanner(View view) {
		System.out.println("11");
	}

	public void onWillCall(View view) {
		System.out.println("22");
	}

	public void onReport(View view) {
		System.out.println("33");
	}

	public void onChangeEvent(View view) {
		System.out.println("44");
		parentViewLL.removeAllViews();
		parentViewLL.addView(childViewEventLL);
	}
}