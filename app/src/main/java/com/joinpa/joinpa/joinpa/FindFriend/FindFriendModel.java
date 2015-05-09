package com.joinpa.joinpa.joinpa.FindFriend;

import com.joinpa.joinpa.joinpa.FindFriend.FindFriend.State.AddState;
import com.joinpa.joinpa.joinpa.Library.Friend;
import com.joinpa.joinpa.joinpa.Library.Resources;

import java.util.List;
import java.util.Observable;

/**
 * Find friend model.
 * Created  on 1/5/2558.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
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
