package com.example.taweesoft.joinpa.Library;

import android.util.Log;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Event implements Serializable{
	private Owner owner;
	private Map<User,Integer> invitedList;
	private int iconID;
	private String topic;
	private String note;
	private Date date;
    private String eventID;
	
	public Event(String eventID,Owner owner,Map<User,Integer> invitedList,int iconID,String topic,String note,Date date){
		this.eventID = eventID;
        this.owner = owner;
		this.invitedList = invitedList;
		this.iconID = iconID;
		this.topic = topic;
		this.note = note;
		this.date = date;
	}

    public Event(String eventID,Map<User,Integer> invitedList,int iconID,String topic,String note,Date date){
        this.eventID = eventID;
        this.invitedList = invitedList;
        this.iconID = iconID;
        this.topic = topic;
        this.note = note;
        this.date = date;
    }

	public Map<User,Integer> getInvitedList(){
		return invitedList;
	}
	
	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public int getIconID() {
		return iconID;
	}

	public void setIconID(int iconID) {
		this.iconID = iconID;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setInvitedList(Map<User, Integer> invitedList) {
		this.invitedList = invitedList;
	}

	public String getEventID(){ return eventID; };

    public static Map<User,Integer> createInvitedMap(List<Friend> invitedList){
        Map<User,Integer> invitedListMap = new HashMap<User,Integer>();
        for(User friend : invitedList){
            invitedListMap.put(friend, 0);
        }
        return invitedListMap;
    }

    public boolean equals(Object obj){
        if( obj == null ) return false;
        if( !(obj instanceof Event) ) return false;
        Event event = (JoiningEvent)obj;
        return getEventID().equals(event.getEventID());
    }
}
