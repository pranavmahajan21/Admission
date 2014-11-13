package com.mw.admission.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mw.admission.adapter.TicketAdapter;
import com.mw.admission.extra.CreateDialog;
import com.mw.admission.extra.MyApp;
import com.mw.admission.model.Scan;
import com.mw.admission.model.Ticket;

public class OrderDetailActivity extends MenuButtonActivity {

	boolean isHeaderThere = true;

	MyApp myApp;
	Intent previousIntent;

	LayoutInflater inflater;

	int position;

	TextView nameTV, orderNumberTV, numberOfTicketsTV;

	LinearLayout parentLL;
	LinearLayout childLL;

	ListView barcodeLV;
	List<Ticket> ticketsOfOrderList;
	TicketAdapter adapter;

	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	Gson gson;

	CreateDialog createDialog;
	ProgressDialog progressDialog;

	RequestQueue queue;

	Ticket tempTicket = null;
	String orderId;

	private void initThings() {
		previousIntent = getIntent();
		position = previousIntent.getIntExtra("position", 0);
		myApp = (MyApp) getApplicationContext();

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		editor = sharedPreferences.edit();

		GsonBuilder builder = new GsonBuilder();
		gson = builder.create();

		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		ticketsOfOrderList = new ArrayList<List<Ticket>>(myApp.getOrderMap()
				.values()).get(position);
		orderId = ticketsOfOrderList.get(0).getOrderId();
		// ticketsOfOrderList = (myApp.getOrderMap()
		// .values()).get(position);

		adapter = new TicketAdapter(this, ticketsOfOrderList, 2);

		queue = Volley.newRequestQueue(this);

		createDialog = new CreateDialog(this);
		progressDialog = createDialog.createProgressDialog("Check In",
				"This may take some time", true, null);
	}

	private void findThings() {
		super.findThings(isHeaderThere);

		nameTV = (TextView) findViewById(R.id.name_TV);
		orderNumberTV = (TextView) findViewById(R.id.orderNumber_TV);
		numberOfTicketsTV = (TextView) findViewById(R.id.numberOfTickets_TV);

		barcodeLV = (ListView) findViewById(R.id.barcode_LV);

		parentLL = (LinearLayout) findViewById(R.id.parent_LL);
		childLL = (LinearLayout) findViewById(R.id.child_LL);
	}

