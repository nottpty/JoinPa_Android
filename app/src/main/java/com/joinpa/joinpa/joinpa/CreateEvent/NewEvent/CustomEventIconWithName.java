package com.joinpa.joinpa.joinpa.CreateEvent.NewEvent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joinpa.joinpa.joinpa.Library.Resources;
import com.joinpa.joinpa.joinpa.R;

/**
 * Custom event icon for spinner.
 * Created on 29/4/2558.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class CustomEventIconWithName extends ArrayAdapter<String> {

    /**
     * Constructor.
     * @param context
     */
    public CustomEventIconWithName(Context context){
        super(context,android.R.layout.simple_spinner_dropdown_item, Resources.eventNameKey);
    }

    /**
     * Show view when spinner is dropping down.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    /**
     * Show each event icon and event name.
     * @param position
     * @param view
     * @param parent
     * @return
     */
    public View getView(int position,View view,ViewGroup parent){
        String eventName =  Resources.eventNameKey.get(position);
        int iconID = Resources.eventsName.get(eventName);
        Integer icon = Resources.icons.get(iconID);
        if(view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_custom_event_icon_with_name,parent,false);
        ImageView imgView = (ImageView)view.findViewById(R.id.img_icon);
        TextView txt_eventName = (TextView)view.findViewById(R.id.txt_eventName);
        txt_eventName.setText(eventName);
        imgView.setImageResource(icon);
        return view;
    }
}
