package com.mw.admission.activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class ContactActivity extends MenuButtonActivity {

	boolean isHeaderThere = true;

	private void initThings() {
	}

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
		getLabelActionBarTV().setText("Contact Us");
		getLabelHeaderTV().setText("Contact Us");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_contact);

		findThings(isHeaderThere);
		initThings();
		initView();

	}

}
