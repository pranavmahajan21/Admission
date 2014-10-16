package com.mw.admission.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mw.admission.extra.MyApp;
import com.mw.admission.fragment.OrderFragment;
import com.mw.admission.fragment.TicketFragment;

public class WillCallActivity extends FragmentActivity {

	MyApp myApp;

	Intent nextIntent;

	FragmentPagerAdapter adapterViewPager;

	TextView labelActionBarTV;
	TextView selectedEventTV;

	private ImageButton settingsIB;

	private void initThings() {
		myApp = (MyApp) getApplicationContext();
	}

	public void findThings() {
		labelActionBarTV = (TextView) findViewById(R.id.label_action_TV);
		selectedEventTV = (TextView) findViewById(R.id.selectedEvent_TV);

		settingsIB = (ImageButton) findViewById(R.id.settings_IB);

		labelActionBarTV.setTypeface(myApp.getTypefaceBoldSans());
		selectedEventTV.setTypeface(myApp.getTypefaceBoldSans());
	}

	public void initView() {
		labelActionBarTV.setText("Will Call");
		selectedEventTV.setText(myApp.getSelectedEvent().getName());
		settingsIB.setVisibility(View.GONE);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_will_call);

		initThings();
		findThings();
		initView();

		ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
		adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
		vpPager.setAdapter(adapterViewPager);

	}

	public static class MyPagerAdapter extends FragmentPagerAdapter {
		private static int NUM_ITEMS = 2;

		public MyPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		// Returns total number of pages
		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

		// Returns the fragment to display for that page
		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				// Pranav : I have not removed the args just to see the scope of
				// fragments
				return TicketFragment.newInstance(0, "Ticket");
			case 1:
				return OrderFragment.newInstance();
			default:
				return null;
			}
		}

		// Returns the page title for the top indicator
		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return "Ticket";
			case 1:
				return "Order";
			default:
				return "";
			}
		}

	}

	public void onMenu(View view) {
		nextIntent = new Intent(this, MenuActivity.class);
		nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(nextIntent);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		onMenu(null);
	}

}