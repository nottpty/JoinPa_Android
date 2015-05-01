package com.example.taweesoft.joinpa.CreateEvent.NewEvent;

import android.util.Log;

import com.example.taweesoft.joinpa.Library.Owner;
import com.example.taweesoft.joinpa.MainActivity;

import java.io.Serializable;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by taweesoft on 29/4/2558.
 */
public class NewEventModel extends Observable implements Serializable,Observer{
    private Date date = null;
    private int iconID = 0;
    private Owner owner = MainActivity.owner;
    private String eventName;
    private String note;

    public NewEventModel(){
        date = new Date();
        date.setMonth(date.getMonth()+1);
        date.setYear(date.getYear()+1900);

    }

    public void setDate(int[] dateArr){
        int day = dateArr[0];
        int month = dateArr[1]+1;
        int year = dateArr[2];
        date.setDate(day);
        date.setMonth(month);
        date.setYear(year);
    }

    public void setTime(int[] timeArr){
        int hour = timeArr[0];
        int minute = timeArr[1];
        date.setHours(hour);
        date.setMinutes(minute);
    }

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

    public Date getDateAndTime(){
        return date;
    }
}
