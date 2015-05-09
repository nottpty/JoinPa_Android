package com.joinpa.joinpa.joinpa.CreateEvent.FriendView.FriendListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.joinpa.joinpa.joinpa.Library.Friend;
import com.joinpa.joinpa.joinpa.Library.Observable;
import com.joinpa.joinpa.joinpa.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Friend list custom adapter.
 * Show each friend in a card format.
 * Created on 28/4/2558.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class FriendListCustomAdapter extends ArrayAdapter<Friend>{
    private Observable observable;
    private List<Friend> selectedFriend = new ArrayList<Friend>();
    private List<Friend> friendList;
    private List<Boolean> isCheckedState;

    /**
     * Constructor.
     * @param context
     * @param friendList
     */
    public FriendListCustomAdapter(Context context,List<Friend> friendList){
        super(context,0,friendList);
        observable = new Observable();
        observable.addObserver((Observer)context); //Observer == FriendListActivity
        this.friendList = friendList;
        isCheckedState = new ArrayList<Boolean>();
        initialBooleanList(new boolean[friendList.size()]);
    }

    /**
     * Get view of each friend in card format.
     * @param position
     * @param view
     * @param parent
     * @return
     */
    public View getView(int position, View view, ViewGroup parent){
        checkForNewFriend();
        Friend friend = getItem(position);
        view = LayoutInflater.from(getContext()).inflate(R.layout.activity_friend_list_view,null);
        TextView txt_friendName = (TextView)view.findViewById(R.id.txt_friendName);
        CheckBox cb_check = (CheckBox)view.findViewById(R.id.cb_check);
        SelectFriendEvent event = new SelectFriendEvent(friend,position);
        cb_check.setOnClickListener(event);
        cb_check.setChecked(isCheckedState.get(position));
        txt_friendName.setText(friend.getUsername());

        return view;
    }

    /**
     * Add friend into selected list.
     */
    class SelectFriendEvent implements View.OnClickListener{
        private Friend friend;
        private int position;
        public SelectFriendEvent(Friend friend,int position){
            this.friend = friend;
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            boolean checked = !isCheckedState.get(position);
            isCheckedState.set(position,checked);
            if(checked)
                selectedFriend.add(friend);
            else
                selectedFriend.remove(friend);
            observable.setChanged();
            observable.notifyObservers(selectedFriend);
        }
    }

    /**
     * Check for new friend (If you have)
     */
    public void checkForNewFriend(){
        if(friendList.size() != isCheckedState.size()){
            int diff = friendList.size() - isCheckedState.size();
            for(int i = 0; i< diff ; i++)
                isCheckedState.add(0,false);
        }
    }

    /**
     * Initial boolean list for make view is correct check.
     * @param arr
     */
    public void initialBooleanList(boolean[] arr){
        for(boolean bool : arr){
            isCheckedState.add(bool);
        }
    }
}
