package com.example.taweesoft.joinpa.FindFriend.FindFriend.State;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.taweesoft.joinpa.FindFriend.FindFriendDialog;
import com.example.taweesoft.joinpa.Library.Database;
import com.example.taweesoft.joinpa.Library.Friend;
import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.MainActivity;

import java.util.List;

/**
 * Created by taweesoft on 1/5/2558.
 */
public class FoundState implements AddState{
    @Override
    public void addNewFriend(final FindFriendDialog view, final Friend friend) {
        Toast.makeText(view.getContext(),friend.getUsername() + " began your friend", Toast.LENGTH_LONG).show();
        Resources.owner.addFriend(friend);
        view.getController().setState(view.notFoundState);
    }


}
