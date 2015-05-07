package com.example.taweesoft.joinpa.Library;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Onwer class.
 */

public class Owner extends User{
	private List<Event> eventList;
	private List<Friend> friendList;
    private List<JoiningEvent> joiningEvents;
    private List<JoiningEvent> joinedEvent;
    private List<Friend> friendRequest;
    private LoadData loadMyEvent,loadMyJoinedEvent;

    /**
     * Constructor.
     * @param username
     * @param notifyKey
     */
	public Owner(String username,String notifyKey){
		super(username,notifyKey);
        eventList = new ArrayList<Event>();
        joinedEvent = new ArrayList<JoiningEvent>();
		loadMyEvent = new LoadMyEvent(this);
        loadMyJoinedEvent = new LoadMyJoinedEvent(this);

        /*Load data in background.*/
        loadData(loadMyEvent);
        loadData(loadMyJoinedEvent);

        /*Get important data.*/
		friendList = Database.getFriendList(this);
        joiningEvents = Database.myJoiningEvents(this,Resources.WAITING);
        friendRequest = Database.getFriendRequest(this);
	}

    /**
     * Get friend list.
     * @return
     */
	public List<Friend> getFriendList(){
		return friendList;
	}

    /**
     * Get event list.
     * @return
     */
	public List<Event> getEventList() {
		return eventList;
	}

    /**
     * Get joining event list.
     * @return
     */
    public List<JoiningEvent> getJoiningEvents() { return joiningEvents; }

    /**
     * Add event.
     * @param e
     */
	public void addEvent(Event e){
		eventList.add(0,e);
	}

    /**
     * Add friend.
     * @param friend
     */
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

    /**
     * Add joined event.
     * @param event
     */
    public void addJoinedEvent(JoiningEvent event) { joinedEvent.add(0,event); }

    /**
     * Move joining event to joined list.
     * @param event
     */
    public void moveToJoined(JoiningEvent event){
        joiningEvents.remove(event);
        joinedEvent.add(0,event);
    }

    /**
     * Set event list.
     * @param eventList
     */
    public void setEventList(List<Event> eventList){
        this.eventList = eventList;
    }

    /**
     * Check for load my event is finished.
     * @return
     */
    public boolean isLoadMyEventFinish(){
        return loadMyEvent.isFinished();
    }

    /**
     * Check for load joined event is finished.
     * @return
     */
    public boolean isLoadMyJoinedFinish(){
        return loadMyJoinedEvent.isFinished();
    }

    /**
     * Load data.
     * @param loadData
     */
    public void loadData(LoadData loadData){
        loadData.load();
    }

    /**
     * Get joined event list.
     * @return
     */
    public List<JoiningEvent> getJoinedEvent(){
        return joinedEvent;
    }

    /**
     * Set joined event list.
     * @param joinedEvent
     */
    public void setJoinedEvent(List<JoiningEvent> joinedEvent){
        this.joinedEvent = joinedEvent;
    }

    /**
     * Set joining event list.
     * @param joiningEvent
     */
    public void setJoiningEvents(List<JoiningEvent> joiningEvent){
        this.joiningEvents.clear();
        this.joiningEvents.addAll(joiningEvent);
    }

    /**
     * Set friend request list.
     * @param friendRequest
     */
    public void setFriendRequest(List<Friend> friendRequest){
        this.friendRequest.clear();
        this.friendRequest.addAll(friendRequest);
    }

    /**
     * Get friend request.
     * @return
     */
    public List<Friend> getFriendRequest(){
        return friendRequest;
    }

    /**
     * Accept friend from request.
     * @param friend
     */
    public void acceptFriend(final Friend friend){
        friendRequest.remove(friend);
        addFriend(friend);
        AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Database.acceptFriend(friend);
                return null;
            }
        };
        task.execute();
    }
}
