package com.example.taweesoft.joinpa.Library;

import android.util.Log;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Event class.
 */
public class Event implements Serializable{
	private Owner owner;
	private Map<User,Integer> invitedList;
	private int iconID;
	private String topic;
	private String note;
	private Date date;
    private String eventID;
    private int joinedNumber,waitingNumber,declineNumber;

    /**
     * Constructor.
     * @param eventID
     * @param owner
     * @param invitedList
     * @param iconID
     * @param topic
     * @param note
     * @param date
     */
	public Event(String eventID,Owner owner,Map<User,Integer> invitedList,int iconID,String topic,String note,Date date){
		this.eventID = eventID;
        this.owner = owner;
		this.invitedList = invitedList;
		this.iconID = iconID;
		this.topic = topic;
		this.note = note;
		this.date = date;
        calculateNumber();
	}

    /**
     * Constructor.
     * @param eventID
     * @param invitedList
     * @param iconID
     * @param topic
     * @param note
     * @param date
     */
    public Event(String eventID,Map<User,Integer> invitedList,int iconID,String topic,String note,Date date){
        this.eventID = eventID;
        this.invitedList = invitedList;
        this.iconID = iconID;
        this.topic = topic;
        this.note = note;
        this.date = date;
        calculateNumber();
    }

    /**
     * Get invited list.
     * @return
     */
	public Map<User,Integer> getInvitedList(){
		return invitedList;
	}

    /**
     * Get owner of current event.
     * @return
     */
	public Owner getOwner() {
		return owner;
	}

    /**
     * Set owner.
     * @param owner
     */
	public void setOwner(Owner owner) {
		this.owner = owner;
	}

    /**
     * Get icon id.
     * @return
     */
	public int getIconID() {
		return iconID;
	}

    /**
     * Set icon id.
     * @param iconID
     */
	public void setIconID(int iconID) {
		this.iconID = iconID;
	}

    /**
     * Get topic.
     * @return
     */
	public String getTopic() {
		return topic;
	}

    /**
     * Set topic.
     * @param topic
     */
	public void setTopic(String topic) {
		this.topic = topic;
	}

    /**
     * Get note.
     * @return
     */
	public String getNote() {
		return note;
	}

    /**
     * Set note.
     * @param note
     */
	public void setNote(String note) {
		this.note = note;
	}

    /**
     * Get date.
     * @return
     */
	public Date getDate() {
		return date;
	}

    /**
     * Set date.
     * @param date
     */
	public void setDate(Date date) {
		this.date = date;
	}

    /**
     * Set invited list.
     * @param invitedList
     */
	public void setInvitedList(Map<User, Integer> invitedList) {
		this.invitedList = invitedList;
	}

    /**
     * Get event id.
     * @return
     */
	public String getEventID(){ return eventID; };

    /**
     * Create invited map from list.
     * @param invitedList
     * @return
     */
    public static Map<User,Integer> createInvitedMap(List<Friend> invitedList){
        Map<User,Integer> invitedListMap = new HashMap<User,Integer>();
        for(User friend : invitedList){
            invitedListMap.put(friend, 0);
        }
        return invitedListMap;
    }

    /**
     * equals(), check by event id.
     * @param obj
     * @return
     */
    public boolean equals(Object obj){
        if( obj == null ) return false;
        if( !(obj instanceof Event) ) return false;
        Event event = (JoiningEvent)obj;
        return getEventID().equals(event.getEventID());
    }

    /**
     * Get date in string format.
     * @return
     */
    public String getDateStr(){
        return String.format("%02d/%02d/%02d",date.getDate(),date.getMonth(),date.getYear());
    }

    /**
     * Get time in string format.
     * @return
     */
    public String getTimeStr(){
        return String.format("%02d:%02d",date.getHours(),date.getMinutes());
    }

    /**
     * Get number of user who joined event.
     * @return
     */
    public int getJoinedNumber(){
        return joinedNumber;
    }

    /**
     * Get number of user who waiting event.
     * @return
     */
    public int getWaitingNumber(){
        return waitingNumber;
    }

    /**
     * Get number of user who decline event.
     * @return
     */
    public int getDeclineNumber(){
        return declineNumber;
    }

    /**
     * Calculate the joined,waiting,decline number.
     */
    public void calculateNumber(){
        for(Map.Entry<User,Integer> each : invitedList.entrySet()){
            int status=each.getValue();
            switch(status){
                case 0:
                    waitingNumber++;
                    break;
                case 1:
                    joinedNumber++;
                    break;
                case 2:
                    declineNumber++;
                    break;
            }
        }
    }
}
