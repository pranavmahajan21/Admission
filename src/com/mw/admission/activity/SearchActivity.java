package com.mw.admission.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.mw.admission.adapter.TicketAdapter;
import com.mw.admission.extra.MyApp;
import com.mw.admission.model.Ticket;

public class SearchActivity extends MenuButtonActivity {

	boolean isHeaderThere = false;

	MyApp myApp;

	ListView ticketLV;
	List<Ticket> ticketList;
	TicketAdapter adapter;

	EditText searchET;

	private void initThings() {
		myApp = (MyApp) getApplicationContext();
		ticketList = myApp.getTicketList();

		if (ticketList != null && ticketList.size() > 0) {
			adapter = new TicketAdapter(this, ticketList, 1);
		}
		nextIntent = new Intent(this, TicketDetailActivity.class);
	}

	public void findThings(boolean isHeaderThere) {
		super.findThings(isHeaderThere);

		ticketLV = (ListView) findViewById(R.id.ticket_LV);
		searchET = (EditText) findViewById(R.id.search_ET);
	}

	public void initView() {
		super.initView();
		getLabelActionBarTV().setText("Search");

		if (adapter != null) {
			ticketLV.setAdapter(adapter);
		} else {
			// no tickets
		}
	}

	private void myOwnOnTextChangeListeners() {
		searchET.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// On text change call filter function of Adapter
				SearchActivity.this.adapter.filter(cs.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search_ticket);

		findThings(isHeaderThere);
		initThings();
		initView();

		myOwnOnTextChangeListeners();

		ticketLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				nextIntent.putExtra("position", position);
				startActivityForResult(nextIntent, 1321);
			}

		});

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// If we don't do this step then app crashes while coming back from
		// TicketDetailActivity (after "admit this guest"). TODO Do R&D on that
		// error
		adapter.notifyDataSetChanged();
	}
}
