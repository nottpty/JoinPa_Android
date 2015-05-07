package com.joinpa.taweesoft.joinpa.MyEventView;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.joinpa.taweesoft.joinpa.Library.Resources;
import com.joinpa.taweesoft.joinpa.R;

import java.util.Observable;
import java.util.Observer;

/**
 * My event activity (View).
 */
public class MyEventActivity extends ActionBarActivity implements Observer {
    private ListView lv_myEventList;
    private ProgressBar progressBar;
    private MyEventController controller;

    /**
     * On create activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_my_event);

        /*Create model and controller.*/
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

    /**
     * On create option menu on actionbar.
     * @param menu
     * @return
     */
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

    /**
     * update method for observer.
     * @param observable
     * @param data
     */
    @Override
    public void update(Observable observable, Object data) {
        /*Now is do nothing.*/
    }

    /**
     * Run progress bar for loading data.
     */
    class RunProgressBar extends AsyncTask<Void,Void,Void>{
        protected void onPreExecute(){
            /*Show progress bar.*/
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected Void doInBackground(Void... params) {
            /*If my event data is not ready.*/
            while(!Resources.owner.isLoadMyEventFinish()){ }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            /*Hide progress bar.*/
            progressBar.setVisibility(View.INVISIBLE);
            controller.setListViewAdapter();
            controller.setListViewClickAction();
        }
    }

    /**
     * Set listView click action.
     * @param action
     */
    public void setListViewClickAction( AdapterView.OnItemClickListener action){
        lv_myEventList.setOnItemClickListener(action);
    }

    /**
     * Set listView adapter.
     * @param adapter
     */
    public void setLisViewAdapter( ArrayAdapter adapter){
        lv_myEventList.setAdapter(adapter);
    }
}
