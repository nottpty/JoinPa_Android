package com.example.taweesoft.joinpa.Library;

import java.util.ArrayList;
import java.util.List;


public class Owner extends User{
	private List<Event> eventList;
	private List<Friend> friendList;
    private List<JoiningEvent> joiningEvents;
    private List<JoiningEvent> joinedEvent;
    private LoadMyEvent loadMyEvent;
	public Owner(String username,String notifyKey){
		super(username,notifyKey);
        eventList = new ArrayList<Event>();
        joinedEvent = new ArrayList<JoiningEvent>();
		loadMyEvent = new LoadMyEvent(this);
        loadMyEvent();
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
		eventList.add(0,e);
	}

    public void addFriend(Friend friend) { friendList.add(0,friend); }

    public void addJoinedEvent(JoiningEvent event) { joinedEvent.add(0,event); }

    public void moveToJoined(JoiningEvent event){
        joiningEvents.remove(event);
        joinedEvent.add(0,event);
    }

    public void setEventList(List<Event> eventList){
        this.eventList = eventList;
    }

    public boolean isLoadFinish(){
        return loadMyEvent.isDone();
    }

    public void loadMyEvent(){
        loadMyEvent.load();
    }
}
