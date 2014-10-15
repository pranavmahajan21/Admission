package com.mw.admission.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mw.admission.activity.R;
import com.mw.admission.extra.MyApp;
import com.mw.admission.model.Event;
import com.mw.admission.model.MenuItem;

public class MenuAdapter extends BaseAdapter {

	Context context;
	List<MenuItem> menuList;

	MyApp myApp;

	LayoutInflater inflater;

	public MenuAdapter(Context context, List<MenuItem> menuList) {
		super();
		this.context = context;
		this.menuList = menuList;
		myApp = (MyApp) context.getApplicationContext();
	}

	// public void swapData(List<ParseObject> alertList) {
	// this.eventPO = alertList;
	// }

	static class ViewHolder {
		protected TextView labelTV;
		protected ImageView arrowIV;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			viewHolder = new ViewHolder();
			convertView = inflater
					.inflate(R.layout.element_menu, parent, false);

			viewHolder.labelTV = (TextView) convertView
					.findViewById(R.id.label_TV);
			viewHolder.arrowIV = (ImageView) convertView
					.findViewById(R.id.arrow_IV);

			 viewHolder.labelTV.setTypeface(myApp.getTypefaceRegularSans());

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		MenuItem tempMenuItem = menuList.get(position);
		viewHolder.labelTV.setText(tempMenuItem.getText());

		if (tempMenuItem.isArrowVisible()) {
			viewHolder.arrowIV.setVisibility(View.VISIBLE);
		} else {
			viewHolder.arrowIV.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	@Override
	public int getCount() {
		return menuList.size();
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