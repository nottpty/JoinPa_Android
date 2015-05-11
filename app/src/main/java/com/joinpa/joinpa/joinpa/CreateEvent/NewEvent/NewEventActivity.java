package com.joinpa.joinpa.joinpa.CreateEvent.NewEvent;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.joinpa.joinpa.joinpa.Library.Database;
import com.joinpa.joinpa.joinpa.Library.Event;
import com.joinpa.joinpa.joinpa.Library.Friend;
import com.joinpa.joinpa.joinpa.Library.MessageDialog;
import com.joinpa.joinpa.joinpa.Library.Owner;
import com.joinpa.joinpa.joinpa.Library.Resources;
import com.joinpa.joinpa.joinpa.Library.User;
import com.joinpa.joinpa.joinpa.R;

import java.util.Map;
import java.util.Observable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observer;

/**
 * New event activity (View)
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class NewEventActivity extends ActionBarActivity implements Observer {
    private Spinner spn_eventWithIcon,spn_icon;
    private List<Friend> selectedFriends;
    private TextView txt_note;
    private TextView txt_date;
    private TextView txt_time;
    private TextView txt_ownEvent;
    private Button btn_create;
    private LinearLayout layout_date,layout_time,layout_providedEvent,layout_ownEvent;
    private NewEventController controller;
    private CheckBox cb_ownEvent;
    private String eventName;

    /**
     * On create activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_new_event);
        //Get selected friends list from select friend activity.
        selectedFriends = (List<Friend>)getIntent().getSerializableExtra("SelectedFriend");

        /*Initialize components.*/
        initComponent();
        initSpinnerIcon();
        initDateTextView();
        initTimeTextView();

        /*Receive model and create controller.*/
        NewEventModel model = (NewEventModel)getIntent().getSerializableExtra("Model");
        controller = new NewEventController(this,model);
    }

    /**
     * Initial the components.
     */
    public void initComponent(){
        spn_eventWithIcon = (Spinner)findViewById(R.id.spn_eventWithIcon);
        txt_note = (TextView)findViewById(R.id.txt_note);
        btn_create = (Button)findViewById(R.id.btn_create);
        btn_create.setOnClickListener(new CreateEventAction());
        txt_date = (TextView)findViewById(R.id.txt_date);
        txt_time = (TextView)findViewById(R.id.txt_time);
        layout_date = (LinearLayout)findViewById(R.id.layout_date);
        layout_time = (LinearLayout)findViewById(R.id.layout_time);
        cb_ownEvent = (CheckBox)findViewById(R.id.cb_ownEvent);
        cb_ownEvent.setOnCheckedChangeListener(new CheckForOwnEvent());
        spn_icon = (Spinner)findViewById(R.id.spn_icon);
        layout_providedEvent = (LinearLayout)findViewById(R.id.layout_providedEvent);
        layout_ownEvent = (LinearLayout)findViewById(R.id.layout_ownEvent);
        txt_ownEvent = (TextView)findViewById(R.id.txt_ownEvent);
    }

    /**
     * Set spinner's icon.
     */
    public void initSpinnerIcon(){
        CustomEventIconWithName adapter = new CustomEventIconWithName(this);
        spn_eventWithIcon.setAdapter(adapter);

        CustomSpinnerIcon adapterIcon = new CustomSpinnerIcon(this);
        spn_icon.setAdapter(adapterIcon);

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


    /**
     * Set date layout action.
     * @param action
     */
    public void setLayoutDateAction(View.OnClickListener action){
        this.layout_date.setOnClickListener(action);
    }

    /**
     * Set time layout action.
     * @param action
     */
    public void setLayoutTimeAction(View.OnClickListener action){
        this.layout_time.setOnClickListener(action);
    }

    /**
     * Set spinner on click action.
     * @param action
     */
    public void setIconSpinnerAction(AdapterView.OnItemSelectedListener action){
        this.spn_eventWithIcon.setOnItemSelectedListener(action);
    }

    /**
     * Set event name.
     * @param eventName
     */
    public void setEventName(String eventName){
        this.eventName = eventName;
    }

    /**
     * Get controller.
     * @return
     */
    public NewEventController getController(){
        return  controller;
    }

    /**
     * Action when create new event.
     */
    class CreateEventAction implements View.OnClickListener{
        @Override
        public void onClick(View v){
            /*Initial component for create event.*/
            Date date = controller.getDateAndTime();
            String eventID = System.currentTimeMillis()+"";
//            String eventID = String.format("%02d%02d%04d%02d%02d%02d", now.getDay(),now.getMonth(),now.getYear(),now.getHours(),now.getMinutes(),now.getSeconds());
            Owner owner = Resources.owner;
            final List<Friend> invitedList = selectedFriends;

            String eventName = "";
            int iconID = 0;
            if(cb_ownEvent.isChecked()) {
                eventName = txt_ownEvent.getText().toString();
                if(Resources.isIllegalText(eventName,"nothing")){
                    MessageDialog dialog = new MessageDialog(NewEventActivity.this,R.drawable.cross_icon,"Error","Invalid event name.");
                    dialog.showDialog();
                    return;
                }
                iconID = spn_icon.getSelectedItemPosition();
            }else {
                eventName = (String) spn_eventWithIcon.getSelectedItem();
                iconID = Resources.eventsName.get(eventName);
            }

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
                    Database.addEvent(event);
                    for(Friend friend : invitedList){
                        Database.sendMsg(friend.getNotifyKey(),msg);
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {

                }


            };
            task.execute();
        }
    }

    class CheckForOwnEvent implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int provided_visibility = 0;
            int own_visibility = 0;
            if(isChecked){
                provided_visibility = View.GONE;
                own_visibility = View.VISIBLE;
            }else{
                provided_visibility = View.VISIBLE;
                own_visibility = View.GONE;
            }
            layout_providedEvent.setVisibility(provided_visibility);
            layout_ownEvent.setVisibility(own_visibility);
        }
    }
}
