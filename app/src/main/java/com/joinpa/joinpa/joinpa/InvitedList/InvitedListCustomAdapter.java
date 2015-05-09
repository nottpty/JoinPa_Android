package com.joinpa.joinpa.joinpa.InvitedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        String username = each.getKey().getUsername();
        int status = each.getValue().intValue();
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.invited_list_view,parent,false);
        TextView txt_username = (TextView)convertView.findViewById(R.id.txt_username);
        LinearLayout layout_bg = (LinearLayout)convertView.findViewById(R.id.layout_bg);
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