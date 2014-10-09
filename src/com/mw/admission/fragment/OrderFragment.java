package com.mw.admission.fragment;

import java.util.List;
import java.util.Map;

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

import com.mw.admission.activity.OrderDetailActivity;
import com.mw.admission.activity.R;
import com.mw.admission.adapter.OrderAdapter;
import com.mw.admission.extra.MyApp;
import com.mw.admission.model.Ticket;

public class OrderFragment extends Fragment {

	MyApp myApp;
	Intent nextIntent;

	ListView orderLV;
	Map<String, List<Ticket>> orderMap;
	OrderAdapter adapter;

	private void initThings() {
		myApp = (MyApp) getActivity().getApplicationContext();

		orderMap = myApp.getOrderMap();

		if (orderMap != null && orderMap.size() > 0) {
			adapter = new OrderAdapter(getActivity(), orderMap);
		}

		nextIntent = new Intent(getActivity(), OrderDetailActivity.class);
	}

	private void findThings() {
		orderLV = (ListView) getActivity().findViewById(R.id.order_LV);
	}

	private void initView() {

		if (adapter != null) {
			orderLV.setAdapter(adapter);
		} else {
			// no orders
		}
	}

	public static OrderFragment newInstance() {
		OrderFragment fragmentFirst = new OrderFragment();
		return fragmentFirst;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_order_list, container,
				false);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		findThings();
		initThings();
		initView();

		orderLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				nextIntent.putExtra("position", position);
				startActivity(nextIntent);
			}

		});
	}

}
