package com.mw.admission.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mw.admission.adapter.MenuAdapter;
import com.mw.admission.extra.MyApp;

public class MenuActivity extends MenuButtonActivity {

	boolean isHeaderThere = false;

	MyApp myApp;

	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;

	Intent previousIntent;
	Intent nextIntent;

	ListView menuLV;
	MenuAdapter adapter;

	TextView timeTV;

	private void initThings() {
		myApp = (MyApp) getApplicationContext();

		previousIntent = getIntent();

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this
				.getApplicationContext());
		editor = sharedPreferences.edit();

		adapter = new MenuAdapter(this, myApp.getMenuItemList());
	}

	public void findThings(boolean isHeaderThere) {

		super.findThings(isHeaderThere);
		menuLV = (ListView) findViewById(R.id.menu_LV);
		timeTV = (TextView) findViewById(R.id.time_TV);
	}

	public void initView() {
		super.initView();
		getLabelActionBarTV().setText("Menu");
		timeTV.setText("(12 hrs 37 mins)");
		timeTV.setTypeface(myApp.getTypefaceBoldSans());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menu);
		initThings();
		findThings(isHeaderThere);
		initView();

		menuLV.setAdapter(adapter);

		menuLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					nextIntent = new Intent(
							"com.google.zxing.client.android.SCAN");
					nextIntent.putExtra("SCAN_MODE", "QR_CODE_MODE");
					startActivityForResult(nextIntent,
							MyApp.CAMERA_REQUEST_CODE);

					// Intent intent = new Intent(
					// "com.google.zxing.client.android.SCAN");
					// intent.putExtra("SCAN_MODE",
					// "QR_CODE_MODE,PRODUCT_MODE");
					// startActivityForResult(intent, 0);

					// nextIntent = new Intent(MenuActivity.this,
					// ScannerActivity.class);
					// startActivity(nextIntent);
					break;
				case 1:
					nextIntent = new Intent(MenuActivity.this,
							WillCallActivity.class);
					startActivity(nextIntent);
					break;
				case 2:
					nextIntent = new Intent(MenuActivity.this,
							ReportActivity.class);
					startActivity(nextIntent);
					break;
				case 3:
					nextIntent = new Intent(MenuActivity.this,
							SearchActivity.class);
					startActivity(nextIntent);
					break;
				case 4:
					nextIntent = new Intent(MenuActivity.this,
							HistoryActivity.class);
					startActivity(nextIntent);
					break;
				case 5:
					nextIntent = new Intent(MenuActivity.this,
							LoginActivity.class);
					startActivity(nextIntent);
					break;
				case 6:
					nextIntent = new Intent(MenuActivity.this,
							EventChangeActivity.class);
					startActivityForResult(nextIntent, MyApp.EVENT_CHANGE);
					break;
				case 7:
					nextIntent = new Intent(MenuActivity.this,
							ContactActivity.class);
					startActivity(nextIntent);
					break;
				case 8:
					nextIntent = new Intent(MenuActivity.this,
							AboutActivity.class);
					startActivity(nextIntent);
					break;
				case 9:
					nextIntent = new Intent(MenuActivity.this,
							LoginActivity.class);
					nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TASK);

					// TODO : clear preferences
					editor.clear();
					editor.commit();

					myApp.setLoginUser(null);
					myApp.setEventList(null);
					myApp.setSelectedEvent(null);
					myApp.setTicketList(null);

					startActivity(nextIntent);
					break;
				default:
					break;
				}
			}

		});

		if (previousIntent.hasExtra("option")) {
			int x = previousIntent.getIntExtra("option", 0);

			menuLV.performItemClick(menuLV.getAdapter().getView(x, null, null),
					x, menuLV.getAdapter().getItemId(x));
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == MyApp.CAMERA_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {

				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				
				Toast.makeText(this, contents, Toast.LENGTH_SHORT).show();
				
				int x = myApp.isTicketValid(contents, false, -1);
				
				if(x==1 || x==2)
				{
					nextIntent = new Intent(MenuActivity.this,
							InvalidTicketActivity.class);
					startActivity(nextIntent);
				}else{
					
				}
				
				Log.i("App", "Scan successful");

				// Handle successful scan

			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
				Log.i("App", "Scan unsuccessful");
			}
		}else if(requestCode == MyApp.EVENT_CHANGE)
		{
			getSelectedEventTV().setText(
					myApp.getSelectedEvent().getName());
		}
		
	}

	public void onMenu(View view) {
	}
}
