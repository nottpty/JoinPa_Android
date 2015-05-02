package com.example.taweesoft.joinpa;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.taweesoft.joinpa.CreateEvent.FriendView.FriendListView.FriendListActivity;
import com.example.taweesoft.joinpa.FindFriend.FindFriendDialog;
import com.example.taweesoft.joinpa.Library.JoiningEvent;
import com.example.taweesoft.joinpa.JoiningEventView.JoiningListCustomAdapter;
import com.example.taweesoft.joinpa.Library.Owner;
import com.example.taweesoft.joinpa.MyEventView.MyEventActivity;

import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class MainActivity extends ActionBarActivity implements Observer{
    public static Owner owner;
    private ListView lv_joiningList;
    private List<JoiningEvent> joiningEventList;
    private Button btn_myEvent,btn_createEvent,btn_findFriend;
    private JoiningListCustomAdapter joinListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        owner = (Owner)getIntent().getSerializableExtra("Owner");
        initComponents();
        initMyEventList();
        initButton();
    }

    /**
     * Initial components
     */
    public void initComponents(){
        btn_myEvent = (Button)findViewById(R.id.btn_myEvent);
        btn_createEvent = (Button)findViewById(R.id.btn_newEvent);
        lv_joiningList = (ListView)findViewById(R.id.lv_joiningList);
        btn_findFriend = (Button)findViewById(R.id.btn_findFriend);
    }

    /**
     * Initial list view
     */
    public void initMyEventList(){
        joiningEventList = owner.getJoiningEvents();
        joinListAdapter = new JoiningListCustomAdapter(this,joiningEventList);
        lv_joiningList.setAdapter(joinListAdapter);
    }

    /**
     * Initial buttons event.
     */
    public void initButton(){
        ShowMyEventClick clickEvent = new ShowMyEventClick();
        btn_myEvent.setOnClickListener(clickEvent);

        CreateEventClick createEventClick = new CreateEventClick();
        btn_createEvent.setOnClickListener(createEventClick);

        ShowFindDialog findDialog = new ShowFindDialog();
        btn_findFriend.setOnClickListener(findDialog);
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
        public void onClick(View v){
            Intent intent = new Intent(MainActivity.this,MyEventActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Show friend list for invite to event.
     */
    class CreateEventClick implements View.OnClickListener{
        public void onClick(View v){
            Intent intent = new Intent(MainActivity.this, FriendListActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Show find friend dialog.
     */
    class ShowFindDialog implements View.OnClickListener{
        public void onClick(View v){
            FindFriendDialog findFriend = new FindFriendDialog(MainActivity.this);
            findFriend.openDialog();
        }
    }



    /**
     * Update listview on joining event.
     */
    @Override
    public void update(Observable observable, Object data) {
        if( data == null ) return;
        JoiningEvent joiningEvent = (JoiningEvent)data;
        owner.moveToJoined(joiningEvent);
        runOnUiThread(new Runnable() { // Because original thread can touch this view.
            @Override
            public void run() {
                joinListAdapter.notifyDataSetChanged();
            }
        });

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
        }
        return super.onOptionsItemSelected(item);
    }
}
