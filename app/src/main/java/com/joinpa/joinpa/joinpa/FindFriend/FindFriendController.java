package com.joinpa.joinpa.joinpa.FindFriend;

import com.joinpa.joinpa.joinpa.FindFriend.FindFriend.State.AddState;
import com.joinpa.joinpa.joinpa.Library.Friend;

import java.util.Observer;

/**
 * Find friend controller.
 * Created on 1/5/2558.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class FindFriendController {
    private FindFriendModel model;
    private Observer view;

    /**
     * Constructor.
     * @param view
     * @param model
     */
    public FindFriendController(Observer view, FindFriendModel model){
        this.model = model;
        this.view = view;
        model.addObserver(view); // Observer = FindFriendDialog
    }

    /**
     * Check is this friend already in friend list.
     * @param friend
     * @return
     */
    public boolean isAlreadyAdd(Friend friend){
        return model.isAlreadyAdd(friend);
    }

    /**
     * Set state.
     * @param state
     */
    public void setState(AddState state){
        model.setState(state);
    }

    /**
     * Add new friend.
     * @param view
     * @param friend
     */
    public void addNewFriend(FindFriendDialog view, Friend friend){
        model.addNewFriend(view,friend);
    }
}
