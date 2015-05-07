package com.example.taweesoft.joinpa.MyJoinedEvent;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import com.example.taweesoft.joinpa.InvitedList.InvitedListModel;
import com.example.taweesoft.joinpa.Library.JoiningEvent;
import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.Library.User;
import com.example.taweesoft.joinpa.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * My joined event activity.
 */
public class MyJoinedEventActivity extends ActionBarActivity {
    private ExpandableListView ep_lv_myJoinedEvent;
    private ProgressBar pbg_loading;

    /**
     * On create the activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_my_joined_event);
        initComponents();
    }

    /**
     * Initial the components.
     */
    public void initComponents(){
        ep_lv_myJoinedEvent = (ExpandableListView)findViewById(R.id.ep_lv_myJoinedEvent);
        pbg_loading = (ProgressBar)findViewById(R.id.pgb_loading);
        CheckLoading task = new CheckLoading();
        task.execute();
    }

    /**
     * Set view of expandable list view.
     */
    public void setExpandableListView(){
        List<JoiningEvent> joinedEvent = Resources.owner.getJoinedEvent();
        List<List<Map<User,Integer>>> allInvitedList = new ArrayList<List<Map<User,Integer>>>();
        for(JoiningEvent event : joinedEvent){
            List<Map<User,Integer>> invitedListEachEvent = InvitedListModel.sortedMap(event.getInvitedList());
            allInvitedList.add(invitedListEachEvent);
        }
        MyJoinedEventCustomAdapter adapter = new MyJoinedEventCustomAdapter(this,joinedEvent,allInvitedList);
        ep_lv_myJoinedEvent.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_joined_event, menu);
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

    /**
     * Check for data is ready.
     */
    class CheckLoading extends AsyncTask<Void,Void,Void>{
        /**
         * Show the progress bar.
         */
        @Override
        protected void onPreExecute() {
            pbg_loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            /*Run the progress bar until the data is ready.*/
            while(!Resources.owner.isLoadMyJoinedFinish()){}
            return null;
        }

        /**
         * Hide progress bar.
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            pbg_loading.setVisibility(View.INVISIBLE);
            setExpandableListView();
        }
    }
}
