package com.example.taweesoft.joinpa.InvitedList;

import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.Library.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Created by taweesoft on 3/5/2558.
 */
public class InvitedListModel{
    public static List<Map<User,Integer>> sortedMap(Map<User,Integer> unsortMap){
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
