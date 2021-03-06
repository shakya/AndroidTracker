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

public class CurrentTracekrList extends ListActivity {

	GlobalData globalData;
	ArrayList<Requester> trackers;
	ArrayList<String> trackerNames;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		globalData = ((GlobalData) getApplicationContext());
		trackers = globalData.getTrackers();

		trackerNames = new ArrayList<String>();

		for (Requester t : trackers) {
			trackerNames.add(t.getName());
		}

		// set list xml class as the gui
		setContentView(R.layout.list);

		// create array adapter ot manage the list
		ArrayAdapter<String> list = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, trackerNames);
		// set the list to adapter
		setListAdapter(list);

		// to identify the selected item
		ListView lv = getListView();
		lv.setTextFilterEnabled(false);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView textView = (TextView) view;
				String strText = textView.getText().toString();

				// checks which user trackee is requested
//				Toast.makeText(getApplicationContext(), strText,
//						Toast.LENGTH_SHORT).show();
				for (int i = 0; i < trackerNames.size(); i++) {
					if (strText.equalsIgnoreCase(trackerNames.get(i))) {
						Toast.makeText(getApplicationContext(), strText,
								Toast.LENGTH_SHORT).show();
						try {
							
							Intent intent = new Intent(CurrentTracekrList.this,ManageTracker.class);
							intent.putExtra("tracker", trackers.get(i));										
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
