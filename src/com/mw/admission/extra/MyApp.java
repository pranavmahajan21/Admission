package com.mw.admission.extra;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.mw.admission.model.Event;
import com.mw.admission.model.MenuItem;
import com.mw.admission.model.User;

public class MyApp extends Application {

//	http://staging.missiontix.com	404

	//	http://private-db490-missiontix.apiary-proxy.com	500
	
	// http://beta.missiontix.com		500
	
	public static final String URL = "http://staging.missiontix.com/"; 
	public static final String LOGIN = "api/users/authenticate";
	public static final String EVENT = "http://private-db490-missiontix.apiary-proxy.com/api/users/events/";
	public static final String TICKET = "http://private-db490-missiontix.apiary-proxy.com/api/admissions/events/";

	// volley stuff
	public static final String TAG = MyApp.class.getSimpleName();
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;// not needed

	private static MyApp mInstance;

	User loginUser;

	List<MenuItem> menuItemList;

	SharedPreferences sharedPreferences;

	Event selectedEvent;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		menuItemList = new ArrayList<MenuItem>();
		menuItemList.add(new MenuItem("Scanner", false));
		menuItemList.add(new MenuItem("Will Call", false));
		menuItemList.add(new MenuItem("Event Report", false));
		menuItemList.add(new MenuItem("Ticket Lookup", false));
		menuItemList.add(new MenuItem("History", false));
		menuItemList.add(new MenuItem("Synchronize Data", false));
		menuItemList.add(new MenuItem("Change Event", true));
		menuItemList.add(new MenuItem("Help/Contact Us", true));
		menuItemList.add(new MenuItem("About", true));
		menuItemList.add(new MenuItem("Logout", false));
	}

	public static synchronized MyApp getInstance() {
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	// we dont need to fetch images in this app
	// public ImageLoader getImageLoader() {
	// getRequestQueue();
	// if (mImageLoader == null) {
	// mImageLoader = new ImageLoader(this.mRequestQueue,
	// new LruBitmapCache());
	// }
	// return this.mImageLoader;
	// }

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}

	public Event getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(Event selectedEvent) {
		this.selectedEvent = selectedEvent;
	}

	public List<MenuItem> getMenuItemList() {
		return menuItemList;
	}

	public void setMenuItemList(List<MenuItem> menuItemList) {
		this.menuItemList = menuItemList;
	}

}
