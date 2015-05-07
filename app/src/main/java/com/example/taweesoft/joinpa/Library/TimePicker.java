package com.example.taweesoft.joinpa.Library;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.taweesoft.joinpa.CreateEvent.NewEvent.NewEventActivity;

import java.util.Calendar;
import java.util.Observer;

/**
 * Time picker.
 * Created on 30/4/2558.
 */
public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    private Observable observable;

    /**
     * Create time picker dialog.
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        observable = new Observable();
        NewEventActivity activity = (NewEventActivity)getActivity();
        Observer model = (Observer)activity.getController().getModel();
        observable.addObserver(model);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(),this,hour,minute,true);
    }

    /**
     * Notify observer when time is changed or set. (Observer == NewEventModel)
     * @param timerPicker
     * @param hour
     * @param minute
     */
    public void onTimeSet(android.widget.TimePicker timerPicker,int hour, int minute){
        observable.setChanged();
        int[] dateArr = new int[]{hour,minute};
        observable.notifyObservers(dateArr);
    }
}
