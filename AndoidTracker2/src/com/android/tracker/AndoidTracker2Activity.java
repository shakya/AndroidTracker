package com.android.tracker;

//main class

import java.util.ArrayList;

import com.android.communication.SMSReceiveService;

import com.android.data.GlobalData;
import com.android.data.Requester;
import com.android.data.Trackee;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AndoidTracker2Activity extends ListActivity {
	/** Called when the activity is first created. */
	private LocationManager locationManager;
	GlobalData globalData;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		addData();
		
		globalData = (GlobalData) getApplicationContext();

		GeoUpdateHandler guh = new GeoUpdateHandler(globalData);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000,
				0, guh);
		// BroadcastReceiver receiver;
		//
		// IntentFilter filter = new IntentFilter();
		// //filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		//
		// receiver = new SMSReceiveService();
		// registerReceiver(receiver, filter);

		// Intent intent2 = new Intent(
		// AndoidTracker2Activity.this,
		// SendMyLocation.class);
		// startActivity(intent2);
		super.onCreate(savedInstanceState);

		//
		// try {
		// Intent intent2 = new Intent(
		// AndoidTracker2Activity.this,
		// LocationSender.class);
		// startActivity(intent2);
		// } catch (Exception e) {
		// // TODO: handle exception
		// Toast.makeText(getApplicationContext(),
		// e.toString(),
		// Toast.LENGTH_SHORT).show();
		// }
		// Toast.makeText(getApplicationContext(),
		// strText,Toast.LENGTH_SHORT).show();

		// set list xml class as the gui
		setContentView(R.layout.list);

		// create array adapter to manage the list
		ArrayAdapter<String> list = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, getResources()
						.getStringArray(R.array.list1));
		// set the list to adapter
		setListAdapter(list);

		// to identify the selected item
		ListView lv = getListView();
		lv.setTextFilterEnabled(false);

		// get the items in the list to an string array. to make the comparison
		// easy
		final String[] items = getResources().getStringArray(R.array.list1);

		// implimenting click listner fot the list
		lv.setOnItemClickListener(new OnItemClickListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * android.widget.AdapterView.OnItemClickListener#onItemClick(android
			 * .widget.AdapterView, android.view.View, int, long)
			 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView textView = (TextView) view;
				String strText = textView.getText().toString();

				// if my location is clicked
				if (strText.equalsIgnoreCase(items[0])) {
					Toast.makeText(getApplicationContext(), strText,
							Toast.LENGTH_SHORT).show();
					try {
						Intent intent = new Intent(AndoidTracker2Activity.this,
								LocationViewr.class);
						intent.putExtra("location", "my");
						startActivity(intent);
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
				/**
				 * if find others is clicked
				 */
				//
				else if (strText.equalsIgnoreCase(items[1])) {
					Toast.makeText(getApplicationContext(), strText,
							Toast.LENGTH_SHORT).show();

					try {
						Intent intent2 = new Intent(
								AndoidTracker2Activity.this,
								CurrentTrackeeActivity.class);
						startActivity(intent2);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// if current trackers is clicked
				else if (strText.equalsIgnoreCase(items[2])) {
					Toast.makeText(getApplicationContext(), strText,
							Toast.LENGTH_SHORT).show();

					try {
						Intent intent2 = new Intent(
								AndoidTracker2Activity.this,
								CurrentTracekrList.class);
						startActivity(intent2);
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(getApplicationContext(), e.toString(),
								Toast.LENGTH_SHORT).show();
					}

				}
				// if request to get location is clicked
				else if (strText.equalsIgnoreCase(items[3])) {
					Toast.makeText(getApplicationContext(), strText,
							Toast.LENGTH_SHORT).show();

					try {

						Intent intent2 = new Intent(
								AndoidTracker2Activity.this,
								SendRequestActivity.class);
						startActivity(intent2);

					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(getApplicationContext(),
								"exception" + e.toString(), Toast.LENGTH_SHORT)
								.show();
					}

				}

				// if recieved requests is clicked
				else if (strText.equalsIgnoreCase(items[4])) {
					Toast.makeText(getApplicationContext(), strText,
							Toast.LENGTH_SHORT).show();

					try {
						Intent intent2 = new Intent(
								AndoidTracker2Activity.this,
								RecievedRequestActivity.class);
						startActivity(intent2);
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
				// if exit is clicked
				else if (strText.equalsIgnoreCase("Exit")) {
					Toast.makeText(getApplicationContext(), strText,
							Toast.LENGTH_SHORT).show();
					System.exit(0);
				}
			}

		});

	}

	private void addData() {
		GlobalData globalData = ((GlobalData) getApplicationContext());
		ArrayList<Trackee> trackees = globalData.getTrackees();

		globalData.addTrackee(new Trackee("pathum", "012343239", 7.0, 82.0,
				0.0, 0.0));
		globalData.addTrackee(new Trackee("prabhath", "0987654345", 7.1, 80.8,
				0.0, 0.0));
		globalData.addTrackee(new Trackee("Jayamal", "0675434567", 7.2, 81.2,
				0.0, 0.0));
		globalData.addTrackee(new Trackee("Manjula", "0234565437", 7.3, 80.3,
				0.0, 0.0));

		ArrayList<Requester> requesters = globalData.getRequesters();

		// add tempery data for demostration purposes
		globalData.addRequester(new Requester("A", "0712345676"));
		globalData.addRequester(new Requester("B", "0712345670"));
		globalData.addRequester(new Requester("C", "0789876554"));
		globalData.addRequester(new Requester("D", "0112345677"));

		ArrayList<Requester> trackers = globalData.getTrackers();

	}

}