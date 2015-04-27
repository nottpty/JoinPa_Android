package com.example.taweesoft.joinpa;

import java.io.Serializable;
import java.util.List;


public class Owner extends User{
	private List<Event> eventList;
	private List<Friend> friendList;
	public Owner(String username,String notifyKey){
		super(username,notifyKey);
		eventList = Database.getMyEvent(this);
		friendList = Database.getFriendList(this);
	}
	
	public List<Friend> getFriendList(){
		return friendList;
	}

	public List<Event> getEventList() {
		return eventList;
	}
	
	public void addEvent(Event e){
		eventList.add(e);
	}
	
}
