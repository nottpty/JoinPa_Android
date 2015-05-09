package com.joinpa.joinpa.joinpa.FindFriend.FindFriend.Strategy;

import android.os.AsyncTask;

import com.joinpa.joinpa.joinpa.Library.Database;
import com.joinpa.joinpa.joinpa.Library.Friend;

import java.util.Observable;
import java.util.Observer;

/**
 * Find friend on database. (For high accuracy).
 * Created on 30/4/2558.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class DatabaseFinder extends Observable implements Finder{

    public DatabaseFinder(Observer dialog){
        addObserver(dialog);
    } // Observer = FindFriendDialog

    /**
     * Find friend on database.
     * @param username
     */
    @Override
    public void find(final String username) {
        AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
            Friend friend = null;

            @Override
            protected Void doInBackground(Void... params) {
                friend = (Friend)Database.getEachUser(username);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                setChanged();
                notifyObservers(friend);
            }
        };
        task.execute();
    }

}
