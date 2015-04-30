package com.example.taweesoft.joinpa.MyEventView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.taweesoft.joinpa.Library.Event;
import com.example.taweesoft.joinpa.R;

import java.util.Date;
import java.util.List;

/**
 * Created by taweesoft on 28/4/2558.
 */
public class MyEventCustomAdapter extends ArrayAdapter<Event>{

    public MyEventCustomAdapter(Context context,List<Event> myEventList){
        super(context,0,myEventList);
    }

    public View getView(int position,View view,ViewGroup parent){
        Event event = getItem(position);
        if(view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_my_event_view,parent,false);
        TextView txt_eventName  = (TextView)view.findViewById(R.id.txt_eventName);
        TextView txt_note = (TextView)view.findViewById(R.id.txt_note);
        TextView txt_date = (TextView)view.findViewById(R.id.txt_date);
        TextView txt_time = (TextView)view.findViewById(R.id.txt_time);
        TextView txt_joinNumber = (TextView)view.findViewById(R.id.txt_joinNumber);

        txt_eventName.setText(event.getTopic());
        txt_note.setText(event.getNote());
        Date date = event.getDate();
        txt_date.setText(String.format("%02d/%02d/%02d",date.getDay(),date.getMonth(),date.getYear()));
        txt_time.setText(String.format("%02d:%02d:%02d", date.getHours(), date.getMinutes(), date.getSeconds()));
        txt_joinNumber.setText(event.getInvitedList().size()+"");
        return view;
    }
}