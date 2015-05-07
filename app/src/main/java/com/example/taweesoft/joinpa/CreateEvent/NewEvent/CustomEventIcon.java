package com.example.taweesoft.joinpa.CreateEvent.NewEvent;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.R;

/**
 * Custom event icon for spinner.
 * Created on 29/4/2558.
 */
public class CustomEventIcon extends ArrayAdapter<Integer> {

    /**
     * Constructor.
     * @param context
     */
    public CustomEventIcon(Context context){
        super(context,android.R.layout.simple_spinner_dropdown_item, Resources.icons);
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
        int icon = getItem(position);
        if(view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_custom_event_icon,parent,false);
        ImageView imgView = (ImageView)view.findViewById(R.id.img_icon);
        TextView txt_eventName = (TextView)view.findViewById(R.id.txt_eventName);
        txt_eventName.setText(Resources.eventsName.get(icon));
        Log.d("KKKK : , ", txt_eventName.getText().toString());
        imgView.setImageResource(icon);
        return view;
    }
}
