package com.example.taweesoft.joinpa.InvitedList;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ListView;

import com.example.taweesoft.joinpa.Library.Event;
import com.example.taweesoft.joinpa.Library.User;
import com.example.taweesoft.joinpa.R;

import java.util.List;
import java.util.Map;

/**
 * Created by taweesoft on 2/5/2558.
 */
public class InvitedListDialog{
    private Context context;
    private Event event;
    private ListView lv_invitedList;
    private InvitedListController controller;
    public InvitedListDialog(Context context,Event event){
        this.context = context;
        this.event = event;
        InvitedListModel model = new InvitedListModel();
        controller = new InvitedListController(this,model);
    }

    public void openDialog(){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //Hide the action bar.
        dialog.setContentView(R.layout.invited_list);
        lv_invitedList = (ListView)dialog.findViewById(R.id.lv_invitedList);
        List<Map<User,Integer>> sortedListMap = controller.sortedMap(event.getInvitedList());
        InvitedListCustomAdapter adapter = new InvitedListCustomAdapter(context,sortedListMap);
        lv_invitedList.setAdapter(adapter);
        dialog.show();
    }
}
