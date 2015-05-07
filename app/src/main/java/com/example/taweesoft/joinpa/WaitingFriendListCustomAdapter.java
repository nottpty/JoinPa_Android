package com.example.taweesoft.joinpa;

import android.content.Context;
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

import java.util.List;
import java.util.Observer;

/**
 * Created by taweesoft on 7/5/2558.
 */
public class WaitingFriendListCustomAdapter extends ArrayAdapter<Friend>{
    private Observable observable;
    public WaitingFriendListCustomAdapter(Context context,List<Friend> waitingList){
        super(context,0,waitingList);
        observable = new Observable();
        observable.addObserver((Observer)context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Friend friend = getItem(position);
        String friendName = friend.getUsername();
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.friend_waiting_list_view,null);
        TextView txt_username = (TextView)convertView.findViewById(R.id.txt_username);
        Button btn_accept = (Button)convertView.findViewById(R.id.btn_accept);
        btn_accept.setOnClickListener(new AcceptFriend(friend));
        txt_username.setText(friendName);
        return convertView;
    }

    class AcceptFriend implements View.OnClickListener{
        private Friend friend;
        public AcceptFriend(Friend friend){
            this.friend = friend;
        }
        @Override
        public void onClick(View v) {
            Resources.owner.acceptFriend(friend);
            observable.setChanged();
            observable.notifyObservers();
        }
    }
}
