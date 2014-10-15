package com.mw.admission.activity;

import com.mw.admission.extra.MyApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MenuButtonActivity extends Activity {

	MyApp myApp;

	Intent nextIntent;

	private TextView labelActionBarTV;
	private TextView labelHeaderTV;
	private TextView selectedEventTV;

	private ImageButton settingsIB;

	private void setTypeface() {
		labelActionBarTV.setTypeface(myApp.getTypefaceBoldSans());
		selectedEventTV.setTypeface(myApp.getTypefaceBoldSans());
	}

	public void findThings(boolean isHeaderThere) {
		labelActionBarTV = (TextView) findViewById(R.id.label_action_TV);
		if (isHeaderThere) {
			labelHeaderTV = (TextView) findViewById(R.id.label_TV);
			labelHeaderTV.setTypeface(myApp.getTypefaceBoldSans());
		}
		selectedEventTV = (TextView) findViewById(R.id.selectedEvent_TV);
		settingsIB = (ImageButton) findViewById(R.id.settings_IB);

		setTypeface();
	}

	public void initView() {
		selectedEventTV.setText(myApp.getSelectedEvent().getName());
		settingsIB.setVisibility(View.GONE);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApp = (MyApp) getApplicationContext();
	}

	public void onMenu(View view) {
		nextIntent = new Intent(this, MenuActivity.class);
		nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(nextIntent);
	}

	public void onBack(View view) {
		finish();
	}

	// getters - setters
	public TextView getLabelActionBarTV() {
		return labelActionBarTV;
	}

	public void setLabelActionBarTV(TextView labelActionBarTV) {
		this.labelActionBarTV = labelActionBarTV;
	}

	public TextView getLabelHeaderTV() {
		return labelHeaderTV;
	}

	public void setLabelHeaderTV(TextView labelHeaderTV) {
		this.labelHeaderTV = labelHeaderTV;
	}

	public TextView getSelectedEventTV() {
		return selectedEventTV;
	}

	public void setSelectedEventTV(TextView selectedEventTV) {
		this.selectedEventTV = selectedEventTV;
	}

	public ImageButton getSettingsIB() {
		return settingsIB;
	}

	public void setSettingsIB(ImageButton settingsIB) {
		this.settingsIB = settingsIB;
	}
}
