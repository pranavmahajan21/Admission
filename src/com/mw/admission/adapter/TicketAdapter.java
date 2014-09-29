package com.mw.admission.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mw.admission.activity.R;
import com.mw.admission.extra.MyApp;
import com.mw.admission.model.Ticket;

public class TicketAdapter extends BaseAdapter {

	Context context;
	List<Ticket> ticketList;

	MyApp myApp;

	LayoutInflater inflater;

	public TicketAdapter(Context context, List<Ticket> ticketList) {
		super();
		this.context = context;
		this.ticketList = ticketList;
		myApp = (MyApp) context.getApplicationContext();
	}

	// public void swapData(List<ParseObject> alertList) {
	// this.eventPO = alertList;
	// }

	static class ViewHolder {
		protected TextView nameTV;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.element_ticket, parent,
					false);

			viewHolder.nameTV = (TextView) convertView
					.findViewById(R.id.ticket_owner_TV);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Ticket tempTicket = ticketList.get(position);
		viewHolder.nameTV.setText(tempTicket.getNameOfGuest());

		return convertView;
	}

	@Override
	public int getCount() {
		return ticketList.size();
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