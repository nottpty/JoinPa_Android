package com.example.taweesoft.joinpa.MyJoinedEvent;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.taweesoft.joinpa.InvitedList.InvitedListCustomAdapter;
import com.example.taweesoft.joinpa.Library.JoiningEvent;
import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.Library.User;
import com.example.taweesoft.joinpa.R;

import java.util.List;
import java.util.Map;

/**
 * Created by taweesoft on 4/5/2558.
 */
public class MyJoinedEventCustomAdapter extends BaseExpandableListAdapter{
    private Activity activity;
    private List<List<Map<User,Integer>>> childView;
    private List<JoiningEvent> parentView;
    private InvitedListCustomAdapter invitedListCustomAdapter;
    public MyJoinedEventCustomAdapter(Activity activity,List<JoiningEvent> parentView, List<List<Map<User,Integer>>> childView){
        this.parentView = parentView;
        Log.d("IIIII : ", parentView.size()+"");
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
        Log.d("OOOO : " , "RRRRRRR");

        JoiningEvent event = parentView.get(groupPosition);

        Log.d("UUUU : " , event.getTopic());
        if(convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.my_joined_event_view,parent,false);
        LinearLayout layout_note = (LinearLayout)convertView.findViewById(R.id.layout_note);
        TextView txt_note = (TextView)convertView.findViewById(R.id.txt_note);
        TextView txt_eventName = (TextView)convertView.findViewById(R.id.txt_eventName);
        TextView txt_ownerName = (TextView)convertView.findViewById(R.id.txt_ownerName);
        TextView txt_date = (TextView)convertView.findViewById(R.id.txt_date);
        TextView txt_time = (TextView)convertView.findViewById(R.id.txt_time);
        ImageView img_iconBig = (ImageView)convertView.findViewById(R.id.img_iconBig);
        if(event.getNote().equals(""))
            layout_note.setVisibility(View.GONE);
        else
            txt_note.setText(event.getNote());
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
