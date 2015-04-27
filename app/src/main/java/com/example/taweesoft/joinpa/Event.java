package com.example.taweesoft.joinpa;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;


public class Event implements Serializable{
	private Owner owner;
	private Map<Friend,Integer> invitedList;
	private int iconID;
	private String topic;
	private String note;
	private Date date;
	
	public Event(Owner owner,Map<Friend,Integer> invitedList,int iconID,String topic,String note,Date date){
		this.owner = owner;
		this.invitedList = invitedList;
		this.iconID = iconID;
		this.topic = topic;
		this.note = note;
		this.date = date;
	}

    public Event(Map<Friend,Integer> invitedList,int iconID,String topic,String note,Date date){
        this.invitedList = invitedList;
        this.iconID = iconID;
        this.topic = topic;
        this.note = note;
        this.date = date;
    }

	public Map<Friend,Integer> getInvitedList(){
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

	public void setInvitedList(Map<Friend, Integer> invitedList) {
		this.invitedList = invitedList;
	}

	
}
