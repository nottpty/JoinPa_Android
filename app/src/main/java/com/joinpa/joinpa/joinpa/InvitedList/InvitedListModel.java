package com.joinpa.joinpa.joinpa.InvitedList;

import com.joinpa.joinpa.joinpa.Library.Resources;
import com.joinpa.joinpa.joinpa.Library.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Invited list model.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class InvitedListModel{

    /**
     * Sort map by
     * First priority = JOINED.
     * Second priority = WAITING.
     * Third priority = DECLINE.
     * @param unsortMap
     * @return
     */
    public static List<Map<User,Integer>> sortedMap(Map<User,Integer> unsortMap){
        List<Map<User,Integer>> sortedListMap = new ArrayList<Map<User,Integer>>();
        int joinIndex = 0;
        for(Map.Entry<User,Integer> each : unsortMap.entrySet()){
            int status = each.getValue().intValue();
            Map<User,Integer> map = new HashMap<User,Integer>();
            map.put(each.getKey(),each.getValue());
            if(status == Resources.JOIN){
                sortedListMap.add(joinIndex, map);
                joinIndex++;
            }else if(status == Resources.WAITING)
                sortedListMap.add(joinIndex,map);
            else
                sortedListMap.add(map);
        }
        return sortedListMap;
    }
}
