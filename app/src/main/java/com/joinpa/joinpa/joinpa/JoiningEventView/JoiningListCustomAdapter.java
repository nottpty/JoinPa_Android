package com.joinpa.joinpa.joinpa.JoiningEventView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joinpa.joinpa.joinpa.InvitedList.InvitedListDialog;
import com.joinpa.joinpa.joinpa.Library.Database;
import com.joinpa.joinpa.joinpa.Library.JoiningEvent;
import com.joinpa.joinpa.joinpa.Library.Observable;
import com.joinpa.joinpa.joinpa.Library.Resources;
import com.joinpa.joinpa.joinpa.R;

import java.util.Date;
import java.util.List;
import java.util.Observer;

/**
 * Joining list custom view.
 * Created on 27/4/2558.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class JoiningListCustomAdapter extends ArrayAdapter<JoiningEvent>{
    private final int JOIN = 1;
    private final int DECLINE = 2;
    private Observable observable;
    private List<JoiningEvent> joiningList;

    /**
     * Constructor.
     * @param context
     * @param joiningEventList
     */
    public JoiningListCustomAdapter(Context context,List<JoiningEvent> joiningEventList){
        super(context,0,joiningEventList);
        observable = new Observable();
        observable.addObserver((Observer)context);
    }

    /**
     * Show joining list card of each event.
     * @param position
     * @param view
     * @param parent
     * @return
     */
    public View getView(int position, View view, ViewGroup parent){
        JoiningEvent event = getItem(position);

        /*Initialize components.*/
        if(view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_joining_event_view,parent,false);

        TextView txt_topic = (TextView)view.findViewById(R.id.txt_eventName);
        TextView txt_OwnerName = (TextView)view.findViewById(R.id.txt_ownerName);
        TextView txt_date = (TextView)view.findViewById(R.id.txt_date);
        TextView txt_time = (TextView)view.findViewById(R.id.txt_time);
        TextView txt_note = (TextView)view.findViewById(R.id.txt_note);
        TextView btn_join = (TextView)view.findViewById(R.id.btn_join);
        TextView btn_invitedList = (TextView)view.findViewById(R.id.btn_invitedList);
        TextView btn_decline = (TextView)view.findViewById(R.id.btn_decline);
        TextView txt_join = (TextView)view.findViewById(R.id.txt_join);
        TextView txt_waiting = (TextView)view.findViewById(R.id.txt_waiting);
        TextView txt_decline = (TextView)view.findViewById(R.id.txt_decline);
        LinearLayout layout_iconBG = (LinearLayout)view.findViewById(R.id.layout_iconBG);
        LinearLayout layout_note = (LinearLayout)view.findViewById(R.id.layout_note);
        ImageView img_iconBig = (ImageView)view.findViewById(R.id.img_iconBig);

        /*Initialize data to components.*/
        img_iconBig.setImageResource(Resources.icons.get(event.getIconID()));
        layout_iconBG.setBackgroundResource(Resources.cardBG.get(event.getIconID()));
        btn_join.setOnClickListener(setYesEventForJoinConfirm(event));
        btn_invitedList.setOnClickListener(new ShowInvitedListDialog(event));
        btn_decline.setOnClickListener(setYesEventForDeclineConfirm(event));
        txt_topic.setText(event.getTopic());
        txt_OwnerName.setText(event.getEventOwner().getUsername().toUpperCase());
        Date date = event.getDate();
        txt_date.setText(String.format("%02d/%02d/%04d",date.getDate(),date.getMonth(),date.getYear()));
        txt_time.setText(String.format("%02d:%02d",date.getHours(),date.getMinutes()));
        txt_join.setText(event.getJoinedNumber()+"");
        txt_waiting.setText(event.getWaitingNumber()+"");
        txt_decline.setText(event.getDeclineNumber()+"");
        if(event.getNote().equals(""))
            layout_note.setVisibility(View.GONE);
        else
            txt_note.setText(event.getNote());
        return view;
    }

    /**
     * Confirm to join this event (action).
     * @param event
     * @return
     */
    public View.OnClickListener setYesEventForJoinConfirm(JoiningEvent event){
        String message = String.format("Join to \'%s\'\nBy %s",event.getTopic(),event.getEventOwner().getUsername());
        JoinEventAction joinAction = new JoinEventAction(event,JOIN);
        ShowComfirmDialog joinConfirm = new ShowComfirmDialog(event,message,joinAction);
        return joinConfirm;
    }

    /**
     * Decline this event (action).
     * @param event
     * @return
     */
    public View.OnClickListener setYesEventForDeclineConfirm(JoiningEvent event){
        String message = String.format("Decline the \'%s\' event\nBy %s",event.getTopic(),event.getEventOwner().getUsername());
        JoinEventAction declineAction = new JoinEventAction(event,DECLINE);
        ShowComfirmDialog declineConfirm = new ShowComfirmDialog(event,message,declineAction);
        return declineConfirm;
    }

    /**
     * Join event action.
     */
    class JoinEventAction implements DialogInterface.OnClickListener{
        private JoiningEvent event;
        private int status;
        public JoinEventAction(JoiningEvent event,int status){
            this.event = event;
            this.status = status;
        }

        /**
         * Join the event and change the status.
         * @param dialog
         * @param which
         */
        @Override
        public void onClick(DialogInterface dialog, int which) {
            AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    /*Set status in owner's joiningEvent*/
                    int index = Resources.owner.getJoiningEvents().indexOf(event);
                    JoiningEvent currentEvent = Resources.owner.getJoiningEvents().get(index);
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

    /**
     * Show confirm dialog.
     */
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

    /**
     * Show invited list dialog.
     */
    class ShowInvitedListDialog implements View.OnClickListener{
        private JoiningEvent event;
        private InvitedListDialog dialog;
        public ShowInvitedListDialog(JoiningEvent event){
            this.event = event;
            dialog = new InvitedListDialog(getContext(),event);
        }
        @Override
        public void onClick(View v) {
            dialog.openDialog();
        }
    }
}
