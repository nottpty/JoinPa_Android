package com.example.taweesoft.joinpa;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends ActionBarActivity {
    private Owner owner;
    private ListView lv_joiningList;
    private List<JoiningEvent> joiningEventList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        owner = (Owner)getIntent().getSerializableExtra("Owner");
        initComponents();
    }

    /**
     * Initial components
     */
    public void initComponents(){
        lv_joiningList = (ListView)findViewById(R.id.lv_joiningList);
        joiningEventList = readJoiningEventList();
        JoiningListCustomAdapter adapter = new JoiningListCustomAdapter(this,joiningEventList);
        lv_joiningList.setAdapter(adapter);
    }

    public List<JoiningEvent> readJoiningEventList() {
        List<JoiningEvent> joiningEvents=null;
        AsyncTask<Void,Void,List<JoiningEvent>> task = new AsyncTask<Void, Void, List<JoiningEvent>>() {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
            AlertDialog dialog = dialogBuilder.create();
            protected void onPreExecute(){
                dialog.setMessage("Loading your new event...");
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            protected List<JoiningEvent> doInBackground(Void... params) {
                return Database.myJoiningEvents(owner);
            }

            protected void onPostExecute(List<JoiningEvent> param){
                dialog.setMessage("Success");
                dialog.setCancelable(true);
                dialog.dismiss();
            }
        };
        task.execute();
        try{
            joiningEvents = task.get();
        }catch(InterruptedException e1){
            e1.printStackTrace();
        }catch(ExecutionException e2){ e2.printStackTrace();}

        return joiningEvents;
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
