package com.android.tracker;

import com.android.data.GlobalData;
import com.android.data.Requester;
import com.android.data.Trackee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RequestManager extends Activity {
	String name ="";
	String phoneNo="";
	Requester requester;
	GlobalData globalData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.requestmanager);
		
		globalData = ((GlobalData) getApplicationContext());
		
		Intent i = getIntent();
		requester = (Requester) i.getSerializableExtra("requester");
		
		TextView name = (TextView) findViewById(R.id.reqName);
		TextView phoneNo = (TextView) findViewById(R.id.reqPhnNo);
		Button acceptButton = (Button) findViewById(R.id.acceptRequestBtn);
		Button denyButton = (Button) findViewById(R.id.cancelReqBtn);
		
		acceptButton.setVisibility(View.VISIBLE);
		denyButton.setText("Deny");

		name.setText(requester.getName());
		phoneNo.setText(requester.getPhoneNo());
		
		acceptButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				acceptRequester(requester);				
			}
		});
		
		denyButton.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				removeRequester(requester);
				
			}
		});
		
		
	}
	private void removeRequester(Requester requester) {
		globalData.removeRequester(requester);
		Intent intent2 = new Intent(RequestManager.this,RecievedRequestActivity.class);
		startActivity(intent2);
	}

	private void acceptRequester(Requester requester) {
		globalData.addTracker(requester);
		removeRequester(requester);
	}

}
