package com.example.taweesoft.joinpa.Library;

import java.util.List;


public class Owner extends User{
	private List<Event> eventList;
	private List<Friend> friendList;
    private List<JoiningEvent> joiningEvents;
	public Owner(String username,String notifyKey){
		super(username,notifyKey);
		eventList = Database.getMyEvent(this);
		friendList = Database.getFriendList(this);
        joiningEvents = Database.myJoiningEvents(this);
	}
	
	public List<Friend> getFriendList(){
		return friendList;
	}

	public List<Event> getEventList() {
		return eventList;
	}

    public List<JoiningEvent> getJoiningEvents() { return joiningEvents; }
	
	public void addEvent(Event e){
		eventList.add(e);
	}
	
}
