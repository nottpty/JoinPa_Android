package com.example.taweesoft.joinpa.CreateEvent.NewEvent;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

/**
 * Created by taweesoft on 29/4/2558.
 */
public class NewEventController extends Activity{
    private static NewEventActivity activity;
    private NewEventModel model;
    public NewEventController(NewEventModel model){
        this.model = model;
    }

    public static void setView(NewEventActivity activity){
        NewEventController.activity = activity;
        activity.setLayoutDateAction(new DateClick());
    }

    static class DateClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Log.d("QQQQ: ", "Controller worked");
        }
    }
}
