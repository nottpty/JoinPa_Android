package com.example.taweesoft.joinpa.CreateEvent.FriendView.FriendListView;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.taweesoft.joinpa.Library.Friend;
import com.example.taweesoft.joinpa.MainActivity;
import com.example.taweesoft.joinpa.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class FriendListActivity extends ActionBarActivity implements Observer {
    private List<Friend> selectedFriend = new ArrayList<Friend>();
    private ListView lv_friendsList;
    private Button btn_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        initComponent();
        setListViewAdapter();
    }

    /**
     * Initial Components
     */
    public void initComponent(){
        lv_friendsList = (ListView)findViewById(R.id.lv_friendsList);
        btn_next = (Button)findViewById(R.id.btn_next);
    }

    /**
     * Set adapter for listview.
     */
    public void setListViewAdapter(){
        FriendListCustomAdapter adapter = new FriendListCustomAdapter(this, MainActivity.owner.getFriendList());
        lv_friendsList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_list, menu);
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

    public void update(Observable observable,Object obj){
        if(obj.getClass() != List.class) return;;
        selectedFriend = (List<Friend>)obj;
        Log.d("XXXX : ", selectedFriend+"");
    }

    class NextEvent implements View.OnClickListener{
        public void onClick(View v){

        }
    }
}
