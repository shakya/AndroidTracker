package com.android.tracker;

import java.security.PublicKey;
import java.util.ArrayList;

import com.android.data.GlobalData;
import com.android.data.Requester;
import com.android.data.Trackee;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class RecievedRequestActivity extends ListActivity {

	GlobalData globalData;
	// create array lists to keep recieved requestdata
	// ArrayList<Requester> requesters= new ArrayList<Requester>();
	ArrayList<String> requesterDetails = new ArrayList<String>();
	ArrayList<String> requesterNames = new ArrayList<String>();

	ArrayList<Requester> requesters;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.locationviewer);
		globalData = ((GlobalData) getApplicationContext());
		requesters = globalData.getRequesters();
		
		setContentView(R.layout.list);

		// add tempery data for demostration purposes

		for (Requester t : requesters) {
			String name = getContactDetails(t.getPhoneNo());
			if(name.equalsIgnoreCase("")){
				name= t.getName() ;
			}
			requesterDetails.add(name+ "  " + t.getPhoneNo());
			requesterNames.add(t.getName());
		}

		// GlobalData appState = ((GlobalData) getApplicationContext());
		// String state = appState.getState();
		// Toast.makeText(getApplicationContext(), state, Toast.LENGTH_SHORT)
		// .show();
		//
		// appState.setState("STATE OFF");
		// state = appState.getState();
		// Toast.makeText(getApplicationContext(), state, Toast.LENGTH_SHORT)
		// .show();

		// ///////////////////////////////////////

		// set list xml class as the gui
		

		// create array adapter to manage the list
		ArrayAdapter<String> list = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, requesterDetails);
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
//				Toast.makeText(getApplicationContext(), strText,
//						Toast.LENGTH_SHORT).show();
				for (int i = 0; i < requesterDetails.size(); i++) {
					if (strText.equalsIgnoreCase(requesterDetails.get(i))) {
						Toast.makeText(getApplicationContext(), strText,
								Toast.LENGTH_SHORT).show();
						try {
							
							Intent intent = new Intent(RecievedRequestActivity.this,RequestManager.class);
							intent.putExtra("requester", requesters.get(i));										
							startActivity(intent);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}

				}
			}

		});

	}
	private String getContactDetails(String phoneNo) {
		Uri myContacts = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;// People.CONTENT_URI;
		Cursor mqCur = managedQuery(myContacts, null,
				null, null, null);
		if (mqCur.moveToFirst()) {
			String contactName = null;
			String contactNumber = null;
			do {
				contactName = mqCur
						.getString(mqCur
								.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
				contactNumber = mqCur
						.getString(mqCur
								.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
				// Toast.makeText(this, myname + " " + mynumber,
				// Toast.LENGTH_SHORT).show();
				if (contactNumber.equalsIgnoreCase(phoneNo)) {
					return contactName;
				}

			} while (mqCur.moveToNext());
		}
		return "";
	}
	
}
