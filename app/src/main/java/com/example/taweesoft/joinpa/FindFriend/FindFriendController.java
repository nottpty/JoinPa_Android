package com.example.taweesoft.joinpa.FindFriend;

import com.example.taweesoft.joinpa.FindFriend.FindFriend.State.AddState;
import com.example.taweesoft.joinpa.Library.Friend;

/**
 * Created by taweesoft on 1/5/2558.
 */
public class FindFriendController {
    private FindFriendModel model;
    private FindFriendDialog view;
    public FindFriendController(FindFriendDialog view, FindFriendModel model){
        this.model = model;
        this.view = view;
        model.addObserver(view);
    }

    public boolean isAlreadyAdd(Friend friend){
        return model.isAlreadyAdd(friend);
    }

    public void setState(AddState state){
        model.setState(state);
    }

    public void addNewFriend(FindFriendDialog view, Friend friend){
        model.addNewFriend(view,friend);
    }
}
