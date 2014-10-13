package com.mw.admission.extra;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mw.admission.model.Event;
import com.mw.admission.model.MenuItem;
import com.mw.admission.model.Scan;
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

	private static MyApp myAppInstance;

	// don't initialize in this class
	User loginUser;

	// don't initialize in this class
	List<Event> eventList;
	// don't initialize in this class
	Event selectedEvent;

	// don't initialize in this class
	List<Ticket> ticketList;
	Map<String, List<Ticket>> orderMap;

	List<MenuItem> menuItemList;

	List<Scan> scanList;

	SharedPreferences sharedPreferences;

	Typeface typefaceRegularSans;
	Typeface typefaceBoldSans;

	private void initThings() {
		myAppInstance = this;
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

		scanList = new ArrayList<Scan>();
		orderMap = new HashMap<String, List<Ticket>>();

		typefaceRegularSans = Typeface.createFromAsset(getAssets(),
				"fonts/SourceSansPro-Regular.ttf");
		typefaceBoldSans = Typeface.createFromAsset(getAssets(),
				"fonts/SourceSansPro-Semibold.ttf");
	}

	private void fetchPreferences() {

		if (sharedPreferences.contains("isLoggedIn")
				&& sharedPreferences.getBoolean("isLoggedIn", false)) {
			Gson gson = new Gson();
			User user = gson.fromJson(
					sharedPreferences.getString("user", null), User.class);
			if (user != null) {
				setLoginUser(user);
				if (sharedPreferences.contains("eventList")) {
					Type listType = (Type) new TypeToken<ArrayList<Event>>() {
					}.getType();
					List<Event> eventList = (List<Event>) gson.fromJson(
							sharedPreferences.getString("eventList", null),
							listType);
					setEventList(eventList);
				}
				if (sharedPreferences.contains("position_selected_event")) {
					setSelectedEvent(getEventList().get(
							sharedPreferences.getInt("position_selected_event",
									0)));
				}
				if (sharedPreferences.contains("ticketList")) {
					Type listType = (Type) new TypeToken<ArrayList<Ticket>>() {
					}.getType();
					List<Ticket> ticketList = (List<Ticket>) gson.fromJson(
							sharedPreferences.getString("ticketList", null), listType);
					setTicketList(ticketList);
				}

			} else {
				// Something wrong with preferences
			}
		}

	}

	@Override
	public void onCreate() {
		super.onCreate();

		initThings();

		fetchPreferences();

	}

	public static synchronized MyApp getInstance() {
		return myAppInstance;
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

	public static void isTicketValidPattern(String barcode) {

	}

	public static void isTicketValid(String barcode, boolean isPositionKnown,
			int position) {

		// if(isInPatternMode)
		// {
		// isTicketValidPattern(barcode);
		// return;
		// }

		int tempTicketPosition;
		Ticket tempTicket = null;

		if (isPositionKnown) {
			// if(we are coming from will call page)
			tempTicket = myAppInstance.getTicketList().get(position);
		} else {
			// we are coming from scanner page
			for (int i = 0; i < myAppInstance.getTicketList().size(); i++) {
				if (barcode.equals(myAppInstance.getTicketList().get(i))) {
					tempTicket = myAppInstance.getTicketList().get(i);
				}
			}
		}

		if (tempTicket != null) {
			if (!isPositionKnown) {
				// if we don't know the position i.e. we are coming from scanner
				// page
				tempTicket.setScanTimeAndScannerIDAndCheckedIn(new Date(),
						myAppInstance.getLoginUser().getUsername(), true);
			}
			Scan scan = new Scan();
			scan.setBarcode(barcode);
			scan.setScanDate(new Date());
			if (barcode.equals(tempTicket.getBarcode())) {
				// VALID BARCODE

				if (!tempTicket.isCheckedIn()) {
					// NOT CHECKED IN i.e. *** ideal case ***
					scan.setResult(0);
					// if(inOnlineMode)
					{
						checkInTicket(barcode);
					}
				} else {
					// ALREADY CHECKED IN i.e. DUPLICATE TICKET
					scan.setResult(1);
				}
			} else {
				// INVALID BARCODE
				scan.setResult(2);
			}
			myAppInstance.getScanList().add(scan);
		} else {
			// bug (ticket should have been found)
			// maybe scanner read something wrong
		}
	}

	private static void checkInTicket(String barcode) {
		// TODO Auto-generated method stub

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
			System.out.println(i + "   " + (aa == null));
		}
	}

	public Map<String, List<Ticket>> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(Map<String, List<Ticket>> orderMap) {
		this.orderMap = orderMap;
	}

	public List<Scan> getScanList() {
		return scanList;
	}

	public void setScanList(List<Scan> scanList) {
		this.scanList = scanList;
	}

	public Typeface getTypefaceRegularSans() {
		return typefaceRegularSans;
	}

	public void setTypefaceRegularSans(Typeface typefaceRegularSans) {
		this.typefaceRegularSans = typefaceRegularSans;
	}

	public Typeface getTypefaceBoldSans() {
		return typefaceBoldSans;
	}

	public void setTypefaceBoldSans(Typeface typefaceBoldSans) {
		this.typefaceBoldSans = typefaceBoldSans;
	}

}
