package com.joinpa.taweesoft.joinpa.CreateEvent.FriendView.FriendListView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.joinpa.taweesoft.joinpa.CreateEvent.NewEvent.NewEventActivity;
import com.joinpa.taweesoft.joinpa.CreateEvent.NewEvent.NewEventModel;
import com.joinpa.taweesoft.joinpa.FindFriend.FindFriendDialog;
import com.joinpa.taweesoft.joinpa.Library.Friend;
import com.joinpa.taweesoft.joinpa.Library.Resources;
import com.joinpa.taweesoft.joinpa.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Friend list actiivty (View)
 */
public class FriendListActivity extends ActionBarActivity implements Observer {
    private List<Friend> selectedFriend = new ArrayList<Friend>();
    private ListView lv_friendsList;
    private Button btn_next,btn_findFriend;

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
        setListViewAdapter();
        initButton();
    }

    /**
     * Initial Components
     */
    public void initComponent(){
        lv_friendsList = (ListView)findViewById(R.id.lv_friendsList);
        lv_friendsList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        btn_next = (Button)findViewById(R.id.btn_next);
        btn_findFriend = (Button)findViewById(R.id.btn_findFriend);
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
}
