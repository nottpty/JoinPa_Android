package com.joinpa.joinpa.joinpa.InvitedList;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ListView;

import com.joinpa.joinpa.joinpa.Library.Event;
import com.joinpa.joinpa.joinpa.Library.User;
import com.joinpa.joinpa.joinpa.R;

import java.util.List;
import java.util.Map;

/**
 * Invited list dialog.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class InvitedListDialog{
    private Context context;
    private Event event;
    private ListView lv_invitedList;
    private InvitedListController controller;

    /**
     * Constructor.
     * @param context
     * @param event
     */
    public InvitedListDialog(Context context,Event event){
        this.context = context;
        this.event = event;

        /*Create model and controller.*/
        InvitedListModel model = new InvitedListModel();
        controller = new InvitedListController(this,model);
    }

    /**
     * Open invited list dialog.
     */
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
