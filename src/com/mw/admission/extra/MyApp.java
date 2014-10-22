package com.mw.admission.extra;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mw.admission.model.Event;
import com.mw.admission.model.MenuItem;
import com.mw.admission.model.Scan;
import com.mw.admission.model.Ticket;
import com.mw.admission.model.User;

public class MyApp extends Application {

	public static final int CAMERA_REQUEST_CODE = 0;

	public static final int EVENT_CHANGE = 10;

	// http://staging.missiontix.com/ 404

	// http://beta.missiontix.com/ 500

	// http://private-db490-missiontix.apiary-proxy.com/ 500

	public static final String URL = "http://beta.missiontix.com/";

	public static final String LOGIN = "api/users/authenticate";
	public static final String EVENT = "api/users/events/";
	public static final String TICKET = "api/admissions/events/";

	// volley stuff
	public static final String TAG = MyApp.class.getSimpleName();

	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;// not needed

	private static MyApp myAppInstance;

	// To check for internet
	ConnectionDetector cd;
	// don't initialize in this class
	User loginUser;

	// don't initialize in this class
	List<Event> eventList;
	// don't initialize in this class
	Event selectedEvent;

	// don't initialize in this class
	List<Ticket> ticketList;
	Map<String, List<Ticket>> orderMap;

	List<Scan> scanList;

	List<MenuItem> menuItemList;

	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	Gson gson;

	Typeface typefaceRegularSans;
	Typeface typefaceBoldSans;

	RequestQueue queue;

	private void initThings() {
		myAppInstance = this;
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		editor = sharedPreferences.edit();

		GsonBuilder builder = new GsonBuilder();
		gson = builder.create();

		menuItemList = new ArrayList<MenuItem>();
		menuItemList.add(new MenuItem("Scanner", false));
		menuItemList.add(new MenuItem("Will Call", false));
		menuItemList.add(new MenuItem("Event Report", false));
		menuItemList.add(new MenuItem("Search", false));
		menuItemList.add(new MenuItem("History", false));
		menuItemList.add(new MenuItem("Synchronize Data", false));
		menuItemList.add(new MenuItem("Change Event", true));
		menuItemList.add(new MenuItem("Help/Contact Us", true));
		menuItemList.add(new MenuItem("About", true));
		menuItemList.add(new MenuItem("Logout", false));

		scanList = new ArrayList<Scan>();
		// orderMap = new HashMap<String, List<Ticket>>();

		typefaceRegularSans = Typeface.createFromAsset(getAssets(),
				"fonts/SourceSansPro-Regular.ttf");
		typefaceBoldSans = Typeface.createFromAsset(getAssets(),
				"fonts/SourceSansPro-Semibold.ttf");

		queue = Volley.newRequestQueue(this);

		cd = new ConnectionDetector(this);
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
					Event tempEvent = getEventList().get(
							sharedPreferences.getInt("position_selected_event",
									0));

					// discuss if the next step is required??
					tempEvent.setScanStartDate(new Date());

					setSelectedEvent(tempEvent);
				}
				if (sharedPreferences.contains("ticketList")) {
					Type listType = (Type) new TypeToken<ArrayList<Ticket>>() {
					}.getType();
					List<Ticket> ticketList = (List<Ticket>) gson.fromJson(
							sharedPreferences.getString("ticketList", null),
							listType);
					setTicketList(ticketList);
				}
				if (sharedPreferences.contains("scanList")) {
					Type listType = (Type) new TypeToken<ArrayList<Scan>>() {
					}.getType();
					List<Scan> scanList = (List<Scan>) gson.fromJson(
							sharedPreferences.getString("scanList", null),
							listType);
					setScanList(scanList);
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

	public int isTicketValid(String barcode, boolean isPositionKnown,
			int position) {

		Scan scan = new Scan();
		scan.setBarcode(barcode);
		scan.setScanDate(new Date());

		// if(isInPatternMode)
		// {
		// isTicketValidPattern(barcode);
		// return;
		// }

		// int tempTicketPosition;
		Ticket tempTicket = null;

		if (isPositionKnown) {
			// if(we are coming from will call page)
			tempTicket = getTicketList().get(position);
		} else {
			// we are coming from scanner page
			for (int i = 0; i < getTicketList().size(); i++) {
				if (barcode.equals(getTicketList().get(i).getBarcode())) {
					tempTicket = getTicketList().get(i);
				}
			}
		}

		if (tempTicket != null) {
			if (!isPositionKnown) {
				// if we don't know the position i.e. we are coming from scanner
				tempTicket.setScanTimeAndScannerIDAndCheckedIn(new Date(),
						getLoginUser().getUsername(), true);
			}

			if (barcode.equals(tempTicket.getBarcode())) {
				// VALID BARCODE

				if (!tempTicket.isCheckedIn()) {
					// NOT CHECKED IN i.e. *** ideal case ***
					scan.setResult(0);
					if (cd.isConnectingToInternet()) {
						checkInTicket(scan);
					} else {
						Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT);
					}
				} else {
					// ALREADY CHECKED IN i.e. DUPLICATE TICKET
					scan.setResult(1);
				}
			}
		} else {
			// we can reach here only through scanner
			// INVALID BARCODE
			scan.setResult(2);
		}
		getScanList().add(scan);

		editor.putString("scanList", gson.toJson(getScanList()));
		editor.commit();

		return scan.getResult();
	}

