package com.joinpa.joinpa.joinpa.Notification;

import android.os.AsyncTask;

import com.joinpa.joinpa.joinpa.Library.Database;
import com.joinpa.joinpa.joinpa.Library.JoiningEvent;
import com.joinpa.joinpa.joinpa.Library.Resources;

/**
 * Notify event's owner.
 */
public class NotifyOwner extends AsyncTask<Void,Void,Void> {

    private JoiningEvent event;
    public NotifyOwner(JoiningEvent event){
        this.event = event;
    }

    /**
     * Notify with message.
     * @param params
     * @return
     */
    @Override
    protected Void doInBackground(Void... params) {
        String ownerNotiKey = event.getEventOwner().getNotifyKey();
        String msg = String.format("\'%s\' %s has joined your event",event.getTopic(), Resources.owner.getUsername());
        Database.sendMsg(ownerNotiKey, msg);
        return null;
    }
}
