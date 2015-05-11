package com.joinpa.joinpa.joinpa.CreateEvent.FriendView.FriendListView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.joinpa.joinpa.joinpa.CreateEvent.NewEvent.NewEventActivity;
import com.joinpa.joinpa.joinpa.CreateEvent.NewEvent.NewEventModel;
import com.joinpa.joinpa.joinpa.FindFriend.FindFriendDialog;
import com.joinpa.joinpa.joinpa.Library.Friend;
import com.joinpa.joinpa.joinpa.Library.Resources;
import com.joinpa.joinpa.joinpa.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Friend list actiivty (View)
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class FriendListActivity extends ActionBarActivity implements Observer {
    private List<Friend> selectedFriend = new ArrayList<Friend>();
    private ListView lv_friendsList;
    private Button btn_next,btn_findFriend;
    private ProgressBar pgb_loading;

    /**
     * Constructor.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_friend_list);

        /*Initialize component.*/
        initComponent();
        initButton();

        /*Run loader.*/
        runProgressBar();
    }

    /**
     * Initial Components
     */
    public void initComponent(){
        lv_friendsList = (ListView)findViewById(R.id.lv_friendsList);
        lv_friendsList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        btn_next = (Button)findViewById(R.id.btn_next);
        btn_findFriend = (Button)findViewById(R.id.btn_findFriend);
        pgb_loading = (ProgressBar)findViewById(R.id.pgb_loading);
    }

    /**
     * Set adapter for listview.
     */
    public void setListViewAdapter(){
        FriendListCustomAdapter adapter = new FriendListCustomAdapter(this, Resources.owner.getFriendList());
        lv_friendsList.setAdapter(adapter);
    }

    /**
     * Initial next button event.
     */
    public void initButton(){
        ShowEventCreator action = new ShowEventCreator(this);
        btn_next.setOnClickListener(action);
        ShowFindDialog findDialog = new ShowFindDialog(this);
        btn_findFriend.setOnClickListener(findDialog);
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

    /**
     * Update method of observer. called from FriendListCustomAdapter.
     * @param observable
     * @param obj
     */
    public void update(Observable observable,Object obj){
        if(obj.getClass() != ArrayList.class) {
            return;
        }
        selectedFriend = (List<Friend>)obj;
    }

    /**
     * Show event creator. (NewEventActivity)
     */
    class ShowEventCreator implements View.OnClickListener{
        private Activity activity;
        public ShowEventCreator(Activity activity){
            this.activity = activity;
        }

        public void onClick(View v){
            if(selectedFriend.size() > 0){
                NewEventModel model = new NewEventModel();
                Intent intent = new Intent(FriendListActivity.this, NewEventActivity.class);
                intent.putExtra("SelectedFriend",(ArrayList<Friend>)selectedFriend);
                intent.putExtra("Model",model);
                FriendListActivity.this.finish();
                startActivity(intent);
            }else{
                AlertDialog dialog = new AlertDialog.Builder(activity).create();
                dialog.setMessage("You did not select any friend");
                dialog.show();
            }

        }
    }

    /**
     * Show find friend dialog.
     */
    class ShowFindDialog implements View.OnClickListener{
        private Activity activity;
        public ShowFindDialog(Activity activity){
            this.activity = activity;
        }

        public void onClick(View v){
            FindFriendDialog findFriend = new FindFriendDialog(activity);
            findFriend.openDialog();
        }
    }

    /**
     * Run loader.
     */
    public void runProgressBar(){
        RunProgressBar task = new RunProgressBar();
        task.execute();
    }

    /**
     * Run the progress bar class.
     */
    class RunProgressBar extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            pgb_loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            while(!Resources.owner.isLoadFriendListFinish()){}
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pgb_loading.setVisibility(View.GONE);
            setListViewAdapter();
        }
    }
}
