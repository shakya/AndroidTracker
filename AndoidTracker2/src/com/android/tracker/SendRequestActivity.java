package com.android.tracker;

import com.android.communication.SMSSender;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendRequestActivity extends Activity {
	protected static final View View = null;

	SMSSender smsSender;

	String OwnName = "";
	String phoneNo = "";
	String requesterName = "";

	private EditText etMyName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendrequest);

		final EditText etName = (EditText) findViewById(R.id.editText1);
		final EditText etPhoneNo = (EditText) findViewById(R.id.editText2);
		Button sendReqButton = (Button) findViewById(R.id.button1);
		Button searchContactsButton = (Button) findViewById(R.id.button2);
		final TextView tvReqName = (TextView) findViewById(R.id.textView3);

		etMyName = etName;

		SharedPreferences settings = getSharedPreferences("MYPREFS", 0);
		etName.setText(settings.getString("myName", ""));

		sendReqButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OwnName = etName.getText().toString();
				phoneNo = etPhoneNo.getText().toString();
				sendRequest();

			}
		});

		searchContactsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// getContactDetails();
				doLaunchContactPicker(v);
				etPhoneNo.setText(phoneNo);
				Toast.makeText(getApplicationContext(), phoneNo,
						Toast.LENGTH_SHORT).show();
				tvReqName.setText(requesterName);
				Toast.makeText(getApplicationContext(), requesterName,
						Toast.LENGTH_SHORT).show();

			}
		});

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		SharedPreferences settings = getSharedPreferences("MYPREFS", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("myName", etMyName.getText().toString());
		editor.commit();
	}

	public void sendRequest() {
		if (!phoneNo.equalsIgnoreCase("")) {
			String msg = "ATrckrRequst#"  + OwnName;
			SMSSender smsSender = new SMSSender();
			smsSender.sendSMS(phoneNo, msg);
			
//			SmsManager sm = SmsManager.getDefault();
//
//			// HERE IS WHERE THE DESTINATION OF THE TEXT SHOULD GO
//
//			sm.sendTextMessage(phoneNo, null, msg, null, null);
			
			String sender = "";
			if (requesterName.equalsIgnoreCase("")) {
				sender = phoneNo;
			} else {
				sender = requesterName;
			}
			Toast.makeText(getApplicationContext(),
					"Request Sent to " + sender, Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(), "Invalid Phone Number",
					Toast.LENGTH_SHORT).show();
		}

	}

	private String getMyPhoneNumber() {
		TelephonyManager mTelephonyMgr;
		mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getLine1Number();
	}

	public void doLaunchContactPicker(View view) {
		Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
				Contacts.CONTENT_URI);
		startActivityForResult(contactPickerIntent, 1001);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// This method will identify which buddy is altered and save the changes
		// and updates the UI
		// This will run when a user selected a contact from the contacts list
		if (resultCode == RESULT_OK) {
			Uri result = data.getData();
			String id = result.getLastPathSegment();
			String email = null;
			String phone = null;
			String name = null;
			// Quering emails and phone numbers from the result
			Cursor cursor = getContentResolver().query(Email.CONTENT_URI, null,
					Email.CONTACT_ID + "=?", new String[] { id }, null);
			if (cursor.moveToFirst()) {
				int emailIdx = cursor.getColumnIndex(Email.DATA);
				email = cursor.getString(emailIdx);
			}
			cursor = getContentResolver().query(Phone.CONTENT_URI, null,
					Phone.CONTACT_ID + "=?", new String[] { id }, null);
			if (cursor.moveToFirst()) {
				int phoneIdx = cursor.getColumnIndex(Phone.DATA);
				phone = cursor.getString(phoneIdx);
				int nameIdx = cursor.getColumnIndex(Phone.DISPLAY_NAME);
				name = cursor.getString(nameIdx);
			}
			phoneNo = phone;
			requesterName = name;
			Toast.makeText(this, requesterName + " " + phoneNo, Toast.LENGTH_SHORT).show();
		}
	}
}
