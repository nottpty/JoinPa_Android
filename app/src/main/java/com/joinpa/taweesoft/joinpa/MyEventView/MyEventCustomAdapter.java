package com.joinpa.taweesoft.joinpa.MyEventView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joinpa.taweesoft.joinpa.Library.Event;
import com.joinpa.taweesoft.joinpa.Library.Resources;
import com.joinpa.taweesoft.joinpa.R;

import java.util.Date;
import java.util.List;

/**
 * Custom adapter.
 * To show each event card.
 */
public class MyEventCustomAdapter extends ArrayAdapter<Event>{

    /**
     * Constructor.
     * @param context
     * @param myEventList
     */
    public MyEventCustomAdapter(Context context,List<Event> myEventList){
        super(context,0,myEventList);
    }

    /**
     * Return view of each event. Displaying in the screen.
     * @param position
     * @param view
     * @param parent
     * @return
     */
    public View getView(int position,View view,ViewGroup parent){

        Event event = getItem(position);

        /*Initial components on current view.*/
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

        /*Set component's data.*/
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
