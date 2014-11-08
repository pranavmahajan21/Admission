package com.mw.admission.activity;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mw.admission.extra.CreateDialog;
import com.mw.admission.extra.MyApp;
import com.mw.admission.model.Scan;
import com.mw.admission.model.Ticket;

public class TicketDetailActivity extends MenuButtonActivity {

	boolean isHeaderThere = true;

	MyApp myApp;
	Intent previousIntent;

	int position;
	Ticket selectedTicket;

	LayoutInflater inflater;

	LinearLayout parentLL;
	LinearLayout oldChildLL;
	View newChildLL;

	TextView nameTV, barcodeTV, statusTV, orderNumberTV, numberOfTicketsTV,
			actionTV, action2TV;
	TextView scanDateTV, scannerIDTV;

	TextView label_name_TV, label_barcode_TV, label_status_TV, label_order_TV,
			label_ticket_TV, label_action_TV;

	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	Gson gson;

	CreateDialog createDialog;
	ProgressDialog progressDialog;

	RequestQueue queue;

	private void initThings() {
		previousIntent = getIntent();

		myApp = (MyApp) getApplicationContext();

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		editor = sharedPreferences.edit();

		GsonBuilder builder = new GsonBuilder();
		gson = builder.create();

		position = previousIntent.getIntExtra("position", 0);
		selectedTicket = myApp.getTicketList().get(position);

		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		newChildLL = inflater.inflate(R.layout.child_ticket_details, null);

		queue = Volley.newRequestQueue(this);

		createDialog = new CreateDialog(this);
		progressDialog = createDialog.createProgressDialog("Check In",
				"This may take some time", true, null);
	}

	private void setTypeface() {

		nameTV.setTypeface(myApp.getTypefaceRegularSans());
		barcodeTV.setTypeface(myApp.getTypefaceRegularSans());
		statusTV.setTypeface(myApp.getTypefaceRegularSans());
		orderNumberTV.setTypeface(myApp.getTypefaceRegularSans());
		numberOfTicketsTV.setTypeface(myApp.getTypefaceRegularSans());

		actionTV.setTypeface(myApp.getTypefaceRegularSans());
		action2TV.setTypeface(myApp.getTypefaceRegularSans());

		label_name_TV.setTypeface(myApp.getTypefaceRegularSans());
		label_barcode_TV.setTypeface(myApp.getTypefaceRegularSans());
		label_status_TV.setTypeface(myApp.getTypefaceRegularSans());
		label_order_TV.setTypeface(myApp.getTypefaceRegularSans());
		label_ticket_TV.setTypeface(myApp.getTypefaceRegularSans());
		label_action_TV.setTypeface(myApp.getTypefaceRegularSans());
	}

	public void findThings() {
		super.findThings(isHeaderThere);

		parentLL = (LinearLayout) findViewById(R.id.parent_LL);
		oldChildLL = (LinearLayout) findViewById(R.id.child_LL);

		nameTV = (TextView) findViewById(R.id.name_TV);
		barcodeTV = (TextView) findViewById(R.id.barcode_TV);
		statusTV = (TextView) findViewById(R.id.status_TV);
		orderNumberTV = (TextView) findViewById(R.id.orderNumber_TV);
		numberOfTicketsTV = (TextView) findViewById(R.id.numberOfTickets_TV);

		actionTV = (TextView) findViewById(R.id.action_TV);
		action2TV = (TextView) findViewById(R.id.action2_TV);

		label_name_TV = (TextView) findViewById(R.id.label_name_TV);
		label_barcode_TV = (TextView) findViewById(R.id.label_barcode_TV);
		label_status_TV = (TextView) findViewById(R.id.label_status_TV);
		label_order_TV = (TextView) findViewById(R.id.label_order_TV);
		label_ticket_TV = (TextView) findViewById(R.id.label_ticket_TV);
		label_action_TV = (TextView) findViewById(R.id.label_action_TV);

		setTypeface();
	}

	private void findThingsForNewView() {
		scanDateTV = (TextView) findViewById(R.id.scanDate_TV);
		scannerIDTV = (TextView) findViewById(R.id.scannerID_TV);

		scanDateTV.setTypeface(myApp.getTypefaceRegularSans());
		scannerIDTV.setTypeface(myApp.getTypefaceRegularSans());
	}

	public void initView() {
		super.initView();
		getLabelActionBarTV().setText("Will Call");
		getLabelHeaderTV().setText("Ticket Detail");

		nameTV.setText(selectedTicket.getNameOfGuest());
		barcodeTV.setText(selectedTicket.getBarcode());
		orderNumberTV.setText(selectedTicket.getOrderId());

		// If we don't convert int to String it gives ResourceNotFoundException,
		// don't know why
		numberOfTicketsTV.setText(Integer.toString(selectedTicket
				.getOrderQuantity()));

		if (selectedTicket.isCheckedIn()) {
			onAdmit(null);
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ticket_details);

		initThings();
		findThings();
		initView();

	}

	List<Ticket> tempTicketList;

