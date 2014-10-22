package com.mw.admission.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class ValidTicketActivity extends MenuButtonActivity {

	boolean isHeaderThere = false;
	
	private void initThings() {
	}

	public void findThings(boolean isHeaderThere) {
		super.findThings(isHeaderThere);

		((TextView) findViewById(R.id.label_TV)).setTypeface(myApp
				.getTypefaceBoldSans());
		((TextView) findViewById(R.id.label2_TV)).setTypeface(myApp
				.getTypefaceBoldSans());
	}

	public void initView() {
		super.initView();
		getLabelActionBarTV().setText("Scan Online");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	
		setContentView(R.layout.activity_valid);
		
		findThings(isHeaderThere);
		initThings();
		initView();
	}

	public void onContinue(View view) {
		finish();
	}

}
