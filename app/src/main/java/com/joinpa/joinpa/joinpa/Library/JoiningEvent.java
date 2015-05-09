package com.joinpa.joinpa.joinpa.Library;

import android.util.Log;

import java.util.Date;
import java.util.Map;

/**
 * Joining event extends from Event.
 * Created on 27/4/2558.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class JoiningEvent extends Event {
    private User owner;

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
    public JoiningEvent(String eventID,User owner,Map<User,Integer> invitedList,int iconID,String topic,String note,Date date){
        super(eventID,invitedList,iconID,topic,note,date);
        this.owner = owner;
    }

    /**
     * Get event's owner.
     * @return
     */
    public User getEventOwner(){
        return this.owner;
    }

    /**
     * Set owner status of this event
     * @param status
     */
    public void setStatus(int status){
        Map<User,Integer> invitedList = getInvitedList();
        for(Map.Entry<User,Integer> each : invitedList.entrySet()){
            if(each.getKey().getUsername().equals(Resources.owner.getUsername())){
                invitedList.remove(each.getKey());
                invitedList.put(Resources.owner,status);
                return;
            }
        }
    }

    /**
     * Get my status
     * @return
     */
    public int getMyStatus(){
        Map<User,Integer> invitedList = getInvitedList();
        Log.d("OOO : " , invitedList.size() + "");
        for(Map.Entry<User, Integer> each : invitedList.entrySet()){
            if(each.getKey().equals(Resources.owner))
                return each.getValue();
        }
        return -1;
    }
}
