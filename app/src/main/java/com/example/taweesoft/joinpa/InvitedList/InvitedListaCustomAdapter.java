package com.example.taweesoft.joinpa.InvitedList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.Library.User;
import com.example.taweesoft.joinpa.R;

import java.util.List;
import java.util.Map;

/**
 * Created by taweesoft on 2/5/2558.
 */
public class InvitedListaCustomAdapter extends ArrayAdapter<Map<User,Integer>> {
    public InvitedListaCustomAdapter(Context context, List<Map<User,Integer>> mapList){
        super(context,0,mapList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Map<User,Integer> map = getItem(position);
        Map.Entry<User,Integer> each = map.entrySet().iterator().next();
        String username = each.getKey().getUsername();
        int status = each.getValue().intValue();
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.invited_list_view,parent,false);
        TextView txt_username = (TextView)convertView.findViewById(R.id.txt_username);
        TextView txt_status = (TextView)convertView.findViewById(R.id.txt_status);
        txt_username.setText(username);
        int color=0;
        switch(status){
            case Resources.JOIN :
                color = Color.GREEN;
                break;
            case Resources.WAITING :
                color = Color.GRAY;
                break;
            case Resources.DECLINE :
                color = Color.RED;
                break;
        }
        txt_status.setBackgroundColor(color);
        return convertView;
    }
}
