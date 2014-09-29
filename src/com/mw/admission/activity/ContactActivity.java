package com.mw.admission.activity;

import android.os.Bundle;
import android.view.Window;

public class ContactActivity extends MenuButtonActivity {

	private void initThings() {
	}

	public void findThings() {
		super.findThings();
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

		findThings();
		initThings();
		initView();

	}

}
