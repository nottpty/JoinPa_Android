package com.example.taweesoft.joinpa.JoiningEventView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.taweesoft.joinpa.Library.JoiningEvent;
import com.example.taweesoft.joinpa.R;

import java.util.List;

/**
 * Created by taweesoft on 27/4/2558.
 */
public class JoiningListCustomAdapter extends ArrayAdapter<JoiningEvent>{

    public JoiningListCustomAdapter(Context context,List<JoiningEvent> joiningEventList){
        super(context,0,joiningEventList);
    }

    public View getView(int position, View view, ViewGroup parent){
        JoiningEvent event = getItem(position);
        if(view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_joining_event_view,parent,false);
        TextView txt_topic = (TextView)view.findViewById(R.id.txt_topic);
        TextView txt_OwnerName = (TextView)view.findViewById(R.id.txt_OwnerName);
        txt_topic.setText(event.getTopic());
        txt_OwnerName.setText(event.getOwnerName());
        return view;
    }
}
