package com.mw.admission.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mw.admission.adapter.EventAdapter;
import com.mw.admission.extra.CreateDialog;
import com.mw.admission.extra.MyApp;
import com.mw.admission.model.Event;
import com.mw.admission.model.Ticket;
import com.mw.admission.model.User;

public class LoginActivity extends Activity {

	MyApp myApp;

	Gson gson;
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;

	Intent nextIntent;
	LayoutInflater inflater;

	LinearLayout parentViewLL;
	LinearLayout childViewLoginLL;
	View childViewEventLL;
	View childViewOptionLL;

	EditText usernameTV;
	EditText passwordTV;

	TextView loginPageHeaderTV;
	TextView selectedEventTV;

	ListView eventLV;
	EventAdapter adapter;
	List<Event> eventList;
	List<Ticket> ticketList;

	AlertDialog alertDialog;

	CreateDialog createDialog;
	ProgressDialog progressDialog;

	RequestQueue queue;

	private void initThings() {
		myApp = (MyApp) getApplicationContext();

		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		childViewOptionLL = inflater.inflate(R.layout.child_option, null);

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this
				.getApplicationContext());
		editor = sharedPreferences.edit();

		GsonBuilder builder = new GsonBuilder();
		gson = builder.create();

		createDialog = new CreateDialog(this);
		progressDialog = createDialog.createProgressDialog("LoggingIn",
				"Please Wait", true, null);

