package com.joinpa.joinpa.joinpa.Library;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Onwer class.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */

public class Owner extends User{
	private List<Event> eventList;
	private List<Friend> friendList;
    private List<JoiningEvent> joiningEvents;
    private List<JoiningEvent> joinedEvent;
    private List<Friend> friendRequest;
    private LoadData loadMyEvent,loadMyJoinedEvent,loadFriendList;

    /**
     * Constructor.
     * @param username
     * @param notifyKey
     */
	public Owner(String username,String notifyKey){
		super(username,notifyKey);
        eventList = new ArrayList<Event>();
        joinedEvent = new ArrayList<JoiningEvent>();
        friendList = new ArrayList<Friend>();

		loadMyEvent = new LoadMyEvent(this);
        loadMyJoinedEvent = new LoadMyJoinedEvent(this);
        loadFriendList = new LoadFriendList(this);

        /*Load data in background.*/
        loadData(loadFriendList);
        loadData(loadMyEvent);
        loadData(loadMyJoinedEvent);
        /*Get important data.*/

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
        AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                friendRequest = Database.getFriendRequest(Owner.this);
                if(friendRequest.contains(friend))
                    acceptFriend(friend);
                else{
                    friendList.add(0,friend);
                    Database.addFriend(friend);
                }
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
     * Check for load friend list is finished.
     * @return
     */
    public boolean isLoadFriendListFinish() { return loadFriendList.isFinished(); }

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
        friendList.add(0, friend);
        AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Database.acceptFriend(friend);
                Database.addFriend(friend);
                return null;
            }
        };
        task.execute();
    }

    /**
     * Set friend list.
     */
    public void setFriendList(List<Friend> friendList){
        this.friendList = friendList;
    }
}
