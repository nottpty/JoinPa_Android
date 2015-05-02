package com.example.taweesoft.joinpa.JoiningEventView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.taweesoft.joinpa.Library.Database;
import com.example.taweesoft.joinpa.Library.JoiningEvent;
import com.example.taweesoft.joinpa.Library.Observable;
import com.example.taweesoft.joinpa.Library.User;
import com.example.taweesoft.joinpa.MainActivity;
import com.example.taweesoft.joinpa.R;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Observer;

/**
 * Created by taweesoft on 27/4/2558.
 */
public class JoiningListCustomAdapter extends ArrayAdapter<JoiningEvent>{
    private final int JOIN = 1;
    private final int DECLINE = 2;
    private Observable observable;
    private List<JoiningEvent> joiningList;
    public JoiningListCustomAdapter(Context context,List<JoiningEvent> joiningEventList){
        super(context,0,joiningEventList);
        observable = new Observable();
        observable.addObserver((Observer)context);
    }

    public View getView(int position, View view, ViewGroup parent){
        JoiningEvent event = getItem(position);

        if(view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_joining_event_view,parent,false);

        TextView txt_topic = (TextView)view.findViewById(R.id.txt_topic);
        TextView txt_OwnerName = (TextView)view.findViewById(R.id.txt_OwnerName);
        Button btn_join = (Button)view.findViewById(R.id.btn_join);
        Button btn_decline = (Button)view.findViewById(R.id.btn_decline);
        btn_join.setOnClickListener(setYesEventForJoinConfirm(event));
        btn_decline.setOnClickListener(setYesEventForDeclineConfirm(event));
        txt_topic.setText(event.getTopic());
        txt_OwnerName.setText(event.getEventOwner().getUsername());
        return view;
    }

    public View.OnClickListener setYesEventForJoinConfirm(JoiningEvent event){
        String message = String.format("Join to \'%s\'\nBy %s",event.getTopic(),event.getEventOwner().getUsername());
        JoinEventAction joinAction = new JoinEventAction(event,JOIN);
        ShowComfirmDialog joinConfirm = new ShowComfirmDialog(event,message,joinAction);
        return joinConfirm;
    }

    public View.OnClickListener setYesEventForDeclineConfirm(JoiningEvent event){
        String message = String.format("Decline the \'%s\' event\nBy %s",event.getTopic(),event.getEventOwner().getUsername());
        JoinEventAction declineAction = new JoinEventAction(event,DECLINE);
        ShowComfirmDialog declineConfirm = new ShowComfirmDialog(event,message,declineAction);
        return declineConfirm;
    }
    class JoinEventAction implements DialogInterface.OnClickListener{
        private JoiningEvent event;
        private int status;
        public JoinEventAction(JoiningEvent event,int status){
            this.event = event;
            this.status = status;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    /*Set status in owner's joiningEvent*/
                    int index = MainActivity.owner.getJoiningEvents().indexOf(event);
                    JoiningEvent currentEvent = MainActivity.owner.getJoiningEvents().get(index);
                    currentEvent.setStatus(status);
                    observable.setChanged();
                    observable.notifyObservers(currentEvent);
                    /*Update in database.*/
                    Database.joinEvent(event,status);
                    return null;
                }
            };
            task.execute();
        }
    }

    class ShowComfirmDialog implements View.OnClickListener{
        private AlertDialog.Builder dialog;
        private JoiningEvent event;
        public ShowComfirmDialog(JoiningEvent event,String message,JoinEventAction yesEvent){
            this.event = event;
            dialog = new AlertDialog.Builder(getContext());
            dialog.setMessage(message);
            dialog.setPositiveButton("YES",yesEvent);
            dialog.setNegativeButton("NO", null);
        }

        @Override
        public void onClick(View v) {
            dialog.create().show();
        }
    }
}
