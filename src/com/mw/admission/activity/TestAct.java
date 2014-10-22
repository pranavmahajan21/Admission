package com.mw.admission.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.client.android.CaptureActivity;

public class TestAct extends CaptureActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);

	}

	@Override
	public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
		// TODO Auto-generated method stub
		super.handleDecode(rawResult, barcode, scaleFactor);
		Toast.makeText(this.getApplicationContext(),
				"Scanned code " + rawResult.getText(), Toast.LENGTH_LONG)
				.show();
	}

//	@Override
//	public void handleDecode(Result rawResult, Bitmap barcode) {
//	}
	
	
}
