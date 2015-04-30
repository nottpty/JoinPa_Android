package com.example.taweesoft.joinpa.FindFriend.FindFriend.Strategy;

import android.os.AsyncTask;

import com.example.taweesoft.joinpa.Library.Database;
import com.example.taweesoft.joinpa.Library.Friend;

/**
 * Created by taweesoft on 30/4/2558.
 */
public class DatabaseFinder implements Finder{
    @Override
    public Friend find(final String username) {
        return (Friend)Database.getEachUser(username);
    }
}
