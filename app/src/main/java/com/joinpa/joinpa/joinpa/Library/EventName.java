package com.joinpa.joinpa.joinpa.Library;

/**
 * Event name object for initialize spinner in create new event.
 * Created on 10/5/2558.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class EventName {
    private String eventName;
    private int iconID;

    public EventName(String eventName, int iconID){
        this.eventName = eventName;
        this.iconID = iconID;
    }

    public String getEventName(){
        return eventName;
    }

    public int getIconID(){
        return iconID;
    }
}
