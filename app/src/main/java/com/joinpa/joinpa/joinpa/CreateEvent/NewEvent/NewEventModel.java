package com.joinpa.joinpa.joinpa.CreateEvent.NewEvent;

import com.joinpa.joinpa.joinpa.Library.Owner;
import com.joinpa.joinpa.joinpa.Library.Resources;

import java.io.Serializable;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 * New event model.
 * Created on 29/4/2558.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class NewEventModel extends Observable implements Serializable,Observer{
    private Date date = null;
    private int iconID = 0;
    private Owner owner = Resources.owner;
    private String eventName;
    private String note;

    /**
     * Constructor. set initial date to current.
     */
    public NewEventModel(){
        date = new Date();
        date.setMonth(date.getMonth()+1);
        date.setYear(date.getYear()+1900);

    }

    /**
     * Set date with new data.
     * @param dateArr
     */
    public void setDate(int[] dateArr){
        int day = dateArr[0];
        int month = dateArr[1]+1;
        int year = dateArr[2];
        date.setDate(day);
        date.setMonth(month);
        date.setYear(year);
    }

    /**
     * Set time with new data.
     * @param timeArr
     */
    public void setTime(int[] timeArr){
        int hour = timeArr[0];
        int minute = timeArr[1];
        date.setHours(hour);
        date.setMinutes(minute);
    }

    /**
     * Update method of observer. called from DatePicker and TimePicker.
     * @param observable
     * @param obj
     */
    public void update(Observable observable, Object obj){
        if(obj == null) return;
        int[] dataArr = (int[])obj;
        if(dataArr.length==3){ // Date
            setDate(dataArr);
        }else if(dataArr.length == 2){ // Time
            setTime(dataArr);
        }

        setChanged();
        notifyObservers(date);
    }

    /**
     * Get date and time.
     * @return
     */
    public Date getDateAndTime(){
        return date;
    }
}
