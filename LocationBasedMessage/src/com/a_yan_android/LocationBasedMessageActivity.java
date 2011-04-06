package com.a_yan_android;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LocationBasedMessageActivity extends Activity  
											implements View.OnClickListener,
														LocationListener {
	
	private LocationManager locationManager;
	
	private EditText edtMessage;
	private Button btnSend;
	private Button btnClear;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		locationManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER, 
        		60000,   // 位置取得間隔の最小値(ミリ秒) 電源を節約するためのヒントなので実際の間隔はこれより大きくも小さくもなり得る
        		50,       // 通知位置間隔の最小値(m)
        		this);
		
        edtMessage = (EditText) findViewById(R.id.edt_message);
        btnSend    = (Button) findViewById(R.id.btn_send);
        btnClear   = (Button) findViewById(R.id.btn_clear);
        
        btnSend.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send:
			sendLocationBasedMessage();
			break;
		
		case R.id.btn_clear:
			edtMessage.setText("");
			break;
		
		default:
			break;
		}
	}

	private void sendLocationBasedMessage() {
		Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		// Debug
		// (Toast.makeText(this, 
		//	 String.format("lat:%f,lon:%f", loc.getLatitude(),loc.getLongitude()), 
		//	 Toast.LENGTH_LONG)).show();
		
	}
	
	
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
}