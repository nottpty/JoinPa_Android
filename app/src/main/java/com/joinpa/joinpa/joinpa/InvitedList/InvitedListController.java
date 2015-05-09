package com.joinpa.joinpa.joinpa.InvitedList;

import com.joinpa.joinpa.joinpa.Library.User;

import java.util.List;
import java.util.Map;

/**
 * Invited list controller.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class InvitedListController {
    private InvitedListDialog view;
    private InvitedListModel model;

    /**
     * Constructor.
     * @param view
     * @param model
     */
    public InvitedListController(InvitedListDialog view, InvitedListModel model){
        this.view = view;
        this.model = model;
    }

    /**
     * Call sortedMap.
     * @param unsortMap
     * @return
     */
    public List<Map<User,Integer>> sortedMap(Map<User,Integer> unsortMap){
        return model.sortedMap(unsortMap);
    }
}
