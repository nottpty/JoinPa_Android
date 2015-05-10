package com.joinpa.joinpa.joinpa.MyJoinedEvent;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joinpa.joinpa.joinpa.InvitedList.InvitedListCustomAdapter;
import com.joinpa.joinpa.joinpa.Library.JoiningEvent;
import com.joinpa.joinpa.joinpa.Library.Resources;
import com.joinpa.joinpa.joinpa.Library.User;
import com.joinpa.joinpa.joinpa.R;

import java.util.List;
import java.util.Map;

/**
 * My Joined event custom adapter.
 * Created on 4/5/2558.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class MyJoinedEventCustomAdapter extends BaseExpandableListAdapter{
    private Activity activity;
    private List<List<Map<User,Integer>>> childView;
    private List<JoiningEvent> parentView;
    private InvitedListCustomAdapter invitedListCustomAdapter;

    /**
     * Constructor.
     * @param activity
     * @param parentView
     * @param childView
     */
    public MyJoinedEventCustomAdapter(Activity activity,List<JoiningEvent> parentView, List<List<Map<User,Integer>>> childView){
        this.parentView = parentView;
        this.childView = childView;
        this.activity = activity;
    }

    @Override
    public int getGroupCount() {
        return parentView.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return childView.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        /*Initialize components.*/
        JoiningEvent event = parentView.get(groupPosition);
        if(convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.my_joined_event_view,parent,false);
        LinearLayout layout_note = (LinearLayout)convertView.findViewById(R.id.layout_note);
        TextView txt_note = (TextView)convertView.findViewById(R.id.txt_note);
        TextView txt_eventName = (TextView)convertView.findViewById(R.id.txt_eventName);
        TextView txt_ownerName = (TextView)convertView.findViewById(R.id.txt_ownerName);
        TextView txt_date = (TextView)convertView.findViewById(R.id.txt_date);
        TextView txt_time = (TextView)convertView.findViewById(R.id.txt_time);
        ImageView img_iconBig = (ImageView)convertView.findViewById(R.id.img_iconBig);
        TextView txt_joined = (TextView)convertView.findViewById(R.id.txt_joined);
        TextView txt_waiting = (TextView)convertView.findViewById(R.id.txt_waiting);
        TextView txt_decline = (TextView)convertView.findViewById(R.id.txt_decline);

        /*Initialize data.*/
        if(event.getNote().equals(""))
            layout_note.setVisibility(View.GONE);
        else
            txt_note.setText(event.getNote());
        txt_joined.setText(event.getJoinedNumber()+"");
        txt_waiting.setText(event.getWaitingNumber()+"");
        txt_decline.setText(event.getDeclineNumber()+"");
        txt_eventName.setText(event.getTopic());
        txt_ownerName.setText(event.getEventOwner().getUsername());
        txt_date.setText(event.getDateStr());
        txt_time.setText(event.getTimeStr());
        img_iconBig.setImageResource(Resources.icons.get(event.getIconID()));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        invitedListCustomAdapter = new InvitedListCustomAdapter(activity,childView.get(groupPosition));
        return invitedListCustomAdapter.getView(childPosition,convertView,parent);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
