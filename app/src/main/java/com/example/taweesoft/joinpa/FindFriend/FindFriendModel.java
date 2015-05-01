package com.example.taweesoft.joinpa.FindFriend;

import com.example.taweesoft.joinpa.Library.Friend;
import com.example.taweesoft.joinpa.MainActivity;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by taweesoft on 1/5/2558.
 */
public class FindFriendModel extends Observable{

    public boolean isAlreadyAdd(Friend friend){
        List<Friend> friendList = MainActivity.owner.getFriendList();
        boolean isYou = MainActivity.owner.getUsername().equals(friend.getUsername());
        return friendList.contains(friend) || isYou;
    }
}
