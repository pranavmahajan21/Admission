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
import com.mw.admission.model.Scan;

public class ScanAdapter extends BaseAdapter {

	Context context;
	List<Scan> scanList;

	MyApp myApp;

	LayoutInflater inflater;

	public ScanAdapter(Context context, List<Scan> scanList) {
		super();
		this.context = context;
		this.scanList = scanList;
		myApp = (MyApp) context.getApplicationContext();
	}

	static class ViewHolder {
		protected TextView scanTime_TV;
		protected TextView barcode_TV;
		protected TextView status_TV;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			viewHolder = new ViewHolder();
			convertView = inflater
					.inflate(R.layout.element_scan, parent, false);

			viewHolder.scanTime_TV = (TextView) convertView
					.findViewById(R.id.scanTime_TV);
			viewHolder.barcode_TV = (TextView) convertView
					.findViewById(R.id.barcode_TV);
			viewHolder.status_TV = (TextView) convertView
					.findViewById(R.id.status_TV);

			// viewHolder.scanTime_TV.setTypeface(myApp.getTypefaceRegularSans());
			// viewHolder.scanTime_TV.setTypeface(myApp.getTypefaceRegularSans());
			// viewHolder.scanTime_TV.setTypeface(myApp.getTypefaceRegularSans());

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Scan tempScan = scanList.get(position);
		
		viewHolder.scanTime_TV.setText(myApp.formatDateToString3(tempScan.getScanDate()));
		viewHolder.barcode_TV.setText(tempScan.getBarcode());
		if (tempScan.getResult() == 0) {
			viewHolder.status_TV.setText("VALID");
		} else if (tempScan.getResult() == 1) {
			viewHolder.status_TV.setText("DUPLICATE");
		} else {
			viewHolder.status_TV.setText("INVALID");
		}

		return convertView;
	}

	@Override
	public int getCount() {
		return scanList.size();
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