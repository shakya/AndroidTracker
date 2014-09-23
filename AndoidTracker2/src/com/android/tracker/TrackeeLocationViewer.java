package com.android.tracker;

import com.android.data.GlobalData;
import com.android.data.Trackee;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TrackeeLocationViewer extends Activity {
	Double lat = 0.0;
	Double lon = 0.0;
	Double altitiude = 0.0;
	Double direction = 0.0;
	Float accuracy = (float) 0.0;
	Trackee trackee;

	GlobalData globalData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trackeelocationviewer);

		TextView tvlat = (TextView) findViewById(R.id.tlat);
		TextView tvlon = (TextView) findViewById(R.id.tlon);
		TextView tvalt = (TextView) findViewById(R.id.talt);
		TextView tvdir = (TextView) findViewById(R.id.tdir);
		Button viewButton = (Button) findViewById(R.id.tbutton1);
		Button removeButton = (Button) findViewById(R.id.tbutton2);

		globalData = ((GlobalData) getApplicationContext());

		// creating variables to keep data

		try {
			String recievedStr = "";

			Intent i = getIntent();
			trackee = (Trackee) i.getSerializableExtra("trackee");

			// if other users location is requested
			// if (recievedStr.contains("@")) {
			// String temp[] = new String[2];
			// temp = recievedStr.split("@");
			// tvlat.setText(temp[0]);
			// tvlon.setText(temp[1]);
			// }

			if (trackee != null) {
				lat = trackee.getLat();
				lon = trackee.getLon();
				altitiude = trackee.getAlt();
				direction = trackee.getDir();
			}

			// if own location is requested
			else {
				Toast.makeText(getApplicationContext(),
						"Wrong coordinates recieved", Toast.LENGTH_SHORT)
						.show();
				// lat = recentLoc.getLatitude();
				// lon = recentLoc.getLongitude();
				// altitiude=recentLoc.getAltitude();
				// direction = recentLoc.getBearing();
				// accuracy = recentLoc.getAccuracy();
				// tvlon.setText(lon.toString());
				// tvlat.setText(lat.toString());
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		// setting the content for the text views
		tvlon.setText(lon.toString());
		tvlat.setText(lat.toString());
		tvalt.setText(altitiude.toString());
		tvdir.setText(direction.toString());

		viewButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewLocation();

			}
		});

		removeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				removeTrackee(trackee);

			}
		});
	}

	private void viewLocation() {
		String geoURI = String.format("geo:%f,%f?z=13", lat, lon);
		Uri geo = Uri.parse(geoURI);
		Intent geoMap = new Intent(Intent.ACTION_VIEW, geo);
		startActivity(geoMap);
	}

	private void removeTrackee(Trackee trackee) {
		globalData.removeTrackee(trackee);
		try {
			Intent intent2 = new Intent(TrackeeLocationViewer.this,
					CurrentTrackeeActivity.class);
			startActivity(intent2);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
