package com.android.tracker;

import java.util.ArrayList;

import com.android.data.GlobalData;
import com.android.data.Requester;
import com.android.data.Trackee;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CurrentTrackeeActivity extends ListActivity {

	// create array lists to keep trackee data
	// ArrayList<Trackee> trackees= new ArrayList<Trackee>();
	ArrayList<String> trackeeNames = new ArrayList<String>();

	// AppData appData = AppData.getDataClass();
	GlobalData globalData;
	ArrayList<Trackee> trackees;

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ArrayList<String> requesterDetails=new ArrayList<String>();

	// ArrayList<Requester> requesters= appData.getRequesters();
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.locationviewer);
		globalData = ((GlobalData) getApplicationContext());
		trackees = globalData.getTrackees();

		// add tempery data for demostration purposes

		String state = globalData.getState();
		Toast.makeText(getApplicationContext(), state, Toast.LENGTH_SHORT)
				.show();

		for (Trackee t : trackees) {
			trackeeNames.add(t.getName());
		}

		// ///////////////////////////////////////

		// set list xml class as the gui
		setContentView(R.layout.list);

		// create array adapter ot manage the list
		ArrayAdapter<String> list = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, trackeeNames);
		// set the list to adapter
		setListAdapter(list);

		// to identify the selected item
		ListView lv = getListView();
		lv.setTextFilterEnabled(false);

		// implimenting click listner fot the list
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView textView = (TextView) view;
				String strText = textView.getText().toString();

				// checks which user trackee is requested
				for (int i = 0; i < trackeeNames.size(); i++) {
					if (strText.equalsIgnoreCase(trackeeNames.get(i))) {
						Toast.makeText(getApplicationContext(), strText,
								Toast.LENGTH_SHORT).show();
						try {

							Intent intent = new Intent(
									CurrentTrackeeActivity.this,
									TrackeeLocationViewer.class);
							intent.putExtra("trackee", trackees.get(i));
							startActivity(intent);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}

				}
			}

		});

	}

}
