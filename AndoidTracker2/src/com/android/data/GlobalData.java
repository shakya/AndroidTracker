package com.android.data;

import java.util.ArrayList;

import android.app.Application;
import android.content.SharedPreferences;
import android.widget.Toast;

public class GlobalData extends Application {

	private String myState = "STATE ON";
	private ArrayList<Trackee> trackees = new ArrayList<Trackee>();
	private ArrayList<Requester> requesters = new ArrayList<Requester>();
	private ArrayList<Requester> trackers = new ArrayList<Requester>();
	private String myLat="";
	private String myLon="";
	private String myAltitude="";

	public ArrayList<Requester> getTrackers() {
		return trackers;
		
	}

	public void setTrackers(ArrayList<Requester> trackers) {
		this.trackers = trackers;
	}

	public ArrayList<Trackee> getTrackees() {
		return trackees;
	}

	public void setTrackees(ArrayList<Trackee> trackees) {
		this.trackees = trackees;
	}

	public ArrayList<Requester> getRequesters() {
		return requesters;
	}

	public void setRequesters(ArrayList<Requester> requesters) {
		this.requesters = requesters;
	}

	public void addTrackee(Trackee trackee) {
		trackees.add(trackee);
	}

	public void addRequester(Requester requester) {
		requesters.add(requester);
	}

	public void addTracker(Requester tracker) {
		trackers.add(tracker);
	}

	public void removeTrackee(Trackee trackee) {
		int trackeeIndex = 0;
		Toast.makeText(getApplicationContext(),
				"removing " + trackee.getName(), Toast.LENGTH_SHORT).show();
		for (Trackee t : trackees) {
			if (t.getName().equalsIgnoreCase(trackee.getName())) {
				trackees.remove(trackeeIndex);
				// Toast.makeText(getApplicationContext(),
				// "ASDEFR",Toast.LENGTH_SHORT).show();
				break;
			}
			trackeeIndex++;
		}
	}

	public void removeRequester(Requester requester) {
		int requesterIndex = 0;
		Toast.makeText(getApplicationContext(), requester.getName(),
				Toast.LENGTH_SHORT).show();
		for (Requester t : requesters) {
			if (t.getPhoneNo().equalsIgnoreCase(requester.getPhoneNo())) {
				requesters.remove(requesterIndex);
				Toast.makeText(getApplicationContext(), t.getPhoneNo(),
						Toast.LENGTH_SHORT).show();
				break;
			}
			requesterIndex++;
		}

	}

	public void removeTracker(Requester tracker) {
		int trackerIndex = 0;
		for (Requester t : requesters) {
			if (t.getName().equalsIgnoreCase(tracker.getName())) {
				trackers.remove(trackerIndex);
				break;
			}
			trackerIndex++;
		}
	}

	public String getState() {
		return myState;
	}

	public void setState(String s) {
		myState = s;
	}

	public void updateGlobalData(){
		SharedPreferences settings = getSharedPreferences("GLOBALDATA", 0);
		
	}

	private String getMyAltitude() {
		return myAltitude;
	}

	private void setMyAltitude(String myAltitude) {
		this.myAltitude = myAltitude;
	}

	private String getMyLon() {
		return myLon;
	}

	private void setMyLon(String myLon) {
		this.myLon = myLon;
	}

	private String getMyLat() {
		return myLat;
	}

	private void setMyLat(String myLat) {
		this.myLat = myLat;
	}
}
