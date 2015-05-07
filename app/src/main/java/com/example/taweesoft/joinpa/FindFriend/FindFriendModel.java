package com.example.taweesoft.joinpa.FindFriend;

import android.content.Context;

import com.example.taweesoft.joinpa.FindFriend.FindFriend.State.AddState;
import com.example.taweesoft.joinpa.FindFriend.FindFriend.State.FoundState;
import com.example.taweesoft.joinpa.FindFriend.FindFriend.State.NotFoundState;
import com.example.taweesoft.joinpa.Library.Friend;
import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.MainActivity;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Find friend model.
 * Created  on 1/5/2558.
 */
public class FindFriendModel extends Observable{
    private AddState state;

    /**
     * Check if this friend is already in your friend list.
     * @param friend
     * @return
     */
    public boolean isAlreadyAdd(Friend friend){
        List<Friend> friendList = Resources.owner.getFriendList();
        boolean isYou = Resources.owner.getUsername().equals(friend.getUsername());
        return friendList.contains(friend) || isYou;
    }

    /**
     * Set state.
     * @param state
     */
    public void setState(AddState state){
        this.state = state;
    }

    /**
     * Add new friend.
     * @param context
     * @param friend
     */
    public void addNewFriend(FindFriendDialog context,Friend friend){
        state.addNewFriend(context,friend);
    }
}
