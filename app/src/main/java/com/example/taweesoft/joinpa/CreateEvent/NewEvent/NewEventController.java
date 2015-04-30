package com.example.taweesoft.joinpa.CreateEvent.NewEvent;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.taweesoft.joinpa.Library.DatePicker;
import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.Library.TimePicker;

import java.util.Date;

/**
 * Created by taweesoft on 29/4/2558.
 */
public class NewEventController extends Activity{
    private NewEventActivity view;
    private NewEventModel model;
    public NewEventController(NewEventActivity view, NewEventModel model){
        this.model = model;
        this.view = view;
        view.setLayoutDateAction(new DateClick());
        view.setLayoutTimeAction(new TimeClick());
        view.setIconSpinnerAction(new IconSelectedEvent());
    }
    class DateClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            DialogFragment dateDialog = new DatePicker();
            dateDialog.show(view.getFragmentManager(),"datePicker");
        }
    }

    class TimeClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            DialogFragment timeDialog = new TimePicker();
            timeDialog.show(view.getFragmentManager(),"timePicker");
        }
    }

    class IconSelectedEvent implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            //Get integer of icon in each element of arrayAdapter(CustomEventIcon).
            Integer iconKey = (Integer)parent.getItemAtPosition(position);
            //Get event's name from map.
            String eventName = Resources.eventsName.get(iconKey);
            view.setEventName(eventName);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public Date getDateAndTime(){
        return model.getDateAndTime();
    }
    public NewEventModel getModel(){
        return this.model;
    }
}
