package com.android.communication;

import java.util.ArrayList;

import com.android.data.GlobalData;
import com.android.data.Requester;
import com.android.data.Trackee;
import com.android.tracker.RecievedRequestActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/* 
 * This class listens to the system broadcast messages 
 * when message is received. 
 	*It reads the message
 	*identify the message (whether it is relevant to the Android Tracker)
 	*if it is not just notify the user
 	*if it is relevant 
 		* if the message is a request add the request to the global data so that user can accept or deny it
 		* if the message is a update the location of particular trackee	 */


public class SMSReceiveService extends BroadcastReceiver {
	//defining global data variables
	GlobalData globalData;
	Context myContext;
	Context newContext;

	private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public void onReceive(Context context, Intent intent) {
		
		//getting references to the global data
		globalData = ((GlobalData) context.getApplicationContext());
		myContext = context;
		
		if (intent.getAction().equals(SMS_RECEIVED)) { 					// check weather sms received
														

			Bundle bundle = intent.getExtras(); 						// get the additional information add with intent
												
			if (bundle != null && bundle.containsKey("pdus")) {
				Object[] pdusObj = (Object[]) bundle.get("pdus");		 // get the msg objects
																	
				SmsMessage[] messages = new SmsMessage[pdusObj.length];   // all the messages in the inbox are stored in the messages array

				// getting SMS information from Pdu
				for (int i = 0; i < pdusObj.length; i++) {
					messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
				}

				
				SmsMessage currentMessage = messages[0];  							//only take the last received message
				String phoneNo = currentMessage.getDisplayOriginatingAddress(); 	// get senders phone number								
				String messageBody = currentMessage.getDisplayMessageBody(); 		//get message body																																						
																				

				Toast.makeText(context, phoneNo + messageBody,
						Toast.LENGTH_SHORT).show(); 								// notify the message to the user

				if (messageBody.startsWith("ATrckrLocation")) {  					//if location data received 
					Toast.makeText(context, "location data recieved",
							Toast.LENGTH_SHORT).show(); 							//notify the user
					manageLocationData(messageBody, phoneNo); 						// call the method to manage the location data
				}
				
				if (messageBody.startsWith("ATrckrRequst")) { 						//if request received
					Toast.makeText(context, "request recieved ",
							Toast.LENGTH_SHORT).show(); 							//  notify the user
					manageRequest(messageBody, phoneNo); 							// call the method to manage the request

				}

			}
		}
	}

	/**
	 * This method split the request message and update the global data. returns a requester class of the newly received requester
	 * @param msg
	 * @param phoneNo
	 * @return
	 */
	public Requester manageRequest(String msg, String phoneNo) {
		try { 
			String data[] = msg.trim().split("#"); 						// split the message at #
			String name = data[1]; 										// get the name of the requester
			Requester requester = new Requester(name, phoneNo); 		// create the requester class for the new requester
			globalData.addRequester(requester);							// update global data with the requester
			return requester;
		}catch (Exception e) {
			// TODO: handle exception
			Log.e("SMSReciever", "Invalid request recieved");			//log the exception
		}
		return null;
	}

	public void manageLocationData(String msg, String phoneNo) {
		try{
		// lat + "#" + lon + "#" + alt + "#" + dir
		String location[] = msg.trim().split("#");  			//split the message to get the details
		Double lat = Double.parseDouble(location[1]); 			//parses the values in to variables
		Double lon = Double.parseDouble(location[2]);
		Double alt = Double.parseDouble(location[3]);
		Double dir = Double.parseDouble(location[4].trim());

		ArrayList<Trackee> trackees = globalData.getTrackees(); 		//get the trackees to th local variable trackees
		for (Trackee t : trackees) { 									// iterate through the trackee array 
			if (t.getPhoneNo().equalsIgnoreCase(phoneNo)) { 			// if the trackee for the received location is found
				t.setLat(lat);											// update the variables
				t.setLon(lon);
				t.setAlt(alt);
				t.setDir(dir);
			}
		}
	
	}catch (Exception e) {
		// TODO: handle exception
		Log.e("SMSReciever", "Invalid location recieved");				//log the exception
	}
	}

}