	public void onAdmit(View view) {
		if (view != null) {
			Scan scan = myApp.createScanObject(selectedTicket.getBarcode(),
					true, position);

			int x = scan.getResult();
			if (x == 0) {
				// TODO check for internet connection
				checkInTicket(scan);
			} else {
				// when x==2 , (x==1 won't be possible on this page)
				Toast.makeText(this, "Duplicate Entry", Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			statusTV.setText("Admitted");
			statusTV.setTextColor(Color.parseColor("#ff0000"));

			parentLL.removeView(oldChildLL);
			parentLL.addView(newChildLL);

			findThingsForNewView();

			if (selectedTicket.getScanTime() != null) {
				scanDateTV.setText(selectedTicket.getScanned_at());
			} else {
				scanDateTV.setText("NA");
			}
			scannerIDTV.setText(selectedTicket.getScannerID());
		}

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
//							selectedTicket.setScanTimeAndScannerIDAndCheckedIn(
//									scan.getScanDate(), myApp.getLoginUser()
//											.getUsername(), false);
//							selectedTicket.setCheckedIn(true);
							
							selectedTicket.setScanTimeAndScannerIDAndCheckedIn(
									scan.getScanDate(), myApp.getLoginUser()
											.getUsername(), true);
//							selectedTicket.setCheckedIn(true);

							// changes in view
							statusTV.setText("Admitted");
							statusTV.setTextColor(Color.parseColor("#ff0000"));

							// replace view in parent LL
							parentLL.removeView(oldChildLL);
							parentLL.addView(newChildLL);

							findThingsForNewView();

							scanDateTV.setText(selectedTicket.getScanTime()
									.toString());
							scannerIDTV.setText(selectedTicket.getScannerID());

							/** update global ticket list **/
							myApp.getTicketList().set(position, selectedTicket);

							/** update global order map **/
							/**
							 * BEWARE. This code can lead to
							 * OutOfMemoryException. Test properly. BEWARE
							 **/
							List<Ticket> tempOrderTicketList = myApp
									.getOrderMap().get(
											selectedTicket.getOrderId());
							for (int i = 0; i < tempOrderTicketList.size(); i++) {
								if (tempOrderTicketList.get(i).getBarcode()
										.equals(selectedTicket.getBarcode())) {
									tempOrderTicketList.set(i, selectedTicket);
								}
							}

							/** update preferences **/
							editor.putString("ticketList",
									gson.toJson(myApp.getTicketList()));
							editor.commit();
							
							scan.setResult(0);
							myApp.getScanList().add(scan);
							
							editor.putString("scanList", gson.toJson(myApp.getScanList()));
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
												TicketDetailActivity.this,
												"Duplicate Ticket",
												Toast.LENGTH_LONG).show();
									}
								}
								scan.setResult(1);
								myApp.getScanList().add(scan);
								
								editor.putString("scanList", gson.toJson(myApp.getScanList()));
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
		System.out.println("onAdmitAll");
		tempTicketList = myApp.getOrderMap().get(selectedTicket.getOrderId());
		progressDialog.show();
		JSONObject aa = myApp.createOrderJSON(tempTicketList);
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

								// update Ticket & order List in Global
								// Application class

								List<Ticket> tempTicketList = myApp
										.getTicketList();
								for (int i = 0; i < tempTicketList.size(); i++) {
									/**
									 * if(orderId matches) { update ticket list
									 * & create new scan object }
									 **/
									if (tempTicketList
											.get(i)
											.getOrderId()
											.equals(selectedTicket.getOrderId())) {
										System.out.println("true");
										updateTicketListAndScanList(i);
									}
								}
								myApp.setTicketList(tempTicketList);

								// changes in view
								statusTV.setText("Admitted");
								statusTV.setTextColor(Color
										.parseColor("#ff0000"));

								// replace view in parent LL
								parentLL.removeView(oldChildLL);
								parentLL.addView(newChildLL);

								findThingsForNewView();

								scanDateTV.setText(selectedTicket.getScanTime()
										.toString());
								scannerIDTV.setText(selectedTicket
										.getScannerID());

								Toast.makeText(
										TicketDetailActivity.this,
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
							 * update Ticket in Global Application class &
							 * create Scan Objects
							 **/
							/**
							 * No. of tickets to update = All tickets in order -
							 * Tickets that have been rejected
							 **/
							List<Ticket> tempOrderTicketList = myApp
									.getOrderMap().get(
											selectedTicket.getOrderId());

							List<Ticket> tempTicketList = myApp.getTicketList();

							for (int i = 0; i < tempOrderTicketList.size(); i++) {
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

								// if code reaches this point, it means that
								// tempOrderTicketList.get(i) barcode is not
								// rejected
								for (int k = 0; k < tempTicketList.size(); k++) {
									if (tempTicketList
											.get(k)
											.getBarcode()
											.equals(tempOrderTicketList.get(i)
													.getBarcode())) {

										updateTicketListAndScanList(k);
									}
								}// for
							}

							myApp.setTicketList(tempTicketList);

							// Show Toast/Alert
							if (tempTicketList.size() == rejectedTicketsJSONArray
									.length()) {
								Toast.makeText(TicketDetailActivity.this,
										"Do not admit any guest on order",
										Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(
										TicketDetailActivity.this,
										"Admit "
												+ (tempTicketList.size() - rejectedTicketsJSONArray
														.length()) + "/"
												+ tempTicketList.size()
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

	private void updateTicketListAndScanList(int i) {
		// TODO the date used in this function are not correct, we have to fetch
		// them from
		myApp.getTicketList()
				.get(i)
				.setScanTimeAndScannerIDAndCheckedIn(new Date(),
						myApp.getLoginUser().getUsername(), true);

		Scan scan = new Scan(myApp.getTicketList().get(i).getBarcode(), 0,
				new Date());

		myApp.getScanList().add(scan);

		editor.putString("scanList", gson.toJson(myApp.getScanList()));
		editor.commit();
	}

}
