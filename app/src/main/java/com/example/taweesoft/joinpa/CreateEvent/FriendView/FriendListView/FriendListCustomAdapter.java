package com.example.taweesoft.joinpa.CreateEvent.FriendView.FriendListView;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.taweesoft.joinpa.Library.Friend;
import com.example.taweesoft.joinpa.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by taweesoft on 28/4/2558.
 */
public class FriendListCustomAdapter extends ArrayAdapter<Friend>{
    private AdapterObservable observable;
    private List<Friend> selectedFriend = new ArrayList<Friend>();
    private List<Friend> friendList;
    private LinearLayout layout_name;
    public FriendListCustomAdapter(Context context,List<Friend> friendList){
        super(context,0,friendList);
        observable = new AdapterObservable();
        observable.addObserver((Observer)context);
        this.friendList = friendList;
    }

    public View getView(final int position, View view, ViewGroup parent){
        Friend friend = getItem(position);
        if(view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_friend_list_view,parent,false);
        TextView txt_friendName = (TextView)view.findViewById(R.id.txt_friendName);
        layout_name = (LinearLayout)view.findViewById(R.id.layout_name);
        txt_friendName.setText(friend.getUsername());
        SelectFriendEvent event = new SelectFriendEvent(friend,layout_name);
        view.setOnClickListener(event);
        return view;
    }

    class SelectFriendEvent implements View.OnClickListener{
        private Boolean isCheck = false;
        private Friend friend;
        private LinearLayout layout;
        public SelectFriendEvent(Friend friend, LinearLayout layout){
            this.friend = friend;
            this.layout = layout;
        }
        public void onClick(View v){
            if(!isCheck){
                selectedFriend.add(friend);
                layout.setBackgroundColor(0xff38ff4f);
            }else{
                selectedFriend.remove(friend);
                layout.setBackgroundColor(0xff72c7ff);
            }
            observable.setChanged();
            observable.notifyObservers(selectedFriend);
            isCheck = !isCheck;
        }
    }

    class AdapterObservable extends Observable{
        public void setChanged(){
            super.setChanged();
        }
    }

}
