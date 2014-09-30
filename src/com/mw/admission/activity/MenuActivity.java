package com.mw.admission.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.mw.admission.adapter.MenuAdapter;
import com.mw.admission.extra.MyApp;

public class MenuActivity extends Activity {

	MyApp myApp;

	Intent nextIntent;

	ListView menuLV;
	MenuAdapter adapter;

	TextView labelActionBarTV;
	TextView selectedEventTV;
	TextView timeTV;

	private void initThings() {
		myApp = (MyApp) getApplicationContext();
		adapter = new MenuAdapter(this, myApp.getMenuItemList());
	}

	private void findThings() {
		labelActionBarTV = (TextView) findViewById(R.id.label_action_TV);
		menuLV = (ListView) findViewById(R.id.menu_LV);
		selectedEventTV = (TextView) findViewById(R.id.event_selected_name_TV);
		timeTV = (TextView) findViewById(R.id.time_TV);
	}

	public void initView() {
		labelActionBarTV.setText("Menu");
		selectedEventTV.setText(myApp.getSelectedEvent().getName());
		timeTV.setText("(12 hrs 37 mins)");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menu);
		initThings();
		findThings();
		initView();

		menuLV.setAdapter(adapter);

		menuLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					nextIntent = new Intent("com.google.zxing.client.android.SCAN");
					nextIntent.putExtra("SCAN_MODE", "QR_CODE_MODE");
	                startActivityForResult(nextIntent, 0);
					
//	                nextIntent = new Intent(MenuActivity.this,
//							ScannerActivity.class);
//					startActivity(nextIntent);
					break;
				case 1:
					nextIntent = new Intent(MenuActivity.this,
							WillCallActivity.class);
					startActivity(nextIntent);
					break;
				case 2:
					nextIntent = new Intent(MenuActivity.this,
							EventReportActivity.class);
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
					startActivity(nextIntent);
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

					startActivity(nextIntent);
					break;
				default:
					break;
				}
			}

		});
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
           if (resultCode == RESULT_OK) {
               
              String contents = intent.getStringExtra("SCAN_RESULT");
              String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
           
              // Handle successful scan
                                        
           } else if (resultCode == RESULT_CANCELED) {
              // Handle cancel
              Log.i("App","Scan unsuccessful");
           }
      }
   }
	
	public void onMenu(View view) {
	}
}
