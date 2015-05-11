package com.joinpa.joinpa.joinpa.CreateEvent.NewEvent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.joinpa.joinpa.joinpa.Library.Resources;
import com.joinpa.joinpa.joinpa.R;

/**
 * Created by taweesoft on 11/5/2558.
 */
public class CustomSpinnerIcon extends ArrayAdapter<Integer>{

    /**
     * Constructor.
     * @param context
     */
    public CustomSpinnerIcon(Context context){
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int icon = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_event_icon,parent,false);
        ImageView img_icon = (ImageView)convertView.findViewById(R.id.img_icon);
        img_icon.setImageResource(icon);
        return convertView;
    }
}
