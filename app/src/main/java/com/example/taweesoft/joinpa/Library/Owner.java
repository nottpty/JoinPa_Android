package com.example.taweesoft.joinpa.Library;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class Owner extends User{
	private List<Event> eventList;
	private List<Friend> friendList;
    private List<JoiningEvent> joiningEvents;
    private List<JoiningEvent> joinedEvent;
    private List<Friend> friendWaitingList;
    private LoadData loadMyEvent,loadMyJoinedEvent;
	public Owner(String username,String notifyKey){
		super(username,notifyKey);
        eventList = new ArrayList<Event>();
        joinedEvent = new ArrayList<JoiningEvent>();
		loadMyEvent = new LoadMyEvent(this);
        loadMyJoinedEvent = new LoadMyJoinedEvent(this);
        loadData(loadMyEvent);
        loadData(loadMyJoinedEvent);
		friendList = Database.getFriendList(this);
        joiningEvents = Database.myJoiningEvents(this,Resources.WAITING);
        friendWaitingList = Database.getWaitingFriendList(this);
        Log.d("YYYYYYYY : ", friendWaitingList.size()+"");
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

    public void addFriend(final Friend friend) {
        friendList.add(0,friend);
        AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Database.addFriend(friend);
                return null;
            }
        };
        task.execute();
    }

    public void addJoinedEvent(JoiningEvent event) { joinedEvent.add(0,event); }

    public void moveToJoined(JoiningEvent event){
        joiningEvents.remove(event);
        joinedEvent.add(0,event);
    }

    public void setEventList(List<Event> eventList){
        this.eventList = eventList;
    }

    public boolean isLoadMyEventFinish(){
        return loadMyEvent.isFinished();
    }

    public boolean isLoadMyJoinedFinish(){
        return loadMyJoinedEvent.isFinished();
    }

    public void loadData(LoadData loadData){
        loadData.load();
    }
    public List<JoiningEvent> getJoinedEvent(){
        return joinedEvent;
    }

    public void setJoinedEvent(List<JoiningEvent> joinedEvent){
        this.joinedEvent = joinedEvent;
    }

    public void setJoiningEvents(List<JoiningEvent> joiningEvent){
        this.joiningEvents.clear();
        this.joiningEvents.addAll(joiningEvent);
    }

    public List<Friend> getFriendWaitingList(){
        return friendWaitingList;
    }
}
