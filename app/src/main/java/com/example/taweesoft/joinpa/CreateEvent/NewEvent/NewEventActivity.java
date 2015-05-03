package com.example.taweesoft.joinpa.CreateEvent.NewEvent;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.taweesoft.joinpa.Library.Database;
import com.example.taweesoft.joinpa.Library.DatePicker;
import com.example.taweesoft.joinpa.Library.Event;
import com.example.taweesoft.joinpa.Library.Friend;
import com.example.taweesoft.joinpa.Library.Owner;
import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.Library.User;
import com.example.taweesoft.joinpa.MainActivity;
import com.example.taweesoft.joinpa.R;

import java.util.Map;
import java.util.Observable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observer;

public class NewEventActivity extends ActionBarActivity implements Observer {
    private Spinner spn_icon;
    private List<Friend> selectedFriends;
    private TextView txt_eventName,txt_note;
    private TextView txt_date;
    private TextView txt_time;
    private Button btn_create;
    private LinearLayout layout_date,layout_time;
    private NewEventController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_new_event);
        //Get selected friends list from select friend activity.
        selectedFriends = (List<Friend>)getIntent().getSerializableExtra("SelectedFriend");
        NewEventModel model = (NewEventModel)getIntent().getSerializableExtra("Model");
        model.addObserver(this);
        initComponent();
        initSpinnerIcon();
        initDateTextView();
        initTimeTextView();

        controller = new NewEventController(this,model);
    }

    /**
     * Initial the components.
     */
    public void initComponent(){
        spn_icon = (Spinner)findViewById(R.id.spn_icon);
        txt_eventName = (TextView)findViewById(R.id.txt_eventName);
        txt_note = (TextView)findViewById(R.id.txt_note);
        btn_create = (Button)findViewById(R.id.btn_create);
        btn_create.setOnClickListener(new CreateEventAction());
        txt_date = (TextView)findViewById(R.id.txt_date);
        txt_time = (TextView)findViewById(R.id.txt_time);
        layout_date = (LinearLayout)findViewById(R.id.layout_date);
        layout_time = (LinearLayout)findViewById(R.id.layout_time);
    }

    /**
     * Set spinner's icon.
     */
    public void initSpinnerIcon(){
        CustomEventIcon adapter = new CustomEventIcon(this);
        spn_icon.setAdapter(adapter);
    }

    /**
     * Initial date for text view.
     */
    public void initDateTextView(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);
        txt_date.setText(String.format("%02d/%02d/%04d",day,month,year));
    }

    /**
     * Initial time for text view.
     */
    public void initTimeTextView(){
        Date date = new Date();
        int hour = date.getHours();
        int minute = date.getMinutes();
        txt_time.setText(String.format("%02d:%02d",hour,minute));
    }

    /**
     * Observe the NewEventModel.
     */
    public void update(Observable observable , Object obj){
        if(obj.getClass() != Date.class) return;
        Date date = (Date)obj;
        int day = date.getDate();
        int month = date.getMonth();
        int year = date.getYear();
        int hour = date.getHours();
        int minute = date.getMinutes();
        txt_date.setText(String.format("%02d/%02d/%04d",day,month,year));
        txt_time.setText(String.format("%02d:%02d",hour,minute));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_event, menu);
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



    public void setLayoutDateAction(View.OnClickListener action){
        this.layout_date.setOnClickListener(action);
    }

    public void setLayoutTimeAction(View.OnClickListener action){
        this.layout_time.setOnClickListener(action);
    }

    public void setIconSpinnerAction(AdapterView.OnItemSelectedListener action){
        this.spn_icon.setOnItemSelectedListener(action);
    }

    public void setEventName(String eventName){
        this.txt_eventName.setText(eventName);
    }

    public NewEventController getController(){
        return  controller;
    }

    class CreateEventAction implements View.OnClickListener{
        @Override
        public void onClick(View v){
            /*Initial component for create event.*/
            Date date = controller.getDateAndTime();
            Date now = new Date();
            String eventID = System.currentTimeMillis()+"";
//            String eventID = String.format("%02d%02d%04d%02d%02d%02d", now.getDay(),now.getMonth(),now.getYear(),now.getHours(),now.getMinutes(),now.getSeconds());
            Owner owner = Resources.owner;
            final List<Friend> invitedList = selectedFriends;
            int iconID = spn_icon.getSelectedItemPosition();
            String eventName = txt_eventName.getText().toString();
            String note = txt_note.getText().toString();
            Map<User,Integer> invitedMap = Event.createInvitedMap(invitedList);

            final Event event = new Event(eventID,owner,invitedMap,iconID,eventName,note,date);
            Resources.owner.addEvent(event); // Add event to owner list.
            final String msg = String.format("%s@@\'%s\' %s has invited you to join his event",iconID,eventName,owner.getUsername());
            AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    NewEventActivity.this.finish();
                }

                @Override
                protected Void doInBackground(Void... params) {
                    for(Friend friend : invitedList){
                        Database.sendMsg(friend.getNotifyKey(),msg);
                    }
                    Database.addEvent(event);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {

                }


            };
            task.execute();
        }
    }
}
