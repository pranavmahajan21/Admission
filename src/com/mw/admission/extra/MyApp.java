package com.mw.admission.extra;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.mw.admission.model.Event;

public class MyApp extends Application {

	public static final String URL = "http://asset-manage.cloudapp.net:3000/";
	
	// volley stuff
	public static final String TAG = MyApp.class.getSimpleName();
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;// not needed
	
	private static MyApp mInstance;
	
	
	SharedPreferences sharedPreferences;
	String email,password,userToken;
	Event selectedEvent;
	
	
	
	
	
	public Event getSelectedEvent() {
		return selectedEvent;
	}


	public void setSelectedEvent(Event selectedEvent) {
		this.selectedEvent = selectedEvent;
	}


	public String getUserToken() {
		return userToken;
	}


	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}


	public void saveSharedPreferences()
	{
		Editor editor = sharedPreferences.edit();
		if(this.email != null)
		{
			editor.putString("email", this.email);
		}
		if(this.password != null)
		{
			editor.putString("password", this.password);
		}
		editor.commit();
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		if(sharedPreferences.contains("email"))
		{
			this.email = sharedPreferences.getString("email", null);
		}
		if(sharedPreferences.contains("password"))
		{
			this.password = sharedPreferences.getString("password", null);
		}
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
//	public ImageLoader getImageLoader() {
//		getRequestQueue();
//		if (mImageLoader == null) {
//			mImageLoader = new ImageLoader(this.mRequestQueue,
//					new LruBitmapCache());
//		}
//		return this.mImageLoader;
//	}

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
}
