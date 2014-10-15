package com.mw.admission.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class InvalidTicketActivity extends MenuActivity {

	boolean isHeaderThere = true;
	
	private void initThings() {
	}

	public void findThings(boolean isHeaderThere) {
		super.findThings(isHeaderThere);

//		((TextView) findViewById(R.id.label_TV)).setTypeface(myApp
//				.getTypefaceRegularSans());
//		((TextView) findViewById(R.id.label2_TV)).setTypeface(myApp
//				.getTypefaceRegularSans());
//		((TextView) findViewById(R.id.label3_TV)).setTypeface(myApp
//				.getTypefaceRegularSans());
	}

	public void initView() {
		super.initView();
		getLabelActionBarTV().setText("Scan Online");
//		getLabelHeaderTV().setText("About");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
	
		setContentView(R.layout.activity_invalid);
		
		findThings(isHeaderThere);
		initThings();
		initView();
	}

	public void onContinue(View view) {
		finish();
	}

}
