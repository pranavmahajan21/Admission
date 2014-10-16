package com.mw.admission.activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class AboutActivity extends MenuButtonActivity {

	boolean isHeaderThere = true;


	public void findThings(boolean isHeaderThere) {
		super.findThings(isHeaderThere);

		((TextView) findViewById(R.id.label_TV)).setTypeface(myApp
				.getTypefaceRegularSans());
		((TextView) findViewById(R.id.label2_TV)).setTypeface(myApp
				.getTypefaceRegularSans());
		((TextView) findViewById(R.id.label3_TV)).setTypeface(myApp
				.getTypefaceRegularSans());
		((TextView) findViewById(R.id.label4_TV)).setTypeface(myApp
				.getTypefaceRegularSans());
		((TextView) findViewById(R.id.label5_TV)).setTypeface(myApp
				.getTypefaceRegularSans());
	}

	public void initView() {
		super.initView();
		getLabelActionBarTV().setText("About");
		getLabelHeaderTV().setText("About");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about);

//		initThings();
		findThings(isHeaderThere);
		initView();

	}

}
