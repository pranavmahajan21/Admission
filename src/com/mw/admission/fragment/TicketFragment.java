package com.mw.admission.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.mw.admission.activity.R;
import com.mw.admission.activity.TicketDetailActivity;
import com.mw.admission.adapter.TicketAdapter;
import com.mw.admission.extra.MyApp;
import com.mw.admission.model.Ticket;

public class TicketFragment extends Fragment {

	MyApp myApp;
	Intent nextIntent;

	ListView ticketLV;
	List<Ticket> ticketList;
	TicketAdapter adapter;

	private void initThings() {
		myApp = (MyApp) getActivity().getApplicationContext();

		ticketList = myApp.getTicketList();

		if (ticketList != null && ticketList.size() > 0) {
			adapter = new TicketAdapter(getActivity(), ticketList, 0);
		}

		nextIntent = new Intent(getActivity(), TicketDetailActivity.class);
	}

	private void findThings() {
		ticketLV = (ListView) getActivity().findViewById(R.id.ticket_LV);
	}

	private void initView() {

		if (adapter != null) {
			ticketLV.setAdapter(adapter);
		} else {
			// no tickets
		}
	}

	// newInstance constructor for creating fragment with arguments
	public static TicketFragment newInstance(int page, String title) {
		TicketFragment fragmentFirst = new TicketFragment();

		// Bundle args = new Bundle();
		// args.putInt("someInt", page);
		// args.putString("someTitle", title);
		// fragmentFirst.setArguments(args);

		return fragmentFirst;
	}

	// Store instance variables based on arguments passed
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// page = getArguments().getInt("someInt", 0);
		// title = getArguments().getString("someTitle");
	}

	// Inflate the view for the fragment based on layout XML
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_ticket_list, container,
				false);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		findThings();
		initThings();
		initView();

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
