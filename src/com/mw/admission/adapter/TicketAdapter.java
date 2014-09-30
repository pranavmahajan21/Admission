package com.mw.admission.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
	List<Ticket> tempTicketList = new ArrayList<Ticket>();
	boolean whichViewToUse;
	
	MyApp myApp;

	LayoutInflater inflater;

	public TicketAdapter(Context context, List<Ticket> ticketList, boolean whichViewToUse) {
		super();
		this.context = context;
		this.ticketList = ticketList;
		tempTicketList = new ArrayList<Ticket>();
		tempTicketList.addAll(ticketList);
		this.whichViewToUse = whichViewToUse;
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
			
			if(whichViewToUse){
			convertView = inflater.inflate(R.layout.element_ticket, parent,
					false);
			}else{convertView = inflater.inflate(R.layout.element_ticket2, parent,
					false);}
			
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

	public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        ticketList.clear();
        if (charText.length() == 0) {
        	ticketList.addAll(tempTicketList);
        } else {
            for (Ticket tempTicket : tempTicketList) {
                if (tempTicket.getNameOfGuest().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                	ticketList.add(tempTicket);
                }
            }
        }
        notifyDataSetChanged();
    }
}