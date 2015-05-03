package com.example.taweesoft.joinpa.Notification;

import android.os.AsyncTask;

import com.example.taweesoft.joinpa.Library.Database;
import com.example.taweesoft.joinpa.Library.JoiningEvent;
import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.MainActivity;

/**
 * Created by taweesoft on 2/5/2558.
 */
public class NotifyOwner extends AsyncTask<Void,Void,Void> {

    private JoiningEvent event;
    public NotifyOwner(JoiningEvent event){
        this.event = event;
    }

    @Override
    protected Void doInBackground(Void... params) {
        String ownerNotiKey = event.getEventOwner().getNotifyKey();
        String msg = String.format("\'%s\' %s has joined your event",event.getTopic(), Resources.owner.getUsername());
        Database.sendMsg(ownerNotiKey, msg);
        return null;
    }
}
