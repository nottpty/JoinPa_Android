package com.example.taweesoft.joinpa.CreateEvent.NewEvent;

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

import com.example.taweesoft.joinpa.Library.Friend;
import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewEventActivity extends ActionBarActivity {
    private Spinner spn_icon;
    private List<Friend> selectedFriends;
    private TextView txt_eventName,txt_note;
    private TextView txt_day,txt_month,txt_year;
    private TextView txt_hour,txt_minute;
    private Button btn_selectDate,btn_create;
    private LinearLayout layout_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        //Get selected friends list from select friend activity.
        selectedFriends = (List<Friend>)getIntent().getSerializableExtra("SelectedFriend");
        initComponent();
        initSpinnerIcon();
        initDateTextView();
        initTimeTextView();

        NewEventController.setView(this);
    }

    /**
     * Initial the components.
     */
    public void initComponent(){
        spn_icon = (Spinner)findViewById(R.id.spn_icon);
        txt_eventName = (TextView)findViewById(R.id.txt_eventName);
        txt_note = (TextView)findViewById(R.id.txt_note);
        btn_selectDate = (Button)findViewById(R.id.btn_selectDate);
        btn_create = (Button)findViewById(R.id.btn_create);
        txt_day = (TextView)findViewById(R.id.txt_day);
        txt_month = (TextView)findViewById(R.id.txt_month);
        txt_year = (TextView)findViewById(R.id.txt_year);
        txt_hour = (TextView)findViewById(R.id.txt_hour);
        txt_minute = (TextView)findViewById(R.id.txt_minute);
        layout_date = (LinearLayout)findViewById(R.id.layout_date);
    }

    /**
     * Set spinner's icon.
     */
    public void initSpinnerIcon(){
        CustomEventIcon adapter = new CustomEventIcon(this);
        spn_icon.setAdapter(adapter);
        IconSelectedEvent action = new IconSelectedEvent();
        spn_icon.setOnItemSelectedListener(action);
    }

    /**
     * Initial date for text view.
     */
    public void initDateTextView(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);
        txt_day.setText(String.format("%02d",day));
        txt_month.setText(String.format("%02d",month));
        txt_year.setText(String.format("%02d",year));
    }

    /**
     * Initial time for text view.
     */
    public void initTimeTextView(){
        Date date = new Date();
        int hour = date.getHours();
        int minute = date.getMinutes();
        txt_hour.setText(String.format("%02d",hour));
        txt_minute.setText(String.format("%02d",minute));
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

    class IconSelectedEvent implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //Get integer of icon in each element of arrayAdapter(CustomEventIcon).
            Integer iconKey = (Integer)parent.getItemAtPosition(position);
            //Get event's name from map.
            String eventName = Resources.eventsName.get(iconKey);
            txt_eventName.setText(eventName);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public void setLayoutDateAction(View.OnClickListener action){
        this.layout_date.setOnClickListener(action);
    }
}
