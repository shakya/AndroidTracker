package com.android.tracker;

import com.android.data.GlobalData;

import android.app.Activity;
/*
 * */
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LocationViewr extends Activity {
	LocationManager locMgr;
	GlobalData globalData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locationviewer);
		locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
		globalData = ((GlobalData) getApplicationContext());
		
		//creating a location manager
		Location recentLoc = locMgr	.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		//getting references to text views
		TextView tvlat=(TextView)findViewById(R.id.lat);
		TextView tvlon=(TextView) findViewById(R.id.lon);
		TextView tvalt=(TextView)findViewById(R.id.alt);
		TextView tvdir=(TextView)findViewById(R.id.dir);
		Button button=(Button) findViewById(R.id.button1);
		
		//creating variables to keep data
    	Double lat=0.0;
    	Double lon=0.0;
    	Double altitiude=0.0;
		Float direction = (float) 0.0;
		Float accuracy = (float) 0.0;
		
		try{
		String recievedStr="";
		recievedStr=getIntent().getExtras().getString("location");
    	
		//if other users location is requested
		if(recievedStr.contains("@")){
			String temp[]= new String[2];
			temp=recievedStr.split("@");
			tvlat.setText(temp[0]);
			tvlon.setText(temp[1]);
		}
		
		//if own location is requested
		else{
			lat = recentLoc.getLatitude();
			lon = recentLoc.getLongitude();
			altitiude=recentLoc.getAltitude();
			direction = recentLoc.getBearing();
			accuracy = recentLoc.getAccuracy();
			tvlon.setText(lon.toString());
			tvlat.setText(lat.toString());
		}
		
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		//setting the content for the text views
		
		tvalt.setText(altitiude.toString());
		tvdir.setText(direction.toString());
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMyLocation();
				
			}
		});
	}
	
	
	
	//method to view location in the map
	 /**
	 * show my location to the users
	 */
	public void showMyLocation(){
		 Location recentLoc = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	    	Double lat=0.0;
	    	Double lon=0.0;
			lat = recentLoc.getLatitude();
			lon = recentLoc.getLongitude();
			
			//setting the uri to initiate the map
			//geo:latitude,longitude?z=zoom (z =1-23)
			String geoURI = String.format("geo:%f,%f?z=13", lat, lon);
			Uri geo = Uri.parse(geoURI);
			Intent geoMap = new Intent(Intent.ACTION_VIEW, geo);
			startActivity(geoMap);
	    }
}
