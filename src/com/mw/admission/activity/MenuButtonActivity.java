package com.mw.admission.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

public class MenuButtonActivity extends Activity {

//	TextView labelActionBarTV;
//	TextView labelHeaderTV;
	
	Intent nextIntent;

//	private void findThings() {
//		labelActionBarTV = (TextView) findViewById(R.id.label_action_TV);
//		labelHeaderTV = (TextView) findViewById(R.id.label_TV);
//	}
	
	public void onMenu(View view) {
		nextIntent = new Intent(this, MenuActivity.class);
		nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(nextIntent);
	}

}
