package com.joinpa.joinpa.joinpa.AddFriend;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;

import com.joinpa.joinpa.joinpa.Library.Friend;
import com.joinpa.joinpa.joinpa.Library.Resources;
import com.joinpa.joinpa.joinpa.R;

/**
 * Created by taweesoft on 11/5/2558.
 */
public class AddFriend implements View.OnClickListener {
    private Activity activity;
    private Friend friend;

    public AddFriend(Activity activity,Friend friend){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("Add friend");
        dialog.setIcon(R.drawable.person_icon);
        dialog.setMessage(String.format("Send friend request to %s", friend.getUsername()));
        dialog.set
    }

    class Confirm implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Resources.owner.addFriend(friend);
        }
    }

}
