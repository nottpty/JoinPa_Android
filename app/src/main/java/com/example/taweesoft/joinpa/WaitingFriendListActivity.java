package com.example.taweesoft.joinpa;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.taweesoft.joinpa.Library.Friend;
import com.example.taweesoft.joinpa.Library.Resources;

import java.util.List;


public class WaitingFriendListActivity extends ActionBarActivity {
    private ListView lv_waitingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_waiting_list);
        initComponents();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_waiting_list, menu);
        return true;
    }

    public void initComponents(){
        lv_waitingList = (ListView)findViewById(R.id.lv_waitingList);
        List<Friend> waitingList = Resources.owner.getFriendWaitingList();
        WaitingFriendListCustomAdapter adapter = new WaitingFriendListCustomAdapter(this,waitingList);
        lv_waitingList.setAdapter(adapter);
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
