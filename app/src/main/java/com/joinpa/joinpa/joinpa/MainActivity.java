package com.joinpa.joinpa.joinpa;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.joinpa.joinpa.joinpa.CreateEvent.FriendView.FriendListView.FriendListActivity;
import com.joinpa.joinpa.joinpa.FriendRequest.FriendRequestActivity;
import com.joinpa.joinpa.joinpa.Library.Database;
import com.joinpa.joinpa.joinpa.Library.JoiningEvent;
import com.joinpa.joinpa.joinpa.JoiningEventView.JoiningListCustomAdapter;
import com.joinpa.joinpa.joinpa.Library.Resources;
import com.joinpa.joinpa.joinpa.Logout.LogoutDialog;
import com.joinpa.joinpa.joinpa.MyEventView.MyEventActivity;
import com.joinpa.joinpa.joinpa.MyJoinedEvent.MyJoinedEventActivity;
import com.joinpa.joinpa.joinpa.Notification.NotifyOwner;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Main activity.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */

public class MainActivity extends ActionBarActivity implements Observer{
    private ListView lv_joiningList;
    private List<JoiningEvent> joiningEventList;
    private Button btn_myEvent,btn_createEvent,btn_myJoinedEvent;
    private JoiningListCustomAdapter joinListAdapter;
    @Override

    /**
     * On create an activity.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        checkNotiKey();
        initComponents();
        initMyEventList();
        initButton();
//        updateInBackground();
    }

    /**
     * Initial components
     */
    public void initComponents(){
        btn_myEvent = (Button)findViewById(R.id.btn_myEvent);
        btn_createEvent = (Button)findViewById(R.id.btn_newEvent);
        lv_joiningList = (ListView)findViewById(R.id.lv_joiningList);
        btn_myJoinedEvent = (Button)findViewById(R.id.btn_myJoinedEvent);
    }

    /**
     * Initial list view
     */
    public void initMyEventList(){
        joiningEventList = Resources.owner.getJoiningEvents();
        joinListAdapter = new JoiningListCustomAdapter(this,joiningEventList);
        lv_joiningList.setAdapter(joinListAdapter);
    }

    /**
     * Initial buttons event.
     */
    public void initButton(){
        ShowMyEventClick clickEvent = new ShowMyEventClick(this);
        btn_myEvent.setOnClickListener(clickEvent);

        CreateEventClick createEventClick = new CreateEventClick(this);
        btn_createEvent.setOnClickListener(createEventClick);

        ShowMyJoinedEvent joinedEventClick = new ShowMyJoinedEvent(this);
        btn_myJoinedEvent.setOnClickListener(joinedEventClick);

    }

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
        Resources.owner.moveToJoined(joiningEvent);
        runOnUiThread(new Runnable(){ //Because original thread and touch this view.
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if( id == R.id.action_logout){
            LogoutDialog logoutDialog = new LogoutDialog(this);
            logoutDialog.showDialog();
        }else if( id == R.id.action_refresh){
            updateListView();
        }else if( id == R.id.action_notification){
            Intent intent = new Intent(this,FriendRequestActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Check for notification key.
     */
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

    /**
     * Update view of joining event view.
     */
    class UpdateJoiningEventListView extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            Resources.owner.setJoiningEvents(Database.myJoiningEvents(Resources.owner,Resources.WAITING));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    joinListAdapter.notifyDataSetChanged();
                }
            });
        }
    };

    public void updateListView(){
        UpdateJoiningEventListView task = new UpdateJoiningEventListView();
        task.execute();
    }

    /**
     * Auto refresh on joining event.
     */
    public void updateInBackground(){
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                while(true){
                    if(Resources.isNewData){
                        updateListView();
                        Resources.isNewData = false;
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