	private void checkInTicket(Scan scan) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("barcode", scan.getBarcode());
			jsonObject.put("check_in_order", false);
			jsonObject
					.put("timestamp", formatDateToString4(scan.getScanDate()));

			String url = MyApp.URL + MyApp.TICKET + getSelectedEvent().getId()
					+ "/" + getLoginUser().getToken();
			System.out.println("ticket URL : " + url);

			JsonObjectRequest jsObjRequest = new JsonObjectRequest(Method.PUT,
					url, jsonObject, new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							System.out.println(">>>>Response => "
									+ response.toString());
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							System.out.println("ERROR  : " + error.getMessage());
							error.printStackTrace();
							if (error instanceof NetworkError) {
								System.out.println("NetworkError");
							}
							if (error instanceof NoConnectionError) {
								System.out
										.println("NoConnectionError you are now offline.");
							}
							if (error instanceof ServerError) {
								System.out.println("ServerError");
							}
						}
					});

			RetryPolicy policy = new DefaultRetryPolicy(30000,
					DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
			jsObjRequest.setRetryPolicy(policy);
			queue.add(jsObjRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JSONObject createOrderJSON(List<Ticket> ticketList) {
		Date date = new Date();
		JSONArray aa = new JSONArray();
		JSONObject mainJsonObject = new JSONObject();
		try {
			for (int i = 0; i < ticketList.size(); i++) {
				aa.put(new JSONObject().put("barcode",
						ticketList.get(i).getBarcode()).put("timestamp",
						formatDateToString4(date)));
			}
			mainJsonObject.put("barcodes", aa);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mainJsonObject;
	}

	JSONObject returnJsonObject;

	private JSONObject checkInOrder(JSONObject jsonObject) {
		try {

			String url = MyApp.URL + MyApp.TICKET + getSelectedEvent().getId()
					+ "/" + getLoginUser().getToken();
			System.out.println("ticket URL : " + url);
			JsonObjectRequest jsObjRequest = new JsonObjectRequest(Method.POST,
					url, jsonObject, new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							System.out.println(">>>>Response => "
									+ response.toString());
							returnJsonObject = response;
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {

							System.out.println("ERROR  : " + error.getMessage());
							error.printStackTrace();
							JSONObject jsonObject = null;
							JSONArray aa = null;
							try {
								jsonObject = new JSONObject(error.getMessage());
								returnJsonObject = jsonObject;
								// if (jsonObject.has("rejected_barcodes")) {
								// aa = jsonObject
								// .getJSONArray("rejected_barcodes");
								// }
								return;
							} catch (JSONException e) {
								e.printStackTrace();
							}

							// String x;
							// for (int i = 0; i < aa.length(); i++) {
							//
							// }

							if (error instanceof NetworkError) {
								System.out.println("NetworkError");
							} else if (error instanceof NoConnectionError) {
								System.out
										.println("NoConnectionError you are now offline.");
							} else if (error instanceof ServerError) {
								System.out.println("ServerError");
							}
						}
					}) {

				// @Override
				// protected Response<JSONObject> parseNetworkResponse(
				// NetworkResponse response) {
				// System.out.println(response.headers.get("Set-Cookie"));
				// // phpsessid = response.headers.get("Set-Cookie");
				// return super.parseNetworkResponse(response);
				// }

				@Override
				protected VolleyError parseNetworkError(VolleyError volleyError) {
					if (volleyError.networkResponse != null
							&& volleyError.networkResponse.data != null) {
						VolleyError error = new VolleyError(new String(
								volleyError.networkResponse.data));
						volleyError = error;
					}
					// System.out.println("volleyError" + volleyError);
					return volleyError;
				}

			};

			RetryPolicy policy = new DefaultRetryPolicy(30000,
					DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
			jsObjRequest.setRetryPolicy(policy);
			queue.add(jsObjRequest);
			return returnJsonObject;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// return returnJsonObject;
	}

	private JSONObject checkInOrder2(JSONObject jsonObject) {

		String url = MyApp.URL + MyApp.TICKET + getSelectedEvent().getId()
				+ "/" + getLoginUser().getToken();
		RequestFuture<JSONObject> future = RequestFuture.newFuture();
		JsonObjectRequest request = new JsonObjectRequest(Method.POST, url,
				jsonObject, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println(">>>>Response => "
								+ response.toString());
						returnJsonObject = response;
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

						System.out.println("ERROR  : " + error.getMessage());
						error.printStackTrace();
						JSONObject jsonObject = null;
						JSONArray aa = null;
						try {
							jsonObject = new JSONObject(error.getMessage());
							returnJsonObject = jsonObject;
							// if (jsonObject.has("rejected_barcodes")) {
							// aa = jsonObject
							// .getJSONArray("rejected_barcodes");
							// }
						} catch (JSONException e) {
							e.printStackTrace();
						}

						// String x;
						// for (int i = 0; i < aa.length(); i++) {
						//
						// }

						if (error instanceof NetworkError) {
							System.out.println("NetworkError");
						} else if (error instanceof NoConnectionError) {
							System.out
									.println("NoConnectionError you are now offline.");
						} else if (error instanceof ServerError) {
							System.out.println("ServerError");
						}
					}
				}) {

			// @Override
			// protected Response<JSONObject> parseNetworkResponse(
			// NetworkResponse response) {
			// System.out.println(response.headers.get("Set-Cookie"));
			// // phpsessid = response.headers.get("Set-Cookie");
			// return super.parseNetworkResponse(response);
			// }

			@Override
			protected VolleyError parseNetworkError(VolleyError volleyError) {
				if (volleyError.networkResponse != null
						&& volleyError.networkResponse.data != null) {
					VolleyError error = new VolleyError(new String(
							volleyError.networkResponse.data));
					volleyError = error;
				}
				// System.out.println("volleyError" + volleyError);
				return volleyError;
			}

		};
		queue.add(request);

		// future.onResponse(null);

		JSONObject response = null;
		try {
			response = future.get(); // this will block
		} catch (InterruptedException e) {
			// exception handling
			e.printStackTrace();
		} catch (ExecutionException e) {
			// exception handling
			e.printStackTrace();
		}
		return response;
	}

	public String trimMessage(String json, String key) {
		String trimmedString = null;

		try {
			JSONObject obj = new JSONObject(json);
			trimmedString = obj.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		return trimmedString;
	}

	// Somewhere that has access to a context
	public void displayMessage(String toastString) {
		Toast.makeText(this, toastString, Toast.LENGTH_LONG).show();
	}

	@SuppressLint("SimpleDateFormat")
	public String formatDateToString(Date date) {

		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd");

		String dateStr = formatter.format(date);
		System.out.println(">><<><><><" + dateStr);
		return dateStr;

	}

	@SuppressLint("SimpleDateFormat")
	public String formatDateToString2(Date date) {

		SimpleDateFormat formatter = new SimpleDateFormat("MMM-dd-yyyy/HH:mm");

		String dateStr = formatter.format(date);
		System.out.println(">><<><><><" + dateStr);
		return dateStr;

	}

	@SuppressLint("SimpleDateFormat")
	public String formatDateToString3(Date date) {

		SimpleDateFormat formatter = new SimpleDateFormat(
				"MMM-dd-yyyy @ HH:mm:ss aa");

		String dateStr = formatter.format(date);
		System.out.println(">><<><><><" + dateStr);
		return dateStr;

	}

	@SuppressLint("SimpleDateFormat")
	public String formatDateToString4(Date date) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String dateStr = formatter.format(date);
		System.out.println(">><<><><><" + dateStr);
		return dateStr;

	}

	public Date formatStringToDate(String dateString) {
		// 2014-10-16 20:00:00
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date date = null;
		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// System.out.println(">><<><><><" + dateStr);
		return date;

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

		if (ticketList == null) {
			return;
		}

		// Put in preferences
		editor.putString("ticketList", gson.toJson(ticketList));
		editor.commit();

		// create a map for orders
		orderMap = new HashMap<String, List<Ticket>>();
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

	public int convertJOResponseToTicket(JSONObject responseJsonObject) {
		List<Ticket> ticketList = null;
		Type listType = (Type) new TypeToken<ArrayList<Ticket>>() {
		}.getType();
		try {
			ticketList = (List<Ticket>) gson.fromJson(responseJsonObject
					.getJSONArray("tickets").toString(), listType);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		for(int i=0;i<ticketList.size();i++)
		{
			if(ticketList.get(i).getNameOfGuest() == null)
			{ticketList.get(i).setNameOfGuest("Walk-Up");}
		}
		
		setTicketList(ticketList);
		return ticketList.size();
	}
}
