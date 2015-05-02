package com.example.taweesoft.joinpa.Library;

import java.util.ArrayList;
import java.util.List;


public class Owner extends User{
	private List<Event> eventList;
	private List<Friend> friendList;
    private List<JoiningEvent> joiningEvents;
    private List<JoiningEvent> joinedEvent;
	public Owner(String username,String notifyKey){
		super(username,notifyKey);
        joinedEvent = new ArrayList<JoiningEvent>();
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

    public void addFriend(Friend friend) { friendList.add(friend); }

    public void addJoinedEvent(JoiningEvent event) { joinedEvent.add(event); }

    public void moveToJoined(JoiningEvent event){
        joiningEvents.remove(event);
        joinedEvent.add(event);
    }
}
