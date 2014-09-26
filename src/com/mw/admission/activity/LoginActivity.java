package com.mw.admission.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
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
import com.mw.admission.model.User;

public class LoginActivity extends Activity {

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

	AlertDialog alertDialog;
	CreateDialog createDialog;
	ProgressDialog progressDialog;

	RequestQueue queue;

	Gson gson;
	MyApp myApp;

	private void findLoginThings() {

		parentViewLL = (LinearLayout) findViewById(R.id.parent_view_LL);
		childViewLoginLL = (LinearLayout) findViewById(R.id.child_view_LL);
		usernameTV = (EditText) findViewById(R.id.user_ET);
		passwordTV = (EditText) findViewById(R.id.password_ET);
		loginPageHeaderTV = (TextView) findViewById(R.id.header_TV);
	}

	private void findEventThings() {
		eventLV = (ListView) findViewById(R.id.event_LV);
	}

	private void initThings() {
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		myApp = (MyApp) getApplicationContext();

		childViewOptionLL = inflater.inflate(R.layout.child_option, null);

		GsonBuilder builder = new GsonBuilder();
		gson = builder.create();

		queue = Volley.newRequestQueue(this);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Toast.makeText(this, "dsadsa", Toast.LENGTH_SHORT).show();
		System.out.println("dfsred");
		setContentView(R.layout.activity_login);

		findLoginThings();
		initThings();
		staticNonsense();
	}

	private void staticNonsense() {
		// usernameTV.setText("vats_8x10");
		// passwordTV.setText("m1ss1on");

		usernameTV.setText("scanning_user42160");
		passwordTV.setText("password");
	}

	public void onForgotPassword(View view) {
		findViewById(R.id.forgotPassword).setVisibility(View.VISIBLE);
		childViewLoginLL.setVisibility(View.GONE);
	}

	public void onOk(View view) {
		childViewLoginLL.setVisibility(View.VISIBLE);
		findViewById(R.id.forgotPassword).setVisibility(View.GONE);

	}

	public void onLogin(View view) {
		System.out.println(">>>>>> login");
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

							User user = null;
							try {
								user = gson.fromJson(
										response.getJSONObject("user")
												.toString(), User.class);
								user.setToken(response.getString("token"));
								myApp.setLoginUser(user);

								System.out.println("login user" + user);
							} catch (JsonSyntaxException e) {
								e.printStackTrace();
							} catch (JSONException e) {
								e.printStackTrace();
							}
							getEvents();
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							System.out.println("ERROR" + error.getMessage());
							error.printStackTrace();
							loginPageHeaderTV.setTextColor(Color
									.parseColor("#00AEA4"));
							if (error instanceof NetworkError) {
								loginPageHeaderTV
										.setText("NetworkError Email address provided is not registered.");
							}
							if (error instanceof NoConnectionError) {
								loginPageHeaderTV
										.setText("NoConnectionError you are now offline.");
							}
							if (error instanceof ServerError) {
								loginPageHeaderTV
										.setText("ServerError  Email address provided is not registered.");
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
		System.out.println("11");
	}

	public void onWillCall(View view) {
		System.out.println("22");
		nextIntent = new Intent(this, MenuActivity.class);
		nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(nextIntent);
	}

	public void onReport(View view) {
		System.out.println("33");
	}

	public void onChangeEvent(View view) {
		System.out.println("44");
		parentViewLL.removeAllViews();
		parentViewLL.addView(childViewEventLL);
	}

	@Override
	protected void onStop() {
		super.onStop();
		// myApp.saveSharedPreferences();
	}

	public void getEvents() {
		String eventsUrl = MyApp.EVENT + myApp.getLoginUser().getToken();
		System.out.println(eventsUrl);
		JsonArrayRequest req = new JsonArrayRequest(eventsUrl,
				new Response.Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray arg0) {
						// progressDialog.dismiss();
						eventList = new ArrayList<Event>();
						Gson gson = new Gson();
						Type listType = (Type) new TypeToken<ArrayList<Event>>() {
						}.getType();
						eventList = (List<Event>) gson.fromJson(
								arg0.toString(), listType);
						System.out.println(">>>>> events size:"
								+ eventList.size());
						adapter = new EventAdapter(LoginActivity.this,
								eventList);

						childViewEventLL = inflater.inflate(
								R.layout.child_list_event, null);
						parentViewLL.removeAllViews();
						parentViewLL.addView(childViewEventLL);
						findEventThings();
						eventLV.setAdapter(adapter);

						eventLV.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								myApp.setSelectedEvent(eventList.get(position));

								// Load Tickets for event
								getTickets();

							}

						});
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						arg0.getStackTrace();
					}
				});
		queue.add(req);
	} // end of getEvents

	private void findThingsForOptions() {
		selectedEventTV = (TextView) findViewById(R.id.event_selected_name_TV);
	}

	private void getTickets() {
		String ticketsUrl = MyApp.TICKET + myApp.getSelectedEvent().getId()
				+ "/" + myApp.getLoginUser().getToken();
		System.out.println(ticketsUrl);

		JsonObjectRequest ticketsRequest = new JsonObjectRequest(Method.GET,
				ticketsUrl, null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("Ticket Response => "
								+ response.toString());

						parentViewLL.removeAllViews();
						parentViewLL.addView(childViewOptionLL);

						findThingsForOptions();

						selectedEventTV.setText(myApp.getSelectedEvent()
								.getName());
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
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