package com.mw.admission.activity;

import android.os.Bundle;
import android.view.Window;

public class AboutActivity extends MenuButtonActivity {

	boolean isHeaderThere = true;
	
	private void initThings() {
	}

	public void findThings(boolean isHeaderThere) {
		super.findThings(isHeaderThere);
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

		findThings(isHeaderThere);
		initThings();
		initView();

	}

}
