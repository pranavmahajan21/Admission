package com.mw.admission.extra;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MyApp extends Application {

	public static final String URL = "http://asset-manage.cloudapp.net:3000/";
	
	// volley stuff
	public static final String TAG = MyApp.class.getSimpleName();
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;// not needed
	
	private static MyApp mInstance;
	
	@Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
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
