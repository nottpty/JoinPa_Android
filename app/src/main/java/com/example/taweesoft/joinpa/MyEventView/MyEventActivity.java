package com.example.taweesoft.joinpa.MyEventView;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.taweesoft.joinpa.Library.Event;
import com.example.taweesoft.joinpa.MainActivity;
import com.example.taweesoft.joinpa.R;

import java.util.List;


public class MyEventActivity extends ActionBarActivity {
    private ListView lv_myEventList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event);
        initComponents();
    }

    /**
     * Initial components
     */
    public void initComponents(){
        lv_myEventList = (ListView)findViewById(R.id.lv_myEventList);
        List<Event> myEventList = MainActivity.owner.getEventList();
        MyEventCustomAdapter adapter = new MyEventCustomAdapter(this,myEventList);
        lv_myEventList.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_event, menu);
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
