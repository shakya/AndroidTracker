package com.android.data;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

// trackee data class
public class Trackee implements Serializable{
	private String name="";
	private String phoneNo="";
	private Double lat=0.0;
	private Double lon=0.0;
	private Double dir=0.0;
	private Double alt=0.0;
	
	
// constructor to create a trackee when data is recieved.
	public Trackee(String s){
	//yet to impliment	
	}
	
// constructor to create a trackee. used temperarily
	public Trackee(String name ,String phoneNo,Double lat,	Double lon,	Double dir,	Double alt){
		this.name=name;
		this.phoneNo=phoneNo;
		this.lat=lat;
		this.lon=lon;
		this.alt=alt;
		this.dir=dir;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	
	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Double getDir() {
		return dir;
	}

	public void setDir(Double dir) {
		this.dir = dir;
	}

	public Double getAlt() {
		return alt;
	}

	public void setAlt(Double alt) {
		this.alt = alt;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	
}
