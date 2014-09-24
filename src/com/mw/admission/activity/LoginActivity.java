package com.mw.admission.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.mw.admission.adapter.EventAdapter;
import com.mw.admission.extra.CreateDialog;
import com.mw.admission.extra.MyApp;
import com.mw.admission.model.Event;

public class LoginActivity extends Activity {

	// Intent nextIntent;
	LayoutInflater inflater;

	LinearLayout parentViewLL;
	LinearLayout childViewLoginLL;
	View childViewEventLL;
	View childViewOptionLL;
	EditText username;
	EditText password;
	
	TextView login;

	ListView eventLV;
	EventAdapter adapter;
	List<Event> eventList;
	AlertDialog alertDialog;
	JSONObject jsonFromServer;
	RequestQueue queue;
	CreateDialog createDialog;
	ProgressDialog progressDialog;
	
	MyApp globalVariable;

	private void findLoginThings() {
		parentViewLL = (LinearLayout) findViewById(R.id.parent_view_LL);
		childViewLoginLL = (LinearLayout) findViewById(R.id.child_view_LL);
		username = (EditText)findViewById(R.id.user_ET);
		password = (EditText)findViewById(R.id.password_ET);
		login = (TextView)findViewById(R.id.header_TV);
	}

	private void findEventThings() {
		eventLV = (ListView) findViewById(R.id.event_LV);

		adapter = new EventAdapter(this, eventList);
	}

	private void initThings() {
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		globalVariable = (MyApp)getApplicationContext();
		if(globalVariable.getEmail()!=null && globalVariable.getPassword() != null)
		{
			username.setText(globalVariable.getEmail());
			password.setText(globalVariable.getPassword());
		}
	 queue = Volley.newRequestQueue(this);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Toast.makeText(this, "dsadsa", Toast.LENGTH_SHORT).show();
		setContentView(R.layout.activity_login);

		findLoginThings();
		initThings();
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
//		LoginAsyncTask asyncTask = new LoginAsyncTask();
//		asyncTask.execute();
		System.out.println(">>>>>> login");
		JSONObject  jsonobject;
		try{
		  jsonobject = new JSONObject();
		jsonobject.put("login", username.getText().toString());
		jsonobject.put("password", password.getText().toString());
		createDialog = new CreateDialog(LoginActivity.this);
		progressDialog = createDialog.createProgressDialog("Validation", "Please wait while we validate your login.", true, null);
		progressDialog.show();
		 
		 String url = "http://beta.missiontix.com/api/users/authenticate";
		 JsonObjectRequest jsObjRequest = new JsonObjectRequest(Method.POST, url,jsonobject, new Response.Listener<JSONObject>() {
			 
	            @Override
	            public void onResponse(JSONObject response) {
	                // TODO Auto-generated method stub
	                System.out.println(">>>>Response => "+response.toString());
	                globalVariable.setEmail(username.getText().toString());
					globalVariable.setPassword(password.getText().toString());
					try{
					globalVariable.setUserToken(response.getString("token"));
					}catch(Exception e)
					{
						e.printStackTrace();
					}
					getEvents();
	            }
	        }, new Response.ErrorListener() {
	 
	            @Override
	            public void onErrorResponse(VolleyError error) {
	                // TODO Auto-generated method stub
                      error.printStackTrace();
                      login.setTextColor(Color.parseColor("#00AEA4"));
                      if(error instanceof NetworkError)
                      {
                      login.setText(""+"Email address provided is not registered.");
                      }
                      if(error instanceof NoConnectionError)
                      {
                    	  login.setText(""+"you are now offline.");
                      }
                      if (error instanceof ServerError)
                      {
                    	  login.setText(""+"Email address provided is not registered.");
                      }
	            }
	        });
		 RetryPolicy policy = new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
	     jsObjRequest.setRetryPolicy(policy);   
		 queue.add(jsObjRequest);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		 

	}

	public void onScanner(View view) {
		System.out.println("11");
	}

	public void onWillCall(View view) {
		System.out.println("22");
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
		// TODO Auto-generated method stub
		super.onStop();
		globalVariable.saveSharedPreferences();
	}
	
	public void getEvents()
	{
		String eventsUrl = "http://private-db490-missiontix.apiary-proxy.com/api/users/events/"+globalVariable.getUserToken();
		JsonArrayRequest  req = new JsonArrayRequest(eventsUrl, new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray arg0) {
				// TODO Auto-generated method stub
				progressDialog.dismiss();
				eventList = new ArrayList<Event>();
				Gson gson = new Gson();
				try{
					System.out.println(">>>>> events size:"+arg0.length());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for(int i=0;i<arg0.length();i++)
				{
					
					Event e = gson.fromJson(arg0.getJSONObject(i).toString(), Event.class);
					e.setDate(sdf.parse(arg0.getJSONObject(i).getJSONObject("start").getString("date")));
					System.out.println(">>>>> event name:"+e.getName());
					eventList.add(e);
					
				}
				System.out.println(">>>>>> events size:"+eventList.size());
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				childViewEventLL = inflater.inflate(R.layout.child_list_event, null);
				parentViewLL.removeAllViews();
				parentViewLL.addView(childViewEventLL);
				findEventThings();
				eventLV.setAdapter(adapter);

				eventLV.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// fetch tickets
						
						// this code would be used  after getting tickets
						
						
//						childViewOptionLL = inflater.inflate(R.layout.child_option, null);
//						parentViewLL.removeAllViews();
//						parentViewLL.addView(childViewOptionLL);
						globalVariable.setSelectedEvent(eventList.get(position));
					}

				});
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				arg0.getStackTrace();
			}
		});
		queue.add(req);
	} // end of getEvents
	
	
	public void getTickets(View v)
	{
		Toast.makeText(this, "No tickets for "+globalVariable.getSelectedEvent().getName(), Toast.LENGTH_SHORT).show();
	}
	
	
	
}