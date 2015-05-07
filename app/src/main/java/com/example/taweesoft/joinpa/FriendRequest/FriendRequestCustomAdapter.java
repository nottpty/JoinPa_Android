package com.example.taweesoft.joinpa.FriendRequest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.taweesoft.joinpa.Library.Database;
import com.example.taweesoft.joinpa.Library.Friend;
import com.example.taweesoft.joinpa.Library.Observable;
import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.Library.User;
import com.example.taweesoft.joinpa.R;

import java.util.List;
import java.util.Observer;

/**
 * Friend request custom adapter to show each friend request.
 */
public class FriendRequestCustomAdapter extends ArrayAdapter<Friend>{
    private Observable observable;

    /**
     * Constructor.
     * @param context
     * @param waitingList
     */
    public FriendRequestCustomAdapter(Context context, List<Friend> waitingList){
        super(context,0,waitingList);
        observable = new Observable();
        observable.addObserver((Observer)context); // Observer == FriendRequestActivity.
    }

    /**
     * Show view of each friend request.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Friend friend = getItem(position);
        String friendName = friend.getUsername();
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.friend_waiting_list_view,null);
        TextView txt_username = (TextView)convertView.findViewById(R.id.txt_username);
        Button btn_accept = (Button)convertView.findViewById(R.id.btn_accept);
        btn_accept.setOnClickListener(new ShowConfirmDialog(friend));
        txt_username.setText(friendName);
        return convertView;
    }

    /**
     * Show confirm dialog
     */
    class ShowConfirmDialog implements View.OnClickListener{
        private Friend friend;
        public ShowConfirmDialog(Friend friend){
            this.friend = friend;
        }
        @Override
        public void onClick(View v) {
            showConfirmDialog(friend);
        }
    }
    /**
     * Accept friend request.
     */
    class AcceptFriend implements DialogInterface.OnClickListener{
        private Friend friend;
        public AcceptFriend(Friend friend){
            this.friend = friend;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            Resources.owner.acceptFriend(friend);
            observable.setChanged();
            observable.notifyObservers();
        }
    }

    /**
     * Show confirm dialog
     * @param friend
     */
    public void showConfirmDialog(Friend friend){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setMessage(String.format("Accept friend request from %s",friend.getUsername()));
        dialog.setPositiveButton("Yes",new AcceptFriend(friend));
        dialog.setNegativeButton("No",null);
        dialog.show();
    }
}
