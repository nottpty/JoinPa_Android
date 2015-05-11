package com.joinpa.joinpa.joinpa.AddFriend;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.joinpa.joinpa.joinpa.Library.Friend;
import com.joinpa.joinpa.joinpa.Library.Resources;
import com.joinpa.joinpa.joinpa.R;

/**
 * Created by taweesoft on 11/5/2558.
 */
public class AddFriend implements View.OnClickListener {
    private Context context;
    private Friend friend;
    private Button btn;

    /**
     * Constructor for normal use.
     * @param context
     * @param friend
     */
    public AddFriend(Context context,Friend friend){
        this.context = context;
        this.friend = friend;
    }

    /**
     * Constructor for button. Click and hide.
     * @param context
     * @param friend
     */
    public AddFriend(Context context,Friend friend,Button btn){
        this(context,friend);
        this.btn = btn;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Add friend");
        dialog.setIcon(R.drawable.person_icon);
        dialog.setMessage(String.format("Send friend request to %s", friend.getUsername()));
        dialog.setPositiveButton("YES",new Confirm());
        dialog.setNegativeButton("NO",null);
        dialog.show();
    }

    class Confirm implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(btn != null) btn.setVisibility(View.GONE);
            Resources.owner.addFriend(friend);
            Toast.makeText(context,String.format("Sending friend request to %s",friend.getUsername()),Toast.LENGTH_LONG).show();
        }
    }

}
