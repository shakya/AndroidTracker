package com.android.tracker;

import com.android.data.GlobalData;
import com.android.data.Requester;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ManageTracker extends Activity {
	String name ="";
	String phoneNo="";
	Requester tracker;
	GlobalData globalData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.requestmanager);
		
		globalData = ((GlobalData) getApplicationContext());
		
		Intent i = getIntent();
		tracker = (Requester) i.getSerializableExtra("tracker");
		
		TextView name = (TextView) findViewById(R.id.reqName);
		TextView phoneNo = (TextView) findViewById(R.id.reqPhnNo);
		Button acceptButton = (Button) findViewById(R.id.acceptRequestBtn);
		Button denyButton = (Button) findViewById(R.id.cancelReqBtn);
		
		acceptButton.setVisibility(View.GONE);
		denyButton.setText("remove");

		name.setText(tracker.getName());
		phoneNo.setText(tracker.getPhoneNo());
		
		
		
		denyButton.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				removeTracker(tracker);
				
			}
		});
		
		
	}
	private void removeTracker(Requester tracker) {
		globalData.removeTracker(tracker);
		Intent intent2 = new Intent(ManageTracker.this,CurrentTracekrList.class);
		startActivity(intent2);
	}

}
