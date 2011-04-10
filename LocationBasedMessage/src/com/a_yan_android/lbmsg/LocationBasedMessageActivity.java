package com.a_yan_android.lbmsg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.a_yan_android.lib.GoogleServiceUtil;
import com.a_yan_android.lib.GoogleServiceUtil.GOOGLE_AUTH_TOKEN;

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
			sendLocationBasedMessage(edtMessage.getText().toString());
			break;
		
		case R.id.btn_clear:
			edtMessage.setText("");
			break;
		
		default:
			break;
		}
	}

	/**
	 * もっとも最近取得した位置情報を元に、メッセージをGAEアプリに送信、登録
	 * @param message
	 */
	private void sendLocationBasedMessage(String message) {
		final String msg = message;
		final Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		// Debug
		// (Toast.makeText(this, 
		//	 String.format("lat:%f,lon:%f", loc.getLatitude(),loc.getLongitude()), 
		//	 Toast.LENGTH_LONG)).show();
		
		GoogleServiceUtil.Executer exec = new GoogleServiceUtil.Executer(this);
		
		for (Account a : exec.getGoogleAccounts()) {
			
			exec.execute(a, GOOGLE_AUTH_TOKEN.GAE, new GoogleServiceUtil.AbstractGoogleServiceCallback(this) {
				
				@Override
				public HttpPost request() {
					HttpPost post =  new HttpPost("http://typea-android-apps.appspot.com/lbmsg/insert");
					try {
						List<NameValuePair> parms = new ArrayList<NameValuePair>();
						parms.add(new BasicNameValuePair("lat", String.valueOf(loc.getLatitude())));
						parms.add(new BasicNameValuePair("lon", String.valueOf(loc.getLongitude())));
						parms.add(new BasicNameValuePair("message", msg));
						post.setEntity(new UrlEncodedFormEntity(parms, HTTP.UTF_8));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return post;
				}
				
				@Override
				public String getAuthenticateUri(String authToken) {
					return GoogleServiceUtil.defaultGAELoginUrl("typea-android-apps.appspot.com",
																"/lbmsg",
																authToken);
				}
				
				@Override
				public void callback(HttpResponse response) {
					if (response != null) {
						int status = response.getStatusLine().getStatusCode();
						
						StringBuilder buf = new StringBuilder();
						buf.append(String.format("status:%d", status));
						try {
							InputStream in = response.getEntity().getContent();
							BufferedReader reader = new BufferedReader(new InputStreamReader(in));
							String l = null;
							while((l = reader.readLine()) != null) {
								buf.append(l + "\r\n");
							}			
						} catch(Exception e) {
							e.printStackTrace();
						}					
						(Toast.makeText(getContext(), buf.toString(), Toast.LENGTH_LONG)).show();
					}
				}
			});
			
			break;
		}
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