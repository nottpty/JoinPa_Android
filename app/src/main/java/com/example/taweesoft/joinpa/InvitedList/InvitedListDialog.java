package com.example.taweesoft.joinpa.InvitedList;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ListView;

import com.example.taweesoft.joinpa.Library.Event;
import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.Library.User;
import com.example.taweesoft.joinpa.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taweesoft on 2/5/2558.
 */
public class InvitedListDialog {
    private Context context;
    private Event event;
    public InvitedListDialog(Context context,Event event){
        this.context = context;
        this.event = event;
    }

    public void openDialog(){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //Hide the action bar.
        dialog.setContentView(R.layout.invited_list);
        ListView lv_invitedList = (ListView)dialog.findViewById(R.id.lv_invitedList);
        List<Map<User,Integer>> sortedListMap = sortedMap(event.getInvitedList());
        InvitedListaCustomAdapter adapter = new InvitedListaCustomAdapter(context,sortedListMap);
        lv_invitedList.setAdapter(adapter);
        dialog.show();
    }

    public List<Map<User,Integer>> sortedMap(Map<User,Integer> unsortMap){
        List<Map<User,Integer>> sortedListMap = new ArrayList<Map<User,Integer>>();
        for(Map.Entry<User,Integer> each : unsortMap.entrySet()){
            int status = each.getValue().intValue();
            Map<User,Integer> map = new HashMap<User,Integer>();
            map.put(each.getKey(),each.getValue());
            int index = 0;
            if(status == Resources.WAITING)
                index = sortedListMap.size();
            sortedListMap.add(index,map);
        }
        return sortedListMap;
    }
}
