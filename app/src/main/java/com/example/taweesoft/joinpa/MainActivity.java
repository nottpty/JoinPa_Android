package com.example.taweesoft.joinpa;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.taweesoft.joinpa.CreateEvent.FriendView.FriendListView.FriendListActivity;
import com.example.taweesoft.joinpa.FindFriend.FindFriendDialog;
import com.example.taweesoft.joinpa.Library.Database;
import com.example.taweesoft.joinpa.Library.JoiningEvent;
import com.example.taweesoft.joinpa.JoiningEventView.JoiningListCustomAdapter;
import com.example.taweesoft.joinpa.Library.Owner;
import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.MyEventView.MyEventActivity;
import com.example.taweesoft.joinpa.MyJoinedEvent.MyJoinedEventActivity;
import com.example.taweesoft.joinpa.Notification.NotifyOwner;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class MainActivity extends Fragment implements Observer{
    private ListView lv_joiningList;
    private List<JoiningEvent> joiningEventList;
    private Button btn_myEvent,btn_createEvent,btn_myJoinedEvent;
    private JoiningListCustomAdapter joinListAdapter;
    private View v;
    private Activity activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        checkNotiKey();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_main, container, false);
        initComponents();
        initMyEventList();
        initButton();
        return v;
    }
    /**
     * Initial components
     */
    public void initComponents(){
        btn_myEvent = (Button)v.findViewById(R.id.btn_myEvent);
        btn_createEvent = (Button)v.findViewById(R.id.btn_newEvent);
        lv_joiningList = (ListView)v.findViewById(R.id.lv_joiningList);
        btn_myJoinedEvent = (Button)v.findViewById(R.id.btn_myJoinedEvent);
    }

    /**
     * Initial list view
     */
    public void initMyEventList(){
        joiningEventList = Resources.owner.getJoiningEvents();
        joinListAdapter = new JoiningListCustomAdapter(activity,this,joiningEventList);
        lv_joiningList.setAdapter(joinListAdapter);
    }

    /**
     * Initial buttons event.
     */
    public void initButton(){
        ShowMyEventClick clickEvent = new ShowMyEventClick(activity);
        btn_myEvent.setOnClickListener(clickEvent);

        CreateEventClick createEventClick = new CreateEventClick(activity);
        btn_createEvent.setOnClickListener(createEventClick);

        ShowMyJoinedEvent joinedEventClick = new ShowMyJoinedEvent(activity);
        btn_myJoinedEvent.setOnClickListener(joinedEventClick);

    }


//    public List<JoiningEvent> readJoiningEventList() {
//        List<JoiningEvent> joiningEvents=null;
//        AsyncTask<Void,Void,List<JoiningEvent>> task = new AsyncTask<Void, Void, List<JoiningEvent>>() {
//            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
//            AlertDialog dialog = dialogBuilder.create();
//            protected void onPreExecute(){
//                dialog.setMessage("Loading your new event...");
//                dialog.setCancelable(false);
//                dialog.show();
//            }
//
//            @Override
//            protected List<JoiningEvent> doInBackground(Void... params) {
//                return Database.myJoiningEvents(owner);
//            }
//
//            protected void onPostExecute(List<JoiningEvent> param){
//                dialog.setMessage("Success");
//                dialog.setCancelable(true);
//                dialog.dismiss();
//            }
//        };
//        task.execute();
//        try{
//            joiningEvents = task.get();
//        }catch(InterruptedException e1){
//            e1.printStackTrace();
//        }catch(ExecutionException e2){ e2.printStackTrace();}
//
//        return joiningEvents;
//    }

    /**
     * Show My event action.
     */
    class ShowMyEventClick implements View.OnClickListener{
        private Activity activity;

        public ShowMyEventClick(Activity activity){
            this.activity = activity;
        }

        public void onClick(View v){
            Intent intent = new Intent(activity,MyEventActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Show friend list for invite to event.
     */
    class CreateEventClick implements View.OnClickListener{
        private Activity activity;

        public CreateEventClick(Activity activity){
            this.activity = activity;
        }

        public void onClick(View v){
            Intent intent = new Intent(activity, FriendListActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Show joined event.
     */
    class ShowMyJoinedEvent implements View.OnClickListener{
        private Activity activity;

        public ShowMyJoinedEvent(Activity activity){
            this.activity = activity;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, MyJoinedEventActivity.class);
            activity.startActivity(intent);
        }
    }


    /**
     * Update listview on joining event.
     */
    @Override
    public void update(Observable observable, Object data) {
        if( data == null ) return;
        final JoiningEvent joiningEvent = (JoiningEvent)data;
        Log.d("OOO1: ", Resources.owner.getJoiningEvents().size()+"");
        Resources.owner.moveToJoined(joiningEvent);
        Log.d("OOO2: ", Resources.owner.getJoiningEvents().size()+"");
        activity.runOnUiThread(new Runnable(){ //Because original thread and touch this view.
            @Override
            public void run() {
                joinListAdapter.notifyDataSetChanged();
            }
        });
        if(joiningEvent.getMyStatus() == 1){ // Joined
            NotifyOwner upAndNotify = new NotifyOwner(joiningEvent);
            upAndNotify.execute();
        }


    }




    public void checkNotiKey(){
        CheckExistNotiKey task = new CheckExistNotiKey();
        task.execute();
    }
    class CheckExistNotiKey extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            String currentRegistrationID = Resources.deviceID;
            String dbRegistrationID = Resources.owner.getNotifyKey();
            if(!(currentRegistrationID.equals(dbRegistrationID))){
                Database.updateNotificationKey(currentRegistrationID,Resources.owner.getUsername());
            }
            return null;
        }
    }


}
