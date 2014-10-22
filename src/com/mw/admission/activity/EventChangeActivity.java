package com.mw.admission.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
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

public class EventChangeActivity extends MenuButtonActivity {

	boolean isHeaderThere = true;

	MyApp myApp;

	Gson gson;
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;

	ListView eventLV;
	EventAdapter adapter;
	List<Event> eventList;

	List<Ticket> ticketList;

	CreateDialog createDialog;
	ProgressDialog progressDialog;
	AlertDialog alert;

	RequestQueue queue;

	private void initThings() {
		myApp = (MyApp) getApplicationContext();

		createDialog = new CreateDialog(this);
		progressDialog = createDialog.createProgressDialog("Fetching Tickets",
				"Please Wait", true, null);

		AlertDialog.Builder builder = createDialog
				.createAlertDialog(
						"Ticket Data not Found",
						"No local ticket data available. App will scan in Pattern Matching Mode. You can also try to sync with Mission Tix or operate in Live mode.",
						false);

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});

		alert = builder.create();
		// alertDialog.set

		eventList = myApp.getEventList();

		if (eventList != null && eventList.size() > 0) {
			adapter = new EventAdapter(this, eventList);
		}

		GsonBuilder builder2 = new GsonBuilder();
		gson = builder2.create();

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this
				.getApplicationContext());
		editor = sharedPreferences.edit();

		queue = Volley.newRequestQueue(this);
	}

	public void findThings(boolean isHeaderThere) {
		super.findThings(isHeaderThere);
		eventLV = (ListView) findViewById(R.id.event_LV);
	}

	public void initView() {
		super.initView();
		getLabelActionBarTV().setText("Change Event");
		getLabelHeaderTV().setText("Events");

		if (adapter != null) {
			eventLV.setAdapter(adapter);
		} else {
			// no events
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_event_list);

		initThings();
		findThings(isHeaderThere);
		initView();

		eventLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// progressDialog.show();
				if (eventList.get(position).getId()
						.equals(myApp.getSelectedEvent().getId())) {
					// when you click on same event
				} else {
					Event tempEvent = eventList.get(position);
					tempEvent.setScanStartDate(new Date());
					myApp.setSelectedEvent(tempEvent);

					editor.putInt("position_selected_event", position);
					editor.commit();

					// we can't use notify data set changed coz we need to reset
					// the selectedEvent variable inside the adapter class. That
					// will happen only when we re-ruin the constructor
					adapter = new EventAdapter(EventChangeActivity.this,
							eventList);
					eventLV.setAdapter(adapter);
					getSelectedEventTV().setText(
							myApp.getSelectedEvent().getName());

					getTickets();
				}
			}

		});
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
						progressDialog.hide();
						System.out.println("Ticket Response => "
								+ responseJsonObject.toString());

						int i = myApp
								.convertJOResponseToTicket(responseJsonObject);

						if (i < 1) {
							alert.show();
						}

						Toast.makeText(EventChangeActivity.this,
								"tickets size : " + i, Toast.LENGTH_SHORT)
								.show();

					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						progressDialog.hide();
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