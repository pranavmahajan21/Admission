package com.mw.admission.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mw.admission.activity.R;
import com.mw.admission.extra.MyApp;
import com.mw.admission.model.Event;

public class EventAdapter extends BaseAdapter {

	Context context;
	List<Event> eventPO;

	MyApp myApp;
	Event selectedEvent;

	LayoutInflater inflater;

	public EventAdapter(Context context, List<Event> eventList) {
		super();
		this.context = context;
		this.eventPO = eventList;
		myApp = (MyApp) context.getApplicationContext();
		selectedEvent = myApp.getSelectedEvent();
	}

	// public void swapData(List<ParseObject> alertList) {
	// this.eventPO = alertList;
	// }

	static class ViewHolder {
		protected TextView nameTV;
		protected TextView dateTV;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("getview");
		final ViewHolder viewHolder;
		if (convertView == null) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.element_event, parent,
					false);

			viewHolder.nameTV = (TextView) convertView
					.findViewById(R.id.name_TV);
			viewHolder.dateTV = (TextView) convertView
					.findViewById(R.id.date_TV);

			// viewHolder.nameTV.setTypeface(myApp.getTypefaceBold());

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Event tempEvent = eventPO.get(position);
		viewHolder.nameTV.setText(tempEvent.getName());
		if (selectedEvent != null
				&& tempEvent.getId().equals(selectedEvent.getId())) {
			viewHolder.nameTV.setTextAppearance(context, R.style.textRedBold);
		} else {
			viewHolder.nameTV.setTextAppearance(context, R.style.textGreyNormal);
		}

		 viewHolder.dateTV.setText(myApp.formatDate(tempEvent.getDate()));

		return convertView;
	}

	@Override
	public int getCount() {
		return eventPO.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}