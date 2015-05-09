package com.joinpa.joinpa.joinpa.FriendRequest;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.joinpa.joinpa.joinpa.Library.Database;
import com.joinpa.joinpa.joinpa.Library.Friend;
import com.joinpa.joinpa.joinpa.Library.Resources;
import com.joinpa.joinpa.joinpa.R;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Friend request activity.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class FriendRequestActivity extends ActionBarActivity implements Observer {
    private ListView lv_waitingList;
    private FriendRequestCustomAdapter adapter;

    /**
     * Constructor.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_friend_waiting_list);
        initComponents();
    }


    /**
     * On create activity.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_waiting_list, menu);
        return true;
    }

    /**
     * Initial components.
     */
    public void initComponents(){
        lv_waitingList = (ListView)findViewById(R.id.lv_waitingList);
        List<Friend> waitingList = Resources.owner.getFriendRequest();
        adapter = new FriendRequestCustomAdapter(this,waitingList);
        lv_waitingList.setAdapter(adapter);
    }

    /**
     * On create menu item on actionBar.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.action_refresh){
            updateFriendRequest();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Update friend request view.
     */
    class UpdateFriendRequest extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            Resources.owner.setFriendRequest(Database.getFriendRequest(Resources.owner));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    /**
     * Update friend request.
     */
    public void updateFriendRequest(){
        UpdateFriendRequest task = new UpdateFriendRequest();
        task.execute();
    }

    /**
     * Update method for observer. called from FriendRequestCustomAdapter.
     * Update view for listView.
     * @param observable
     * @param data
     */
    @Override
    public void update(Observable observable, Object data) {
        adapter.notifyDataSetChanged();
    }
}
