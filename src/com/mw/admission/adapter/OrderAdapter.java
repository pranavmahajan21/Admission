package com.mw.admission.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mw.admission.activity.R;
import com.mw.admission.model.Ticket;

public class OrderAdapter extends BaseAdapter {

	Context context;

	LayoutInflater inflater;

	List<List<Ticket>> aa;

	public OrderAdapter(Context context, Map<String, List<Ticket>> orderMap) {
		super();
		this.context = context;
		aa = new ArrayList<List<Ticket>>(orderMap.values());
	}

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

			convertView = inflater.inflate(R.layout.element_ticket2, parent,
					false);

			viewHolder.nameTV = (TextView) convertView
					.findViewById(R.id.label_name_TV);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		List<Ticket> tempTicketList = aa.get(position);
		viewHolder.nameTV.setText(tempTicketList.get(0).getNameOfGuest());

		return convertView;
	}

	@Override
	public int getCount() {
		return aa.size();
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