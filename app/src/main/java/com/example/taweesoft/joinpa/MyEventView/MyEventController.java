package com.example.taweesoft.joinpa.MyEventView;

import android.view.View;
import android.widget.AdapterView;

import com.example.taweesoft.joinpa.InvitedList.InvitedListDialog;
import com.example.taweesoft.joinpa.Library.Event;
import com.example.taweesoft.joinpa.Library.Resources;

import java.util.List;

/**
 * Created by taweesoft on 3/5/2558.
 */
public class MyEventController {
    private MyEventActivity view;
    private MyEventModel model;

    public MyEventController(MyEventActivity view, MyEventModel model){
        this.view = view;
        this.model = model;
        model.addObserver(view);
    }

    class ViewInvitedListAction implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            List<Event> myEvent = Resources.owner.getEventList();
            InvitedListDialog dialog = new InvitedListDialog(view,myEvent.get(position));
            dialog.openDialog();
        }
    }

    public void setListViewAdapter(){
        List<Event> myEventList = Resources.owner.getEventList();
        MyEventCustomAdapter adapter = new MyEventCustomAdapter(view,myEventList);
        view.setLisViewAdapter(adapter);
    }

    public void setListViewClickAction(){
        view.setListViewClickAction(new ViewInvitedListAction());
    }
}
