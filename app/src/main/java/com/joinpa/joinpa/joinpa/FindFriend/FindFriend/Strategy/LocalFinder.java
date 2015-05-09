package com.joinpa.joinpa.joinpa.FindFriend.FindFriend.Strategy;

import com.joinpa.joinpa.joinpa.Library.Database;
import com.joinpa.joinpa.joinpa.Library.Friend;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Find friend in local data. (For high performance)
 * Created on 30/4/2558.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class LocalFinder extends Observable implements Finder {

    public LocalFinder(Observer dialog){
        addObserver(dialog);
    } //(Observer = FindFriendDialog)

    /**
     * Find in local resource.
     * @param username
     */
    @Override
    public void find(String username) {
        Map<String,Friend> allUser = Database.allUser;
        Friend friend = allUser.get(username);
        setChanged();
        notifyObservers(friend);
    }
}
