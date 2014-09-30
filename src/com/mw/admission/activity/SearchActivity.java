package com.mw.admission.activity;

import java.util.List;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
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
			adapter = new TicketAdapter(this, ticketList, false);
		}

	}

	public void findThings(boolean isHeaderThere) {
		super.findThings(isHeaderThere);

		ticketLV = (ListView) findViewById(R.id.ticket_LV);
		searchET = (EditText) findViewById(R.id.search_ET);
	}

	public void initView() {
		super.initView();
		getLabelActionBarTV().setText("Search");
		// if (isHeaderThere) {
		// getLabelHeaderTV().setText("Search");
		// }

		if (adapter != null) {
			ticketLV.setAdapter(adapter);
		} else {
			// no tickets
		}
	}

	private void myOwnOnTextChangeListeners() {
		searchET.addTextChangedListener(new TextWatcher() {
		     
		    @Override
		    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
		        // On text change call filter function of Adapter
		        SearchActivity.this.adapter.filter(cs.toString());   
		    }
		     
		    @Override
		    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		            int arg3) {
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
	}


}
