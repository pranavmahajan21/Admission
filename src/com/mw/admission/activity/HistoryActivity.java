package com.mw.admission.activity;

import java.util.List;

import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.mw.admission.adapter.ScanAdapter;
import com.mw.admission.extra.MyApp;
import com.mw.admission.model.Scan;

public class HistoryActivity extends MenuButtonActivity {

	boolean isHeaderThere = false;

	ListView scanLV;
	List<Scan> scanList;
	ScanAdapter adapter;

	private void initThings() {

		myApp = (MyApp) getApplicationContext();
		scanList = myApp.getScanList();

		if (scanList != null && scanList.size() > 0) {
			adapter = new ScanAdapter(this, scanList);
		}
	}

	public void findThings() {
		super.findThings(isHeaderThere);

		scanLV = (ListView) findViewById(R.id.scan_LV);
	}

	public void initView() {
		super.initView();
		getLabelActionBarTV().setText("History");

		scanLV.setAdapter(adapter);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_history);

		findThings();
		initThings();
		initView();

	}

}
