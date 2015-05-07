package com.joinpa.taweesoft.joinpa.FindFriend.FindFriend.State;

import android.widget.Toast;

import com.joinpa.taweesoft.joinpa.FindFriend.FindFriendDialog;
import com.joinpa.taweesoft.joinpa.Library.Friend;
import com.joinpa.taweesoft.joinpa.Library.Resources;

/**
 * Found friend state.
 * Created on 1/5/2558.
 */
public class FoundState implements AddState{

    /**
     * Add friend into friend list. and set current back to not found state.
     * @param view
     * @param friend
     */
    @Override
    public void addNewFriend(final FindFriendDialog view, final Friend friend) {
        Toast.makeText(view.getContext(),friend.getUsername() + " began your friend", Toast.LENGTH_LONG).show();
        Resources.owner.addFriend(friend);
        view.getController().setState(view.notFoundState);
    }


}
