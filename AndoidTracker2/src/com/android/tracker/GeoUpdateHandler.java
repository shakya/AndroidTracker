package com.android.tracker;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.android.communication.SMSSender;

import com.android.data.GlobalData;
import com.android.data.Requester;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;

public class GeoUpdateHandler implements LocationListener {
	double currentLat;
	double currentLon;
	double currentAlt;
	double currentDir;
	GlobalData globalData;
	ArrayList<Requester> trackers;
	
	
	public GeoUpdateHandler(GlobalData globalData){
		this.globalData=globalData;
	}

	@Override
	public void onLocationChanged(Location location) {
		System.out.println("on location changed");
		SMSSender smsSender = new SMSSender();
		
		trackers = globalData.getTrackers();
	
		currentLat = location.getLatitude();
		currentLon = location.getLongitude();
		currentAlt = location.getAltitude();
		currentDir = location.getBearing();
		
		String myLocation = "ATrckrLocation#" +  currentLat
				+ "#" + currentLon + "#" + currentAlt + "#" + currentDir;

		ArrayList<Requester> trackers = globalData.getTrackers();

		for (Requester tracker : trackers) {
			String phoneNo = tracker.getPhoneNo();
			System.out.println("sms sender"+phoneNo+myLocation);
			smsSender.sendSMS(phoneNo, myLocation);
		}
		
		
		}

		
	

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
	
//	public void sendMyLocation() {
//		SMSSender smsSender = new SMSSender();
//		while (true) {
//			Location recentLoc = locMgr
//					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//			String myLocation = "";
//
//			Double lat = 0.0;
//			Double lon = 0.0;
//			Double alt = 0.0;
//			Double dir = 0.0;
//
//			lat = recentLoc.getLatitude();
//			lon = recentLoc.getLongitude();
//			alt = recentLoc.getAltitude();
//			dir = (double) recentLoc.getBearing();
//
//			myLocation = "ATrckrLocation#" +  lat
//					+ "#" + lon + "#" + alt + "#" + dir;
//
//			ArrayList<Requester> trackers = globalData.getTrackers();
//
//			for (Requester tracker : trackers) {
//				String phoneNo = tracker.getPhoneNo();
//				smsSender.sendSMS(phoneNo, myLocation);
//			}
//			Toast.makeText(getApplicationContext(),
//					"Location sent to " + trackers.size() + " trackers",
//					Toast.LENGTH_SHORT).show();
//			try {
//				Thread.sleep(SLEEP_TIME);
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//		}
}