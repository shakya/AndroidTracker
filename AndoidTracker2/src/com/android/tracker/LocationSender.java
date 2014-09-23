package com.android.tracker;

import java.util.ArrayList;

import com.android.communication.SMSSender;
import com.android.data.GlobalData;
import com.android.data.Requester;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class LocationSender extends Activity {
	LocationManager locMgr;
	GlobalData globalData;
	
	 Thread thread=null;
	 final int SLEEP_TIME= 2000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
		globalData = ((GlobalData) getApplicationContext());
		
		thread = new Thread() {
		      public void run() {
		    	  sendMyLocation();
		        try{
		          
		        } catch (Exception e) {
		          
		        }
		       
		                               
		      }
		    };
		    thread.start();
	
	}
	public void sendMyLocation() {
		SMSSender smsSender = new SMSSender();
		while (true) {
			Location recentLoc = locMgr
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);

			String myLocation = "";

			Double lat = 0.0;
			Double lon = 0.0;
			Double alt = 0.0;
			Double dir = 0.0;

			lat = recentLoc.getLatitude();
			lon = recentLoc.getLongitude();
			alt = recentLoc.getAltitude();
			dir = (double) recentLoc.getBearing();

			myLocation = "ATrckrLocation#" + getMyPhoneNumber() + "#" + lat
					+ "#" + lon + "#" + alt + "#" + dir;

			ArrayList<Requester> trackers = globalData.getTrackers();

			for (Requester tracker : trackers) {
				String phoneNo = tracker.getPhoneNo();
				smsSender.sendSMS(phoneNo, myLocation);
			}
			Toast.makeText(getApplicationContext(),
					"Location sent to " + trackers.size() + " trackers",
					Toast.LENGTH_SHORT).show();
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	private String getMyPhoneNumber() {
		TelephonyManager mTelephonyMgr;
		mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getLine1Number();
	}
}
