package com.example.taweesoft.joinpa.FindFriend.FindFriend.Strategy;

import android.os.AsyncTask;

import com.example.taweesoft.joinpa.FindFriend.FindFriendDialog;
import com.example.taweesoft.joinpa.Library.Database;
import com.example.taweesoft.joinpa.Library.Friend;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by taweesoft on 30/4/2558.
 */
public class DatabaseFinder extends Observable implements Finder{

    public DatabaseFinder(Observer dialog){
        addObserver(dialog);
    }

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
