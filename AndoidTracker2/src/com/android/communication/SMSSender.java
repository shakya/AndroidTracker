package com.android.communication;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.telephony.gsm.SmsManager;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Toast;
/*
 * this class manages the sms sending 
 * */
public class SMSSender {
	public void sendSMS(String no, String msg) {
		SmsManager sm = SmsManager.getDefault();

		// HERE IS WHERE THE DESTINATION OF THE TEXT SHOULD GO

		sm.sendTextMessage("5556", null, msg, null, null);
	}

	
}
