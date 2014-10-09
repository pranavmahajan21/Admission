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

	private static final int VIEW0 = 0;
	private static final int VIEW1 = 1;
	private static final int VIEW2 = 2;
	private static final int VIEW3 = 3;
	private static final int VIEW_MAX_COUNT = 4;

	Context context;
	List<Ticket> ticketList;
	List<Ticket> tempTicketList = new ArrayList<Ticket>();

	// boolean whichViewToUse;

	int whichViewToUse;

	MyApp myApp;

	LayoutInflater inflater;

	public TicketAdapter(Context context, List<Ticket> ticketList,
			int whichViewToUse) {
		super();
		this.context = context;
		this.ticketList = ticketList;
		tempTicketList = new ArrayList<Ticket>();
		tempTicketList.addAll(ticketList);
		this.whichViewToUse = whichViewToUse;
		myApp = (MyApp) context.getApplicationContext();
	}

	@Override
	public int getItemViewType(int position) {
		return whichViewToUse;
	}

	@Override
	public int getViewTypeCount() {
		return VIEW_MAX_COUNT;
	}

	static class ViewHolder {
		protected TextView nameTV;
		protected TextView barcodeTV;
		protected TextView statusTV;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		int x = getItemViewType(position);
		if (convertView == null) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			viewHolder = new ViewHolder();

//			if (x == VIEW0) {
//				System.out.println("00000");
//				convertView = inflater.inflate(R.layout.element_ticket, parent,
//						false);
//				viewHolder.nameTV = (TextView) convertView
//						.findViewById(R.id.ticket_owner_TV);
//			} else if (x == VIEW1) {
//				System.out.println("1111111");
//				convertView = inflater.inflate(R.layout.element_ticket2,
//						parent, false);
//				viewHolder.nameTV = (TextView) convertView
//						.findViewById(R.id.ticket_owner_TV);
//			} else if (x == VIEW2) {
//				System.out.println("22222");
//				convertView = inflater.inflate(R.layout.element_ticket3,
//						parent, false);
//				viewHolder.barcodeTV = (TextView) convertView
//						.findViewById(R.id.barcode_TV);
//				viewHolder.statusTV = (TextView) convertView
//						.findViewById(R.id.status_TV);
//			}

			switch (x) {
			case VIEW0:
				convertView = inflater.inflate(R.layout.element_ticket, parent,
						false);
				viewHolder.nameTV = (TextView) convertView
						.findViewById(R.id.ticket_owner_TV);
				break;
			case VIEW1:
				convertView = inflater.inflate(R.layout.element_ticket2,
						parent, false);
				viewHolder.nameTV = (TextView) convertView
						.findViewById(R.id.ticket_owner_TV);
				break;
			case VIEW2:
				convertView = inflater.inflate(R.layout.element_ticket3,
						parent, false);
				viewHolder.barcodeTV = (TextView) convertView
						.findViewById(R.id.barcode_TV);
				viewHolder.statusTV = (TextView) convertView
						.findViewById(R.id.status_TV);
				break;
			case VIEW3:
				convertView = inflater.inflate(R.layout.element_ticket, parent,
						false);
				viewHolder.nameTV = (TextView) convertView
						.findViewById(R.id.ticket_owner_TV);
				break;

			default:
				break;
			}

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Ticket tempTicket = ticketList.get(position);

//		if (x == VIEW0) {
//			viewHolder.nameTV.setText(tempTicket.getNameOfGuest());
//		} else if (x == VIEW1) {
//			viewHolder.nameTV.setText(tempTicket.getNameOfGuest());
//		} else if (x == VIEW2) {
//			viewHolder.barcodeTV.setText(tempTicket.getBarcode());
//			if (tempTicket.isCheckedIn()) {
//				viewHolder.statusTV.setText("Admitted");
//			} else {
//				viewHolder.statusTV.setText("Open");
//			}
//		}

		switch (x) {
		case VIEW0:
			viewHolder.nameTV.setText(tempTicket.getNameOfGuest());
			break;
		case VIEW1:
			viewHolder.nameTV.setText(tempTicket.getNameOfGuest());
			break;
		case VIEW2:
			viewHolder.barcodeTV.setText(tempTicket.getBarcode());
			if (tempTicket.isCheckedIn()) {
				viewHolder.statusTV.setText("Admitted");
			} else {
				viewHolder.statusTV.setText("Open");
			}
			break;
		case VIEW3:
			viewHolder.nameTV.setText(tempTicket.getNameOfGuest());
			viewHolder.nameTV.setTextColor(context.getResources().getColor(
					R.color.grey_background));
			break;

		default:
			break;
		}

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
				if (tempTicket.getNameOfGuest()
						.toLowerCase(Locale.getDefault()).contains(charText)) {
					ticketList.add(tempTicket);
				}
			}
		}
		notifyDataSetChanged();
	}
}