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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.taweesoft.joinpa.Library.Friend;
import com.example.taweesoft.joinpa.R;

import java.util.ArrayList;
import java.util.Arrays;
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
    private boolean[] isCheckedState;

    public FriendListCustomAdapter(Context context,List<Friend> friendList){
        super(context,0,friendList);
        observable = new AdapterObservable();
        observable.addObserver((Observer)context);
        this.friendList = friendList;
        isCheckedState = new boolean[friendList.size()];
    }
    public View getView(int position, View view, ViewGroup parent){

        Friend friend = getItem(position);
        view = LayoutInflater.from(getContext()).inflate(R.layout.activity_friend_list_view,null);
        TextView txt_friendName = (TextView)view.findViewById(R.id.txt_friendName);
        CheckBox cb_check = (CheckBox)view.findViewById(R.id.cb_check);
        SelectFriendEvent event = new SelectFriendEvent(friend,position);
        cb_check.setOnClickListener(event);
        cb_check.setChecked(isCheckedState[position]);
        txt_friendName.setText(friend.getUsername());

        return view;
    }
    class SelectFriendEvent implements View.OnClickListener{
        private Friend friend;
        private int position;
        public SelectFriendEvent(Friend friend,int position){
            this.friend = friend;
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            boolean checked = !isCheckedState[position];
            isCheckedState[position] = checked;
            if(checked)
                selectedFriend.add(friend);
            else
                selectedFriend.remove(friend);
            observable.setChanged();
            observable.notifyObservers(selectedFriend);
        }
    }

    class AdapterObservable extends Observable{
        public void setChanged(){
            super.setChanged();
        }
    }
}
