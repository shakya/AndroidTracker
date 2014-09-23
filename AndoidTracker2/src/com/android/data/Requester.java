package com.android.data;

import java.io.Serializable;

public class Requester implements Serializable {
	
	private String phoneNo="";
	private String name="";
	
	public Requester(String name,String phoneNo ){
		this.name= name;
		this.phoneNo=phoneNo;		
	}
	
	public Requester(String phoneNo){
		this.phoneNo=phoneNo;
		// check the contacts and add the name
		
	}
	
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
