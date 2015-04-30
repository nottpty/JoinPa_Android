package com.example.taweesoft.joinpa.Library;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.taweesoft.joinpa.CreateEvent.NewEvent.NewEventActivity;

import java.util.Calendar;
import java.util.Observer;

/**
 * Created by taweesoft on 29/4/2558.
 */
public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private Observable observable;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        observable = new Observable();
        NewEventActivity activity = (NewEventActivity)getActivity();
        Observer model = (Observer)activity.getController().getModel();
        observable.addObserver(model);
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        observable.setChanged();
        int[] dateArr = new int[]{dayOfMonth,monthOfYear,year};
        observable.notifyObservers(dateArr);
    }

}
