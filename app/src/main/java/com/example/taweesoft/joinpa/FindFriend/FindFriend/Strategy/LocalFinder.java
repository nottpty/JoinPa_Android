package com.example.taweesoft.joinpa.FindFriend.FindFriend.Strategy;

import com.example.taweesoft.joinpa.FindFriend.FindFriendDialog;
import com.example.taweesoft.joinpa.Library.Database;
import com.example.taweesoft.joinpa.Library.Friend;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by taweesoft on 30/4/2558.
 */
public class LocalFinder extends Observable implements Finder {

    public LocalFinder(Observer dialog){
        addObserver(dialog);
    }

    @Override
    public void find(String username) {
        Map<String,Friend> allUser = Database.allUser;
        Friend friend = allUser.get(username);
        setChanged();
        notifyObservers(friend);
    }
}