	public void initView() {
		super.initView();
		getLabelActionBarTV().setText("Will Call");
		getLabelHeaderTV().setText("Order Detail");

		Ticket tempTicket = ticketsOfOrderList.get(0);

		nameTV.setText(tempTicket.getNameOfGuest());
		orderNumberTV.setText(orderId);

		numberOfTicketsTV.setText(Integer.toString(tempTicket
				.getOrderQuantity()));

		barcodeLV.setAdapter(adapter);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_order_details);
		findThings();
		initThings();
		initView();
	}

	public void onAdmitIndividually(View view) {
		parentLL.removeView(childLL);
		adapter = new TicketAdapter(this, ticketsOfOrderList, 3);
		barcodeLV.setAdapter(adapter);
		barcodeLV.setDivider(new ColorDrawable(this.getResources().getColor(
				R.color.grey_text)));

		barcodeLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				tempTicket = ticketsOfOrderList.get(position);

				if (tempTicket.isCheckedIn()) {
					Toast.makeText(OrderDetailActivity.this,
							"Already admitted.", Toast.LENGTH_SHORT).show();
				} else {
					Scan scan = myApp.createScanObject(tempTicket.getBarcode(),
							false, -1);
					checkInTicket(scan);
				}

			}

		});
		// barcodeLV.setDividerHeight(1);
	}

	private void checkInTicket(final Scan scan) {
		progressDialog.show();
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("barcode", scan.getBarcode());
			jsonObject.put("check_in_order", false);
			jsonObject.put("timestamp",
					myApp.formatDateToString4(scan.getScanDate()));

			String url = MyApp.URL + MyApp.TICKET
					+ myApp.getSelectedEvent().getId() + "/"
					+ myApp.getLoginUser().getToken();
			System.out.println("ticket URL : " + url);

			JsonObjectRequest jsObjRequest = new JsonObjectRequest(Method.PUT,
					url, jsonObject, new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							progressDialog.dismiss();
							System.out.println(">>>>Response => "
									+ response.toString());
							Toast.makeText(OrderDetailActivity.this,
									"Admit Guest", Toast.LENGTH_LONG).show();

							tempTicket.setScanTimeAndScannerIDAndCheckedIn(scan
									.getScanDate(), myApp.getLoginUser()
									.getUsername(), true);

							/** changes in view **/

							/** update global ticket list **/
							myApp.getTicketList().set(
									myApp.getTicketPosition(tempTicket),
									tempTicket);

							/** update global order map **/
							/**
							 * BEWARE. This code can lead to
							 * OutOfMemoryException. Test properly. BEWARE
							 **/
							List<Ticket> tempOrderTicketList = myApp
									.getOrderMap().get(tempTicket.getOrderId());
							for (int i = 0; i < tempOrderTicketList.size(); i++) {
								if (tempOrderTicketList.get(i).getBarcode()
										.equals(tempTicket.getBarcode())) {
									tempOrderTicketList.set(i, tempTicket);
								}
							}

							/** update preferences **/
							editor.putString("ticketList",
									gson.toJson(myApp.getTicketList()));
							editor.commit();

							scan.setResult(0);
							myApp.getScanList().add(scan);

							editor.putString("scanList",
									gson.toJson(myApp.getScanList()));
							editor.commit();
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							progressDialog.dismiss();
							System.out.println("ERROR  : " + error.getMessage());
							error.printStackTrace();

							try {
								JSONObject jsonObject = new JSONObject(error
										.getMessage());
								if (jsonObject.has("reason")) {
									if (jsonObject.getString("reason")
											.equalsIgnoreCase("duplicate")) {
										Toast.makeText(
												OrderDetailActivity.this,
												"Duplicate Ticket",
												Toast.LENGTH_LONG).show();
									}
								}
								scan.setResult(1);
								myApp.getScanList().add(scan);

								editor.putString("scanList",
										gson.toJson(myApp.getScanList()));
								editor.commit();
							} catch (JSONException e) {
								e.printStackTrace();
							}

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
					}) {

				@Override
				protected VolleyError parseNetworkError(VolleyError volleyError) {
					if (volleyError.networkResponse != null
							&& volleyError.networkResponse.data != null) {
						VolleyError error = new VolleyError(new String(
								volleyError.networkResponse.data));
						volleyError = error;
					}
					return volleyError;
				}

			};

			RetryPolicy policy = new DefaultRetryPolicy(30000,
					DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
			jsObjRequest.setRetryPolicy(policy);
			queue.add(jsObjRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// end of function

	public void onAdmitAll(View view) {
		progressDialog.show();
		JSONObject aa = myApp.createOrderJSON(ticketsOfOrderList);
		checkInOrder(aa);
	}

	private void checkInOrder(JSONObject jsonObject) {
		try {

			String url = MyApp.URL + MyApp.TICKET
					+ myApp.getSelectedEvent().getId() + "/"
					+ myApp.getLoginUser().getToken();
			System.out.println("ticket URL : " + url);
			JsonObjectRequest jsObjRequest = new JsonObjectRequest(Method.POST,
					url, jsonObject, new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							progressDialog.dismiss();
							System.out.println(">>>>Response => "
									+ response.toString());
							if (!response.has("rejected_barcodes")
									&& response.has("message")) {

								/**
								 * update Ticket & order & Scan List in Global
								 * Application class
								 **/

								List<Ticket> tempTicketList = myApp
										.getTicketList();
								for (int i = 0; i < tempTicketList.size(); i++) {
									/**
									 * if(orderId matches) { update ticket list
									 * & create new scan object }
									 **/
									if (tempTicketList.get(i).getOrderId()
											.equals(orderId)) {
										System.out.println("true");
										myApp.updateTicketListAndScanList(i);
									}
								}
								myApp.setTicketList(tempTicketList);

								/** changes in view **/

								Toast.makeText(
										OrderDetailActivity.this,
										"Admit all " + tempTicketList.size()
												+ " on order.",
										Toast.LENGTH_LONG).show();
							}// if
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							progressDialog.dismiss();
							System.out.println("ERROR  : " + error.getMessage());
							error.printStackTrace();

							JSONArray rejectedTicketsJSONArray = null;
							try {
								JSONObject jsonObject = new JSONObject(error
										.getMessage());
								if (jsonObject.has("rejected_barcodes")) {
									rejectedTicketsJSONArray = jsonObject
											.getJSONArray("rejected_barcodes");
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}

							/**
							 * update Ticket & order & Scan List in Global
							 * Application class
							 **/
							/**
							 * No. of tickets to update = All tickets in order -
							 * Tickets that have been rejected
							 **/
							List<Ticket> tempOrderTicketList = myApp
									.getOrderMap().get(orderId);

							List<Ticket> tempTicketList = myApp.getTicketList();

							for (int i = 0; i < tempOrderTicketList.size(); i++) {
								/**
								 * check if ticket(i) is in list of rejected
								 * barcodes
								 **/
								for (int j = 0; j < rejectedTicketsJSONArray
										.length(); j++) {
									try {
										JSONObject jsonObject = rejectedTicketsJSONArray
												.getJSONObject(j);
										if (jsonObject.getString("barcode")
												.equals(tempOrderTicketList
														.get(i).getBarcode())) {
											break;
										}
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}

								/**
								 * if code reaches this point, it means that
								 * ticket(i) is not in list of rejected
								 * barcodes. Therefore, update ticket & scan
								 * list.
								 **/
								for (int k = 0; k < tempTicketList.size(); k++) {
									if (tempTicketList
											.get(k)
											.getBarcode()
											.equals(tempOrderTicketList.get(i)
													.getBarcode())) {

										myApp.updateTicketListAndScanList(k);
									}
								}// for
							}

							myApp.setTicketList(tempTicketList);

							// Show Toast/Alert
							if (tempOrderTicketList.size() == rejectedTicketsJSONArray
									.length()) {
								Toast.makeText(OrderDetailActivity.this,
										"Do not admit any guest on order.",
										Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(
										OrderDetailActivity.this,
										"Admit "
												+ (tempOrderTicketList.size() - rejectedTicketsJSONArray
														.length()) + "/"
												+ tempOrderTicketList.size()
												+ " guests", Toast.LENGTH_SHORT)
										.show();
							}

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

				@Override
				protected VolleyError parseNetworkError(VolleyError volleyError) {
					if (volleyError.networkResponse != null
							&& volleyError.networkResponse.data != null) {
						VolleyError error = new VolleyError(new String(
								volleyError.networkResponse.data));
						volleyError = error;
					}
					return volleyError;
				}

			};

			RetryPolicy policy = new DefaultRetryPolicy(30000,
					DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
			jsObjRequest.setRetryPolicy(policy);
			queue.add(jsObjRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
