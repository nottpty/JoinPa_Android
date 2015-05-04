package com.example.taweesoft.joinpa.MyJoinedEvent;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.example.taweesoft.joinpa.InvitedList.InvitedListModel;
import com.example.taweesoft.joinpa.Library.JoiningEvent;
import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.Library.User;
import com.example.taweesoft.joinpa.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MyJoinedEventActivity extends ActionBarActivity {
    private ExpandableListView ep_lv_myJoinedEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_joined_event);
        initComponents();
        setExpandableListView();
    }

    /**
     * Initial the components.
     */
    public void initComponents(){
        ep_lv_myJoinedEvent = (ExpandableListView)findViewById(R.id.ep_lv_myJoinedEvent);
    }

    public void setExpandableListView(){
        List<JoiningEvent> joinedEvent = Resources.owner.getJoinedEvent();
        List<List<Map<User,Integer>>> allInvitedList = new ArrayList<List<Map<User,Integer>>>();
        for(JoiningEvent event : joinedEvent){
            List<Map<User,Integer>> invitedListEachEvent = InvitedListModel.sortedMap(event.getInvitedList());
            allInvitedList.add(invitedListEachEvent);
        }
        MyJoinedEventCustomAdapter adapter = new MyJoinedEventCustomAdapter(this,joinedEvent,allInvitedList);
        ep_lv_myJoinedEvent.setAdapter(adapter);
        ep_lv_myJoinedEvent.expandGroup(0);
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
}
