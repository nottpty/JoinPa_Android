package com.example.taweesoft.joinpa.Library;

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

    public boolean equals(Object obj){
        if(obj == null) return false;
        if(!(obj instanceof User)) return false;
        User friend = (User)obj;
        return getUsername().equals(friend.getUsername());
    }
}
