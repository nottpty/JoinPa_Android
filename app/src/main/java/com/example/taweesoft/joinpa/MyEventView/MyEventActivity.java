package com.example.taweesoft.joinpa.MyEventView;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.taweesoft.joinpa.InvitedList.InvitedListDialog;
import com.example.taweesoft.joinpa.Library.Event;
import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.MainActivity;
import com.example.taweesoft.joinpa.R;

import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class MyEventActivity extends ActionBarActivity implements Observer {
    private ListView lv_myEventList;
    private ProgressBar progressBar;
    private MyEventController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_my_event);
        MyEventModel model = new MyEventModel();
        controller = new MyEventController(this,model);
        initComponents();
    }

    /**
     * Initial components
     */
    public void initComponents(){
        lv_myEventList = (ListView)findViewById(R.id.lv_myEventList);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        RunProgressBar task = new RunProgressBar(); //Set adapter for listview.
        task.execute();
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

    @Override
    public void update(Observable observable, Object data) {

    }

    class RunProgressBar extends AsyncTask<Void,Void,Void>{
        protected void onPreExecute(){
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected Void doInBackground(Void... params) {
            while(!Resources.owner.isLoadMyEventFinish()){ }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.INVISIBLE);
            controller.setListViewAdapter();
            controller.setListViewClickAction();
        }
    }

    public void setListViewClickAction( AdapterView.OnItemClickListener action){
        lv_myEventList.setOnItemClickListener(action);
    }

    public void setLisViewAdapter( ArrayAdapter adapter){
        lv_myEventList.setAdapter(adapter);
    }
}
