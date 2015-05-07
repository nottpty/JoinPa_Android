package com.joinpa.taweesoft.joinpa.Library;

import java.io.Serializable;

/**
 * User class.
 */
public class User implements Serializable{
	private String username;
	private String notifyKey;

    /**
     * Constructor.
     * @param username
     * @param notifyKey
     */
	public User(String username,String notifyKey){
		this.username = username;
		this.notifyKey = notifyKey;
	}

    /**
     * Get username.
     * @return
     */
	public String getUsername(){
		return username;
	}

    /**
     * Get notification key.
     * @return
     */
	public String getNotifyKey(){
		return notifyKey;
	}

    /**
     * Check if user has same username.
     * @param obj
     * @return
     */
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(!(obj instanceof User)) return false;
        User friend = (User)obj;
        return getUsername().equals(friend.getUsername());
    }
}