		nextIntent = new Intent(this, MenuActivity.class);
		nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);

		queue = Volley.newRequestQueue(this);

	}

	private void setTypeface() {
		loginPageHeaderTV.setTypeface(myApp.getTypefaceBoldSans());
		usernameTV.setTypeface(myApp.getTypefaceBoldSans());
		passwordTV.setTypeface(myApp.getTypefaceBoldSans());
	}

	private void findLoginThings() {

		parentViewLL = (LinearLayout) findViewById(R.id.parent_view_LL);
		childViewLoginLL = (LinearLayout) findViewById(R.id.child_view_LL);

		usernameTV = (EditText) findViewById(R.id.user_ET);
		passwordTV = (EditText) findViewById(R.id.password_ET);
		loginPageHeaderTV = (TextView) findViewById(R.id.header_TV);

		((TextView) findViewById(R.id.label_forgot_TV)).setTypeface(myApp
				.getTypefaceRegularSans());
		((TextView) findViewById(R.id.login_button_TV)).setTypeface(myApp
				.getTypefaceRegularSans());
		((TextView) findViewById(R.id.forgot_message_TV)).setTypeface(myApp
				.getTypefaceRegularSans());
		((Button) findViewById(R.id.ok_B)).setTypeface(myApp
				.getTypefaceRegularSans());
		setTypeface();
	}

	private void findEventThings() {
		eventLV = (ListView) findViewById(R.id.event_LV);
		((TextView) findViewById(R.id.label_TV)).setTypeface(myApp
				.getTypefaceBoldSans());
	}

	private void staticNonsense() {
		// usernameTV.setText("vats_8x10");
		// passwordTV.setText("m1ss1on");

		// usernameTV.setText("scanning_user42160");
		// passwordTV.setText("password");

		usernameTV.setText("scanning_user54730");
		passwordTV.setText("vb12345");

//		 usernameTV.setText("scanning_user54731");
//		 passwordTV.setText("vb12345");

//		 usernameTV.setText("scanning_user54738");
//		 passwordTV.setText("vb12345");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Toast.makeText(this, "dsadsa", Toast.LENGTH_SHORT).show();
		System.out.println("dfsred");
		setContentView(R.layout.activity_login);

		initThings();
		findLoginThings();

		staticNonsense();

		// When we have fetched data from preferences
		if (myApp.getLoginUser() != null) {
			if (myApp.getEventList() != null) {
				eventList = myApp.getEventList();
				if (myApp.getSelectedEvent() != null) {
					if (myApp.getTicketList() != null) {
						loadMenuView();
					} else {
						getTickets();
					}
				} else {
					loadEventListView();
				}
			} else {
				getEvents();
			}
		}
	}

	private void loadEventListView() {
		adapter = new EventAdapter(LoginActivity.this, myApp.getEventList());

		childViewEventLL = inflater.inflate(R.layout.child_list_event, null);
		parentViewLL.removeAllViews();
		parentViewLL.addView(childViewEventLL);
		findEventThings();
		eventLV.setAdapter(adapter);

		eventLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Event tempEvent = eventList.get(position);
				tempEvent.setScanStartDate(new Date());
				myApp.setSelectedEvent(tempEvent);

				editor.putInt("position_selected_event", position);
				editor.commit();

				// TICKETS TICKETS TICKETS TICKETS
				getTickets();

			}

		});

	}

	private void loadMenuView() {

		parentViewLL.removeAllViews();
		parentViewLL.addView(childViewOptionLL);

		findThingsForOptions();

		selectedEventTV.setText(myApp.getSelectedEvent().getName());
	}

	public void onForgotPassword(View view) {
		findViewById(R.id.forgotPassword).setVisibility(View.VISIBLE);
		childViewLoginLL.setVisibility(View.GONE);
	}

	public void onOk(View view) {
		childViewLoginLL.setVisibility(View.VISIBLE);
		findViewById(R.id.forgotPassword).setVisibility(View.GONE);
	}

	public void onOk2(View view) {
	}

	public void onLogin(View view) {
		System.out.println(">>>>>> login");
		progressDialog.show();
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("login", usernameTV.getText().toString());
			jsonObject.put("password", passwordTV.getText().toString());

			// createDialog = new CreateDialog(LoginActivity.this);
			// progressDialog = createDialog.createProgressDialog("Validation",
			// "Please wait while we validate your login.", true, null);
			// progressDialog.show();import com.google.gson.reflect.TypeToken;
			String url = MyApp.URL + MyApp.LOGIN;
			System.out.println("login URL : " + url);
			JsonObjectRequest jsObjRequest = new JsonObjectRequest(Method.POST,
					url, jsonObject, new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							System.out.println(">>>>Response => "
									+ response.toString());
							progressDialog.dismiss();
							User user = null;
							try {
								user = gson.fromJson(
										response.getJSONObject("user")
												.toString(), User.class);
								user.setToken(response.getString("token"));
							} catch (JsonSyntaxException e) {
								e.printStackTrace();
							} catch (JSONException e) {
								e.printStackTrace();
							}
							myApp.setLoginUser(user);

							editor.putBoolean("isLoggedIn", true);
							editor.putString("user",
									gson.toJson(myApp.getLoginUser()));
							editor.commit();

							System.out.println("login user" + user);

							// EVENTS EVENTS EVENTS EVENTS
							getEvents();
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {

							

							System.out.println("ERROR" + error.getMessage());
							// error.networkResponse
							progressDialog.hide();
							error.printStackTrace();
							loginPageHeaderTV.setTextColor(Color
									.parseColor("#00AEA4"));
							if (error instanceof NetworkError) {
								loginPageHeaderTV.setText("NetworkError");
							}
							if (error instanceof NoConnectionError) {
								loginPageHeaderTV.setText("NoConnectionError");
							}
							if (error instanceof ServerError) {
								loginPageHeaderTV.setText("ServerError");
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

	public void onScanner(View view) {
		nextIntent.putExtra("option", 0);
		startActivity(nextIntent);
	}

	public void onWillCall(View view) {
		nextIntent.putExtra("option", 1);
		startActivity(nextIntent);
	}

	public void onReport(View view) {
		nextIntent.putExtra("option", 2);
		startActivity(nextIntent);
	}

	public void onChangeEvent(View view) {

		editor.remove("position_selected_event");
		editor.commit();
		myApp.setSelectedEvent(null);

		parentViewLL.removeAllViews();

		if (childViewEventLL != null) {
			parentViewLL.addView(childViewEventLL);
		} else {
			// when directly event list page opens coz login details were
			// already there in preferences

			loadEventListView();

			// childViewEventLL = inflater.inflate(R.layout.child_list_event,
			// null);
			// parentViewLL.addView(childViewEventLL);

			// findEventThings();
			// eventLV.setAdapter(adapter);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	public void getEvents() {
		progressDialog = createDialog.createProgressDialog("Fetching Events",
				"Please Wait", true, null);
		progressDialog.show();
		String eventsUrl = MyApp.URL + MyApp.EVENT
				+ myApp.getLoginUser().getToken();
		System.out.println(eventsUrl);
		JsonArrayRequest req = new JsonArrayRequest(eventsUrl,
				new Response.Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray responseJsonArray) {
						progressDialog.dismiss();
						eventList = new ArrayList<Event>();
						// Gson gson = new Gson();
						System.out.println(responseJsonArray.toString());

						Type listType = (Type) new TypeToken<ArrayList<Event>>() {
						}.getType();
						eventList = (List<Event>) gson.fromJson(
								responseJsonArray.toString(), listType);

						// TODO: remove this for loop & find proper way to do
						// this using GSON
						for (int i = 0; i < eventList.size(); i++) {
							try {
								eventList
										.get(i)
										.setDate(
												myApp.formatStringToDate(responseJsonArray
														.getJSONObject(i)
														.getJSONObject("start")
														.getString("date")));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

						myApp.setEventList(eventList);

						editor.putString("eventList",
								gson.toJson(myApp.getEventList()));
						editor.commit();

						System.out.println(">>>>> events size:"
								+ eventList.size());

						loadEventListView();

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						arg0.getStackTrace();
						progressDialog.dismiss();
					}
				});
		queue.add(req);
	} // end of getEvents

	private void findThingsForOptions() {
		selectedEventTV = (TextView) findViewById(R.id.event_selected_name_TV);
		selectedEventTV.setTypeface(myApp.getTypefaceBoldSans());

		((TextView) findViewById(R.id.label_TV)).setTypeface(myApp
				.getTypefaceBoldSans());
	}

	private void getTickets() {
		String ticketsUrl = MyApp.URL + MyApp.TICKET
				+ myApp.getSelectedEvent().getId() + "/"
				+ myApp.getLoginUser().getToken();
		System.out.println("tickets url : " + ticketsUrl);
		progressDialog = createDialog.createProgressDialog("Fetching Tickets",
				"Please Wait", true, null);
		progressDialog.show();

		JsonObjectRequest ticketsRequest = new JsonObjectRequest(Method.GET,
				ticketsUrl, null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject responseJsonObject) {
						progressDialog.dismiss();
						System.out.println("Ticket Response => "
								+ responseJsonObject.toString());

						int size= myApp.convertJOResponseToTicket(responseJsonObject);
						
//						if (i < 1) {
//							alert.show();
//						}
						
						Toast.makeText(LoginActivity.this,
								"tickets size : " + size,
								Toast.LENGTH_SHORT).show();

						loadMenuView();

					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						progressDialog.dismiss();
						System.out.println("ERROR" + error.getMessage());
						error.printStackTrace();
						if (error instanceof NetworkError) {
						}
						if (error instanceof NoConnectionError) {
						}
						if (error instanceof ServerError) {
						}
					}
				});
		RetryPolicy policy = new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		ticketsRequest.setRetryPolicy(policy);
		queue.add(ticketsRequest);
	}

}