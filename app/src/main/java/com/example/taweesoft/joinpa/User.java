package com.example.taweesoft.joinpa;

import java.io.Serializable;

public class User implements Serializable{
	private String username;
	private String notifyKey;
	public User(String username,String notifyKey){
		this.username = username;
		this.notifyKey = notifyKey;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getNotifyKey(){
		return notifyKey;
	}
}
