package com.joinpa.joinpa.joinpa.MyEventView;

import android.view.View;
import android.widget.AdapterView;

import com.joinpa.joinpa.joinpa.InvitedList.InvitedListDialog;
import com.joinpa.joinpa.joinpa.Library.Event;
import com.joinpa.joinpa.joinpa.Library.Resources;

import java.util.List;

/**
 * My event controller.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class MyEventController {
    private MyEventActivity view;
    private MyEventModel model;

    /**
     * Constructor.
     * @param view
     * @param model
     */
    public MyEventController(MyEventActivity view, MyEventModel model){
        this.view = view;
        this.model = model;
        model.addObserver(view);
    }

    /**
     * View my invited list on each event.
     */
    class ViewInvitedListAction implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            List<Event> myEvent = Resources.owner.getEventList();
            InvitedListDialog dialog = new InvitedListDialog(view,myEvent.get(position));
            dialog.openDialog();
        }
    }

    /**
     * Set list view adapter to show each event.
     */
    public void setListViewAdapter(){
        List<Event> myEventList = Resources.owner.getEventList();
        MyEventCustomAdapter adapter = new MyEventCustomAdapter(view,myEventList);
        view.setLisViewAdapter(adapter);
    }

    /**
     * Set list view click action.
     */
    public void setListViewClickAction(){
        view.setListViewClickAction(new ViewInvitedListAction());
    }
}
