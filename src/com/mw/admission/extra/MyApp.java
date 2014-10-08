package com.mw.admission.extra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.mw.admission.model.Ticket;
import com.mw.admission.model.User;

public class MyApp extends Application {

	public static final int CAMERA_REQUEST_CODE = 0;

	// http://staging.missiontix.com 404

	// http://private-db490-missiontix.apiary-proxy.com 500

	// http://beta.missiontix.com 500

	public static final String URL = "http://staging.missiontix.com/";

	public static final String LOGIN = "api/users/authenticate";
	// public static final String EVENT =
	// "http://private-db490-missiontix.apiary-proxy.com/api/users/events/";
	public static final String EVENT = "api/users/events/";
	public static final String TICKET = "api/admissions/events/";

	// volley stuff
	public static final String TAG = MyApp.class.getSimpleName();

	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;// not needed

	private static MyApp mInstance;

	User loginUser;

	List<Event> eventList;
	Event selectedEvent;

	List<Ticket> ticketList;
	Map<String, List<Ticket>> orderMap;

	List<MenuItem> menuItemList;

	SharedPreferences sharedPreferences;

	private void initThings() {
		mInstance = this;
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		menuItemList = new ArrayList<MenuItem>();
		orderMap = new HashMap<String, List<Ticket>>();
	}

	@Override
	public void onCreate() {
		super.onCreate();

		initThings();

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

	public List<Event> getEventList() {
		return eventList;
	}

	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}

	public List<Ticket> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<Ticket> ticketList) {
		this.ticketList = ticketList;

		// create a map for orders
		for (int i = 0; i < this.ticketList.size(); i++) {
			Ticket tempTicket = this.ticketList.get(i);
			if (orderMap.containsKey(tempTicket.getOrderId())) {
				// add all but first tickets of an order
				List<Ticket> aa = orderMap.get(tempTicket.getOrderId());
				aa.add(tempTicket);
				orderMap.put(tempTicket.getOrderId(), aa);
			} else {
				// add the first ticket of an order
				List<Ticket> aa = new ArrayList<Ticket>();
				aa.add(tempTicket);
				orderMap.put(tempTicket.getOrderId(), aa);
			}
		}
		
		System.out.println("orderMap size:" + orderMap.size());
		for (int i = 0; i < orderMap.size(); i++) {
			List<Ticket> aa = orderMap.get(i);
			System.out.println(i + "   " + (aa==null));
		}
	}

	public Map<String, List<Ticket>> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(Map<String, List<Ticket>> orderMap) {
		this.orderMap = orderMap;
	}

}
