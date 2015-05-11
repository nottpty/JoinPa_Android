package com.joinpa.joinpa.joinpa.InvitedList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joinpa.joinpa.joinpa.AddFriend.AddFriend;
import com.joinpa.joinpa.joinpa.Library.Friend;
import com.joinpa.joinpa.joinpa.Library.Resources;
import com.joinpa.joinpa.joinpa.Library.User;
import com.joinpa.joinpa.joinpa.R;

import java.util.List;
import java.util.Map;

/**
 * Invited List custom adapter to show invited list of each event.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class InvitedListCustomAdapter extends ArrayAdapter<Map<User,Integer>> {

    /**
     * Constructor.
     * @param context
     * @param mapList
     */
    public InvitedListCustomAdapter(Context context, final List<Map<User, Integer>> mapList){
        super(context,0,mapList);
    }

    /**
     * Show invited list of each event.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Map<User,Integer> map = getItem(position);
        Map.Entry<User,Integer> each = map.entrySet().iterator().next();
        User user = each.getKey();
        String username = user.getUsername();
        int status = each.getValue().intValue();
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.invited_list_view,parent,false);
        TextView txt_username = (TextView)convertView.findViewById(R.id.txt_username);
        LinearLayout layout_bg = (LinearLayout)convertView.findViewById(R.id.layout_bg);
        Button btn_add = (Button)convertView.findViewById(R.id.btn_add);

        /*If already friend. then not show the add button.*/
        if(Resources.owner.getFriendList().contains(user) || Resources.owner.equals(user))
            btn_add.setVisibility(View.GONE);
        else{
            btn_add.setOnClickListener(new AddFriend(getContext(),new Friend(user.getUsername(),user.getNotifyKey()),btn_add));
        }
        txt_username.setText(username);
        int bg=0;
        switch(status){
            case Resources.JOIN :
                bg = R.drawable.invited_list_green;
                break;
            case Resources.WAITING :
                bg = R.drawable.invited_list_yellow;
                break;
            case Resources.DECLINE :
                bg = R.drawable.invited_list_red;
                break;
        }
        layout_bg.setBackgroundResource(bg);
        return convertView;
    }


}
