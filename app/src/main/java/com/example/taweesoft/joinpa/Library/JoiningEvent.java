package com.example.taweesoft.joinpa.Library;

import android.util.Log;

import com.example.taweesoft.joinpa.Library.Event;
import com.example.taweesoft.joinpa.Library.Friend;
import com.example.taweesoft.joinpa.MainActivity;

import java.util.Date;
import java.util.Map;

/**
 * Created by taweesoft on 27/4/2558.
 */
public class JoiningEvent extends Event {
    private User owner;
    public JoiningEvent(String eventID,User owner,Map<User,Integer> invitedList,int iconID,String topic,String note,Date date){
        super(eventID,invitedList,iconID,topic,note,date);
        this.owner = owner;
    }

    public User getEventOwner(){
        return this.owner;
    }

    public void setStatus(int status){
        Map<User,Integer> invitedList = getInvitedList();
        invitedList.put(Resources.owner,status);
    }

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
