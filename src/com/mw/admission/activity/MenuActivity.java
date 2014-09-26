package com.mw.admission.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.mw.admission.adapter.MenuAdapter;
import com.mw.admission.extra.MyApp;

public class MenuActivity extends Activity {

	MyApp myApp;

	ListView menuLV;
	MenuAdapter adapter;

	Intent nextIntent;
	private void initThings() {
		myApp = (MyApp) getApplicationContext();
		adapter = new MenuAdapter(this, myApp.getMenuItemList());
	}

	private void findThings() {
		menuLV = (ListView) findViewById(R.id.menu_LV);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		initThings();
		findThings();

		menuLV.setAdapter(adapter);

		menuLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					break;
				case 7:
					break;
				case 8:
					break;
				case 9:
					nextIntent = new Intent(MenuActivity.this, LoginActivity.class);
					nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(nextIntent);
					break;
				default:
					break;
				}
			}

		});
	}
}
