package com.example.taweesoft.joinpa.CreateEvent.NewEvent;

import com.example.taweesoft.joinpa.Library.Owner;
import com.example.taweesoft.joinpa.MainActivity;

import java.io.Serializable;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by taweesoft on 29/4/2558.
 */
public class NewEventModel implements Serializable,Observer{
    private Date date = null;
    private int iconID = 0;
    private Owner owner = MainActivity.owner;
    private String eventName;
    private String note;
    public void update(Observable observable, Object obj){
        if(obj == null) return;

    }
}
