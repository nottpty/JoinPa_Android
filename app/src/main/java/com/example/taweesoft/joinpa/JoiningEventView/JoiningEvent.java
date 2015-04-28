package com.example.taweesoft.joinpa.JoiningEventView;

import com.example.taweesoft.joinpa.Library.Event;
import com.example.taweesoft.joinpa.Library.Friend;

import java.util.Date;
import java.util.Map;

/**
 * Created by taweesoft on 27/4/2558.
 */
public class JoiningEvent extends Event {
    String ownerName;
    public JoiningEvent(String eventID,String ownerName,Map<Friend,Integer> invitedList,int iconID,String topic,String note,Date date){
        super(eventID,invitedList,iconID,topic,note,date);
        this.ownerName = ownerName;
    }

    public String getOwnerName(){
        return this.ownerName;
    }
}
