package com.example.taweesoft.joinpa.MyEventView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.taweesoft.joinpa.Library.Event;
import com.example.taweesoft.joinpa.Library.Resources;
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
        TextView txt_date = (TextView)view.findViewById(R.id.txt_date);
        TextView txt_time = (TextView)view.findViewById(R.id.txt_time);
        TextView txt_note = (TextView)view.findViewById(R.id.txt_note);
        TextView txt_joined = (TextView)view.findViewById(R.id.txt_joined);
        TextView txt_waiting = (TextView)view.findViewById(R.id.txt_waiting);
        TextView txt_decline = (TextView)view.findViewById(R.id.txt_decline);
        LinearLayout layout_note = (LinearLayout)view.findViewById(R.id.layout_note);
        LinearLayout layout_iconBG = (LinearLayout)view.findViewById(R.id.layout_iconBG);
        ImageView img_iconBig = (ImageView)view.findViewById(R.id.img_iconBig);
        layout_iconBG.setBackgroundResource(Resources.cardBG.get(event.getIconID()));
        img_iconBig.setImageResource(Resources.icons.get(event.getIconID()));
        txt_eventName.setText(event.getTopic());
        Date date = event.getDate();
        txt_date.setText(String.format("%02d/%02d/%04d",date.getDate(),date.getMonth(),date.getYear()));
        txt_time.setText(String.format("%02d:%02d", date.getHours(), date.getMinutes()));
        txt_joined.setText(event.getJoinedNumber()+"");
        txt_waiting.setText(event.getWaitingNumber()+"");
        txt_decline.setText(event.getDeclineNumber()+"");
        if(event.getNote().equals(""))
            layout_note.setVisibility(View.GONE);
        else
            txt_note.setText(event.getNote());
        return view;
    }
}
